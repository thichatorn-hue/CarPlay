package com.shilapi.xcertplay

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Handler
import android.os.Looper

/**
 * Activity-owned adapter.
 * Sensor events and delayed transitions share the main looper.
 */
internal class AndroidAmbientLight(
    context: Context
) : CarPlayNightModeController.LightSource, SensorEventListener {

    private val manager: SensorManager? =
        context.getSystemService(SensorManager::class.java)

    private val sensor: Sensor? =
        manager?.getDefaultSensor(Sensor.TYPE_LIGHT)

    private val handler: Handler =
        Handler(Looper.getMainLooper())

    private var onLux: ((Float) -> Unit)? = null

    override val available: Boolean
        get() = sensor != null

    override fun start(onLux: (Float) -> Unit): Boolean {
        stop()

        val light: Sensor = sensor ?: return false

        this.onLux = onLux

        val registered: Boolean = runCatching {
            manager?.registerListener(
                this,
                light,
                SensorManager.SENSOR_DELAY_NORMAL,
                handler
            ) == true
        }.getOrDefault(false)

        if (!registered) {
            stop()
        }

        return registered
    }

    override fun stop() {
        onLux = null
        manager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_LIGHT) {
            val lux: Float =
                event.values.firstOrNull() ?: Float.NaN

            onLux?.invoke(lux)
        }
    }

    override fun onAccuracyChanged(
        sensor: Sensor?,
        accuracy: Int
    ) {
        // No action required.
    }
}

/**
 * Scheduler for night mode operations on the main thread.
 */
internal class MainThreadNightModeScheduler :
    CarPlayNightModeController.Scheduler {

    private val handler: Handler =
        Handler(Looper.getMainLooper())

    override fun postDelayed(
        task: Runnable,
        delayMillis: Long
    ) {
        handler.postDelayed(task, delayMillis)
    }

    override fun remove(task: Runnable) {
        handler.removeCallbacks(task)
    }
}
