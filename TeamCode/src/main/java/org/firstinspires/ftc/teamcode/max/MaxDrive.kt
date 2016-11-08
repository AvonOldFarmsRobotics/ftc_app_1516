package org.firstinspires.ftc.teamcode.max

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import framework.ftc.cobaltforge.kobaltforge.annotation.Component
import framework.ftc.cobaltforge.kobaltforge.annotation.Device
import framework.ftc.cobaltforge.kobaltforge.annotation.GamePad1
import org.firstinspires.ftc.teamcode.holonomic.HolonomicOpMode

/**
 * Created by Dummyc0m on 11/5/16.
 */
@TeleOp(name = "MaxDrive")
class MaxDrive : HolonomicOpMode() {
    @Device
    lateinit var launchMotor: DcMotor;

    @Device
    lateinit var tempServo: CRServo

    @GamePad1(Component.A)
    var motorButton = false

    @GamePad1(Component.X)
    var servoPositive = false

    @GamePad1(Component.B)
    var servoNegative = false

    override fun construct() {
        super.construct()
        onInit {
            launchMotor.direction = DcMotorSimple.Direction.FORWARD
            tempServo.direction = DcMotorSimple.Direction.FORWARD
        }
        onLoop {
            if (servoPositive) {
                tempServo.power = 1.0
            } else if (servoNegative) {
                tempServo.power = -1.0
            } else {
                tempServo.power = 0.0
            }
            if (motorButton) {
                launchMotor.power = 1.0
            } else {
                launchMotor.power = 0.0
            }
            false
        }
    }
}
