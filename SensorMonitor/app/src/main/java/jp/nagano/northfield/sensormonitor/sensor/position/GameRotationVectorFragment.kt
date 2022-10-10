package jp.nagano.northfield.sensormonitor.sensor.position

import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class GameRotationVectorFragment: SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_game_rotation_vector
    override val informationLabel = R.id.game_rotation_vector_info
    override val statusLabel = R.id.game_rotation_vector_status
    override val sensor = GameRotationVectorSensor()
    override val logTag = "GameRotationVectorFragment"
    override val tabName = "Game Rotation Vector"
}