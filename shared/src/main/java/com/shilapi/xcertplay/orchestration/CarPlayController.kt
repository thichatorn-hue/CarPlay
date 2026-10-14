package com.shilapi.xcertplay.mfi

import java.io.File
import java.math.BigInteger
import java.security.KeyFactory
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.Signature
import java.security.spec.ECGenParameterSpec
import java.security.spec.PKCS8EncodedKeySpec
import java.util.Date
import org.bouncycastle.asn1.ASN1Encodable
import org.bouncycastle.asn1.ASN1Integer
import org.bouncycastle.asn1.DERBitString
import org.bouncycastle.asn1.DERSequence
import org.bouncycastle.asn1.x500.X500Name
import org.bouncycastle.asn1.x509.AlgorithmIdentifier
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo
import org.bouncycastle.asn1.x509.Time
import org.bouncycastle.asn1.x509.V3TBSCertificateGenerator
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers

// สืบทอด MfiAuthenticationClient ให้ตรงกับที่ CarPlayController คาดหวัง
class LocalMfiAuthenticationClient private constructor(
    private val privateKey: PrivateKey,
    private val certificateBytes: ByteArray,
) : MfiAuthenticationClient {
    
    override val certificateType: MfiCertificateType get() = MfiCertificateType.MFI

    override fun protocolMajor(): Int = 3

    override fun readCertificate(type: Int): ByteArray {
        require(type == 0) { "Invalid certificate type index" }
        return certificateBytes.clone()
    }

    override fun signChallenge(challenge: ByteArray): ByteArray {
        require(challenge.size == 32) { "Challenge must be exactly 32 bytes for SHA-256" }
        
        val signer = Signature.getInstance("SHA256withECDSA")
        signer.initSign(privateKey)
        signer.update(challenge)
        val derSignature = signer.sign()

        // แปลง DER Signature (R และ S) ให้กลับมาเป็นฟอร์แมตดิบ 64 ไบต์ที่ iAP2 คาดหวัง
        val decoder = org.bouncycastle.asn1.ASN1InputStream(derSignature)
        val seq = decoder.readObject() as DERSequence
        val r = (seq.getObjectAt(0) as ASN1Integer).positiveValue.toByteArray()
        val s = (seq.getObjectAt(1) as ASN1Integer).positiveValue.toByteArray()

        val rawSignature = ByteArray(64)
        val rOffset = maxOf(0, r.size - 32)
        val sOffset = maxOf(0, s.size - 32)
        System.arraycopy(r, rOffset, rawSignature, 32 - (r.size - rOffset), r.size - rOffset)
        System.arraycopy(s, sOffset, rawSignature, 64 - (s.size - sOffset), s.size - sOffset)
        
        return rawSignature
    }

    companion object {
        fun load(directory: File, onSign: () -> Unit = {}): LocalMfiAuthenticationClient {
            directory.mkdirs()
            val pk8File = File(directory, "identity.pk8")
            val p7bFile = File(directory, "certificate.p7b")

            if (!pk8File.exists() || !p7bFile.exists()) {
                val generator = KeyPairGenerator.getInstance("EC")
                generator.initialize(ECGenParameterSpec("secp256r1"))
                val pair = generator.generateKeyPair()
                
                val algorithm = AlgorithmIdentifier(X9ObjectIdentifiers.ecdsa_with_SHA256)
                val name = X500Name("CN=DiPlay Software Emulated Accessory")
                val tbs = V3TBSCertificateGenerator().apply {
                    setSerialNumber(ASN1Integer(BigInteger.ONE))
                    setSignature(algorithm)
                    setIssuer(name)
                    setSubject(name)
                    setStartDate(Time(Date(System.currentTimeMillis() - 86400000L)))
                    setEndDate(Time(Date(4102444800000L)))
                    setSubjectPublicKeyInfo(SubjectPublicKeyInfo.getInstance(pair.public.encoded))
                }.generateTBSCertificate()

                val signer = Signature.getInstance("SHA256withECDSA")
                signer.initSign(pair.private)
                signer.update(tbs.encoded)
                val certificate = DERSequence(arrayOf<ASN1Encodable>(
                    tbs, algorithm, DERBitString(signer.sign()),
                )).encoded

                pk8File.writeBytes(pair.private.encoded)
                p7bFile.writeBytes(certificate)
            }

            val privateKeyBytes = pk8File.readBytes()
            val certBytes = p7bFile.readBytes()

            val keyFactory = KeyFactory.getInstance("EC")
            val privateKey = keyFactory.generatePrivate(PKCS8EncodedKeySpec(privateKeyBytes))

            return LocalMfiAuthenticationClient(privateKey, certBytes)
        }
    }
}
