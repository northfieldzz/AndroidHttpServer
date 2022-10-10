package jp.nagano.northfield.sensormonitor.sensor.position

import jp.nagano.northfield.sensormonitor.R
import jp.nagano.northfield.sensormonitor.sensor.SensorFragmentBase

class Pose6DOFFragment : SensorFragmentBase() {
    override val inflaterLabel = R.layout.fragment_pose_6dof
    override val informationLabel = R.id.pose_6dof_info
    override val statusLabel = R.id.pose_6dof_status
    override val sensor = Pose6DOFSensor()
    override val logTag = "Pose6DOFFragment"
    override val tabName = "Pose 6DOF"
}