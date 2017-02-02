package org.firstinspires.ftc.teamcode.autonomous

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import framework.ftc.cobaltforge.kobaltforge.KobaltForge
import framework.ftc.cobaltforge.kobaltforge.annotation.Device
import framework.ftc.cobaltforge.kobaltforge.util.abs

/**
 * First Beacon 63(1600mm), Second 110(2794)
 * Created by Dummyc0m on 11/12/16.
 */
@Autonomous(name = "WIP", group = "Samson")
//@TeleOp(name = "Simple Holonomic")
class WIPAutonomous : KobaltForge() {
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

    // right stick x
    internal var x: Float = 0f

    // right stick y
    internal var y: Float = 0f

    // left stick x
    internal var leftX: Float = 0f

    // left stick y
    internal var leftY: Float = 0f

    private var startTime = 0L

    private var firstLoop = true

    override fun construct() {
        onInit {
            motor1.direction = DcMotorSimple.Direction.FORWARD
            motor2.direction = DcMotorSimple.Direction.REVERSE
            motor3.direction = DcMotorSimple.Direction.REVERSE
            motor4.direction = DcMotorSimple.Direction.FORWARD
        }
        onLoop {
            if (firstLoop) {
                firstLoop = false
                startTime = System.currentTimeMillis()
            }
            if (System.currentTimeMillis() - startTime < 5000) {
                y = 1f
            } else {
                y = 0f
            }
//            val compoundY = clamp((y + leftY).toDouble())
//            move(x.toDouble(), compoundY , leftX.toDouble())
//             x = -x
            val calculatedY = if (leftY.abs() > y.abs()) leftY else y

            val signedAngle1 = dir1Cached - direction(x.toDouble(), calculatedY.toDouble())
            val signedAngle2 = dir2Cached - direction(x.toDouble(), calculatedY.toDouble())

            val joystickMagnitude = magnitude(x.toDouble(), calculatedY.toDouble())

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

//    fun move(x: Double, y: Double, rot: Double) {
//        motor1.power = (y - x + rot)
//        motor2.power = (y + x + rot)
//        motor3.power = (- y + x + rot)
//        motor4.power = (- y - x + rot)
//    }

    /**
     * Move to Coordinate
     */
    fun moveTo(x: Double, y: Double, rot: Double) {

    }

    internal fun clamp(a: Double, max: Double = 1.0, min: Double = -1.0): Double {
        return if (a > max) max else if (a < min) min else a
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
}