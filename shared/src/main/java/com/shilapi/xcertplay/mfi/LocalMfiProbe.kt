package com.shilapi.xcertplay.mfi

import java.io.File

class LocalMfiProbe(private val directory: File) {

    fun probe() {
        // เรียกใช้งานคลาสหลัก LocalMfiAuthenticationClient ที่ถูกต้อง
        val client = LocalMfiAuthenticationClient.load(directory)
        val certificate = client.readCertificate(0)
        
        // ตัวอย่างการใช้งานตรวจสอบ MFi
        val majorVersion = client.protocolMajor()
    }
}
