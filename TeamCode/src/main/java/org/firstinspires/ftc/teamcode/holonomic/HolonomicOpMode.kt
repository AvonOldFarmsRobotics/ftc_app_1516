package org.firstinspires.ftc.teamcode.holonomic

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import framework.ftc.cobaltforge.kobaltforge.KobaltForge
import framework.ftc.cobaltforge.kobaltforge.annotation.Component
import framework.ftc.cobaltforge.kobaltforge.annotation.Device
import framework.ftc.cobaltforge.kobaltforge.annotation.GamePad1
import framework.ftc.cobaltforge.kobaltforge.annotation.GamePad2
import framework.ftc.cobaltforge.kobaltforge.util.abs

/**
 * Holonomic Drive Mode
 * motors on in /*counter*/ clockwise direction
 *
 * ^
 * | positive
 *
 * 2 3
 * 1 4
 * Created by Dummyc0m on 10/7/16.
 */
//@TeleOp(name = "HolonomicTele")
open class HolonomicOpMode : KobaltForge() {
    internal var vecUnit = 1 / Math.sqrt(2.0)
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

    @GamePad1(Component.LEFT_STICK_Y)
    internal var leftY: Float = 0f

    @GamePad2(Component.RIGHT_STICK_X)
    internal var y2: Float = 0f

    @GamePad2(Component.RIGHT_STICK_Y)
    internal var x2: Float = 0f

    @GamePad2(Component.LEFT_STICK_X)
    internal var leftX2: Float = 0f

    @GamePad2(Component.LEFT_STICK_Y)
    internal var leftY2: Float = 0f

    override fun construct() {
        onInit {
            motor1.direction = DcMotorSimple.Direction.FORWARD
            motor2.direction = DcMotorSimple.Direction.REVERSE
            motor3.direction = DcMotorSimple.Direction.REVERSE
            motor4.direction = DcMotorSimple.Direction.FORWARD
        }

        onLoop {
            // x = -x
//            val calculatedY = if (leftY2.abs() > y2.abs()) leftY2 else y2
//            val calculatedX = if (leftY2.abs() > x2.abs()) -leftY2 else -x2

            val calcY = if (y2.abs() > y.abs()) {
                if (leftY.abs() > y2.abs()) leftY else y2
            } else {
                if (leftY.abs() > y.abs()) leftY else y
            }

            val calcX = if (x2.abs() > x.abs()) {
                if (leftY2.abs() > x2.abs()) -leftY2 else -x2
            } else {
                if (leftY2.abs() > x.abs()) -leftY2 else x
            }

            val calcRot = if (leftX2.abs() > leftX.abs()) leftX2 else leftX

//            val calculatedY = if (leftY.abs() > y.abs()) {
//                if (leftY.abs() > y2.abs()) leftY else y2
//            } else if (y.abs() > y2.abs()) y else y2
//
//            val calculatedX = if (x2.abs() > x.abs()) {
//                if (x2.abs() > leftY2.abs()) -x2 else -leftX2
//            } else if (x2.abs() > x.abs()) -x2 else x

            val signedAngle1 = dir1Cached - direction(calcX.toDouble(), calcY.toDouble())
            val signedAngle2 = dir2Cached - direction(calcX.toDouble(), calcY.toDouble())

            val joystickMagnitude = magnitude(calcX.toDouble(), calcY.toDouble())

            val val1 = clamp(dotProduct(motorMagnitude, joystickMagnitude, signedAngle1))
            val val2 = clamp(dotProduct(motorMagnitude, joystickMagnitude, signedAngle2))

            if (Math.abs(val1) > 0.1 || Math.abs(val2) > 0.1) {
                motor1.power = val1
                motor2.power = val2
                motor3.power = val1
                motor4.power = val2
            } else if (Math.abs(calcRot) > 0.1) {
                motor1.power = calcRot.toDouble()
                motor2.power = (-calcRot).toDouble()
                motor3.power = (-calcRot).toDouble()
                motor4.power = calcRot.toDouble()
            } else {
                motor1.power = 0.0
                motor2.power = 0.0
                motor3.power = 0.0
                motor4.power = 0.0
            }
            false
        }
    }

    internal fun dotProduct(a: Double, b: Double, theta: Double): Double {
        return a * b * Math.cos(theta)
    }

    internal fun magnitude(x: Double, y: Double): Double {
        return Math.sqrt(x * x + y * y)
    }

    /**
     * @param x
     * *
     * @param y
     * *
     * @return theta
     */
    internal fun direction(x: Double, y: Double): Double {
        return Math.atan2(y, x)
    }

    internal fun clamp(a: Double, max: Double = 1.0, min: Double = -1.0): Double {
        return if (a > max) max else if (a < min) min else a
    }
}
