package jp.nagano.northfield.sensormonitor.sensor

interface SensorInterface {
    val name: String
    val type: Int

    fun getString(data: FloatArray): String
}