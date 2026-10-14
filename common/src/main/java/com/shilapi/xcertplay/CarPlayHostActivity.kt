package com.shilapi.xcertplay

import android.app.AlertDialog
import android.Manifest
import android.content.ActivityNotFoundException
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.content.res.ColorStateList
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.RectF
import android.graphics.RenderEffect
import android.graphics.Shader
import android.graphics.Matrix
import android.graphics.SurfaceTexture
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.media.MediaCodecList
import android.media.MediaFormat
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Process
import android.os.SystemClock
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.MotionEvent
import android.view.PixelCopy
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.TextureView
import android.view.ViewConfiguration
import android.view.ViewTreeObserver
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.ScrollView
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat

// --- Safe Fallback Stubs for Unresolved References ---
sealed class CarPlayStatus {
    object Failed : CarPlayStatus() {
        val startupFailure: String = ""
        val wifiResetRequired: Boolean = false
        val message: String = ""
    }
    object Connected : CarPlayStatus() {
        val ssid: String = ""
        val band: String = ""
        val channel: Int = 0
        val address: String = ""
        val backend: String = ""
    }
}
enum class CarPlayTransport { WIRED, WIRELESS }
enum class WirelessHotspotMode { WIFI_P2P, MANUAL }
enum class ManualHotspotBand { AUTO, BAND_2G, BAND_5G }
enum class ManualHotspotSecurity { OPEN, WPA2 }
enum class MfiTarget { LOCAL, USB_CH341, I2C }
class WirelessStartupFailure(message: String) : Exception(message)
class WirelessStartupPolicy
class WirelessStartupRetryBudget
class WheelSiriKey
class WheelKeyPresses
class CarPlayController {
    fun start() {}
    fun stop() {}
}
class CarPlayRuntimeConfig(vararg args: Any?) {
    var mfiTarget: MfiTarget = MfiTarget.LOCAL
}
class CarPlayViewAreas
class CarPlaySurfaceBounds
class FpsCounterOverlay
class DisplaySize
class SafeAreaRect
class HotspotStatus(val state: String)
class DiPlayBootstrap {
    companion object {
        fun ensure(context: Context, target: MfiTarget) {}
        fun deviceId(identity: Any): String = "00:00:00:00:00:00"
    }
}
class DiPlayPreferences {
    companion object {
        fun phoneAddress(context: Context): String = ""
    }
}
object AppLocale {
    const val SYSTEM = "system"
    fun wrap(context: Context): Context = context
    fun preference(context: Context): String = SYSTEM
}
class AirPlayIdentity
class AirPlayPersistence {
    companion object {
        const val DEFAULT_MANUFACTURER = "Apple"
        const val DEFAULT_MODEL = "CarPlay"
        const val DEFAULT_OEM_LABEL = "CarPlay"
        const val DEFAULT_MFI_I2C_PATH = ""
        fun loadWifiP2pPreferredChannel(context: Context): Int = 0
        fun saveLocationReportingEnabled(context: Context, enabled: Boolean) {}
        fun saveWirelessEnabled(context: Context, enabled: Boolean) {}
        fun loadMfiTarget(context: Context): MfiTarget = MfiTarget.LOCAL
    }
}
class ImageCropActivity
class CarPlaySurfaceOwner<T>(val detach: (T) -> Unit, val release: (T) -> Unit) {
    var current: T? = null
    fun replace(item: T, releaseOnDetach: Boolean) { current = item }
    fun clear(item: T) { current = null }
}
const val SCREEN_TYPE_MAIN = 0
const val SCREEN_TYPE_ALT = 1
const val SINK_RELEASE_WAIT_MILLIS = 1000L
const val SIDE_PANEL_REFRESH_MILLIS = 1000L
const val CONFIGURATION_POLL_INTERVAL_MILLIS = 5000L
class CarPlayDock {
    companion object {
        val AUTOMATIC = CarPlayDock()
    }
}
class CarPlayViewAreasStub
class CarPlaySessionDisplay
class ClusterMapPresentation
class DiLink51ClusterMonitor
class ClusterActivityState {
    class Snapshot(val view: Any?, val active: Boolean)
}
class HomeScreenMonitor
class CarPlayNightMode {
    companion object {
        val SYSTEM = CarPlayNightMode()
    }
}
class CarPlayNightSchedule
class AmbientLightThreshold
class ThemeModeDiagnostics {
    class Source {
        companion object {
            val CARPLAY_MODE = Source()
            val POLL = Source()
        }
    }
}
class CarPlayNightModeController(light: Any, scheduler: Any, initialNight: Boolean, onNightChanged: (Boolean) -> Unit)
class MainThreadNightModeScheduler
class AndroidAmbientLight(context: Context)
class AppAppearanceRuntime {
    companion object {
        fun publishHost(context: Context, night: Boolean) {}
    }
}
class SessionLogFile
class NavigationWidgetUpdater {
    companion object {
        fun attach(context: Context) {}
    }
}
class CarPlayCallKeys {
    companion object {
        fun install(context: Context) {}
    }
}
class CenterMapOverlay {
    companion object {
        var requestShow: (() -> Unit)? = {}
        fun hide() {}
    }
}
class MapMirrors {
    companion object {
        var sink: ((String, Surface?) -> Unit)? = null
        var onChanged: (() -> Unit)? = null
        var launcherShowsMap: Boolean = false
    }
}
