dependencies {
    api(project(":shared"))
    
    // ดึงโมดูลทั้งหมดเข้ามา เพื่อให้ common มองเห็นคลาส CarPlayStatus และฟังก์ชันทั้งหมดทันที
    implementation(project(":automotive"))
    implementation(project(":maphost"))
    implementation(project(":home"))
    
    // (โค้ด dependencies เดิมที่เหลือของคุณยูมะ)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.exoplayer.hls)
    implementation(libs.androidx.media3.ui)
    testImplementation(libs.junit)
    testImplementation("org.robolectric:robolectric:4.17")
    testImplementation("org.mockito:mockito-core:5.20.0")
    testImplementation(libs.jmdns)
}
