package org.firstinspires.ftc.teamcode.holonomic

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import framework.ftc.cobaltforge.kobaltforge.KobaltForge
import framework.ftc.cobaltforge.kobaltforge.annotation.Component
import framework.ftc.cobaltforge.kobaltforge.annotation.Device
import framework.ftc.cobaltforge.kobaltforge.annotation.GamePad1

/**
 * Holonomic Drive Mode
 * Created by Dummyc0m on 10/7/16.
 */
@TeleOp(name = "HolonomicTele")
open class HolonomicOpMode : KobaltForge() {
    internal val vecUnit = 1 / Math.sqrt(2.0)
    internal val motorMagnitude = Math.sqrt(vecUnit * vecUnit * 2) //Math.sqrt(2);
    internal var vec1X = -vecUnit
    internal var vec1Y = -vecUnit
    internal var vec2X = -vecUnit
    internal var vex2Y = vecUnit
    internal var dir2Cached = direction(vec2X, vex2Y)
    internal var dir1Cached = direction(vec1X, vec1Y)

    @Device
    lateinit var motor1: DcMotor

    @Device
    lateinit var motor2: DcMotor

    @Device
    lateinit var motor3: DcMotor

    @Device
    lateinit var motor4: DcMotor

    @GamePad1(Component.RIGHT_STICK_X)
    internal var x: Float = 0f

    @GamePad1(Component.RIGHT_STICK_Y)
    internal var y: Float = 0f

    @GamePad1(Component.LEFT_STICK_X)
    internal var leftX: Float = 0f

    override fun construct() {
        onInit {
            motor1.direction = DcMotorSimple.Direction.FORWARD
            motor2.direction = DcMotorSimple.Direction.REVERSE
            motor3.direction = DcMotorSimple.Direction.REVERSE
            motor4.direction = DcMotorSimple.Direction.FORWARD
        }

        onLoop {
            // x = -x
            val signedAngle1 = dir1Cached - direction(x.toDouble(), y.toDouble())
            val signedAngle2 = dir2Cached - direction(x.toDouble(), y.toDouble())

            val joystickMagnitude = magnitude(x.toDouble(), y.toDouble())

            val val1 = clamp(dotProduct(motorMagnitude, joystickMagnitude, signedAngle1))
            val val2 = clamp(dotProduct(motorMagnitude, joystickMagnitude, signedAngle2))

            if (Math.abs(val1) > 0.1 || Math.abs(val2) > 0.1) {
                motor1.power = val1
                motor2.power = val2
                motor3.power = val1
                motor4.power = val2
            } else if (Math.abs(leftX) > 0.1) {
                motor1.power = leftX.toDouble()
                motor2.power = (-leftX).toDouble()
                motor3.power = (-leftX).toDouble()
                motor4.power = leftX.toDouble()
            } else {
                motor1.power = 0.0
                motor2.power = 0.0
                motor3.power = 0.0
                motor4.power = 0.0
            }
            false
        }
    }

    private fun dotProduct(a: Double, b: Double, theta: Double): Double {
        return a * b * Math.cos(theta)
    }

    private fun magnitude(x: Double, y: Double): Double {
        return Math.sqrt(x * x + y * y)
    }

    /**
     * @param x
     * *
     * @param y
     * *
     * @return theta
     */
    private fun direction(x: Double, y: Double): Double {
        return Math.atan2(y, x)
    }

    private fun clamp(a: Double): Double {
        return if (a > 1) 1.0 else if (a < -1) -1.0 else a
        //return a;
    }
}