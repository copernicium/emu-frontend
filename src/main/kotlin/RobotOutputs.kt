package frontend

class RobotOutputs {
    companion object {
        const val NUM_PWM_HDRS: Int = 10
    }

    class RobotOutputsImpl{
        var pwm_hdrs = Array<Double>(NUM_PWM_HDRS) { 0.0 }
        var can_motor_controllers = ArrayList<CANMotorController>()
    }

    var roborio: RobotOutputsImpl = RobotOutputsImpl()
}