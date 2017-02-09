package org.firstinspires.ftc.teamcode.deLancer

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import framework.ftc.cobaltforge.kobaltforge.KobaltForge
import framework.ftc.cobaltforge.kobaltforge.annotation.Component
import framework.ftc.cobaltforge.kobaltforge.annotation.Device
import framework.ftc.cobaltforge.kobaltforge.annotation.GamePad1
import framework.ftc.cobaltforge.kobaltforge.util.abs

/**
 * front
 * 1 2
 * 3 4
 * Created by peiqi on 2017/2/4.
 */

@TeleOp(name = "DeLancer")
class DeLancerOpMode : KobaltForge() {

    @Device
    lateinit var motor1: DcMotor

    @Device
    lateinit var motor2: DcMotor

    @Device
    lateinit var motor3: DcMotor

    @Device
    lateinit var motor4: DcMotor

    @GamePad1(Component.LEFT_STICK_X)
    internal var lx: Float = 0f

    @GamePad1(Component.LEFT_STICK_Y)
    internal var ly: Float = 0f

    override fun construct() {
        onInit {
            motor1.direction = DcMotorSimple.Direction.FORWARD
            motor2.direction = DcMotorSimple.Direction.REVERSE
            motor3.direction = DcMotorSimple.Direction.FORWARD
            motor4.direction = DcMotorSimple.Direction.REVERSE
        }

        onLoop {
            if (ly.abs() > 0.1f || lx.abs() > 0.1f) {
                val pow = Math.min(Math.sqrt((ly * ly + lx * lx).toDouble()), 1.0)
                var angle = Math.atan2((-ly).toDouble(), (-lx).toDouble())
                if (angle < 0) angle += Math.PI*2
                val powerArray = listOf(
                    Math.sin(angle - Math.PI/4),
                    Math.cos(angle - Math.PI/4),
                    Math.cos(angle - Math.PI/4),
                    Math.sin(angle - Math.PI/4)
                )
                var max : Double = -2.0
                for (power in powerArray) {
                    if (power.abs() > max) max = power.abs()
                }
                motor1.power = powerArray[0] / max * pow
                motor2.power = powerArray[1] / max * pow
                motor3.power = powerArray[2] / max * pow
                motor4.power = powerArray[3] / max * pow
            } else {
                motor1.power = 0.0
                motor2.power = 0.0
                motor3.power = 0.0
                motor4.power = 0.0
            }
            false
        }
    }

}