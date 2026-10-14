package com.shilapi.xcertplay.orchestration

import android.content.Context
import com.shilapi.xcertplay.mfi.LocalMfiAuthenticationClient
import java.io.File

class CarPlayController(private val context: Context) {

    // ตัวอย่างการเรียกใช้งาน LocalMfiAuthenticationClient ที่ถูกต้องผ่านตัวแปร (ไม่ประกาศคลาสซ้ำซ้อน)
    private val mfiClient by lazy {
        val mfiDir = File(context.filesDir, "mfi")
        LocalMfiAuthenticationClient.load(mfiDir)
    }

    fun initializeSession() {
        // ใช้ mfiClient ที่โหลดมาทำงานต่อได้ทันที
        val cert = mfiClient.readCertificate(0)
        // โค้ดส่วนควบคุม CarPlay อื่นๆ ต่อที่นี่
    }
}
