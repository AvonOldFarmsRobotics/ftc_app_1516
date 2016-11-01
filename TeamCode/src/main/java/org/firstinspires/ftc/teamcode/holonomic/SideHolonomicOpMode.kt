package org.firstinspires.ftc.teamcode.holonomic

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import framework.ftc.cobaltforge.kobaltforge.KobaltForge
import framework.ftc.cobaltforge.kobaltforge.annotation.Component
import framework.ftc.cobaltforge.kobaltforge.annotation.Device
import framework.ftc.cobaltforge.kobaltforge.annotation.GamePad1
import framework.ftc.cobaltforge.kobaltforge.util.Accumulator

/**
 * Useless Op Mode
 * Created by Dummyc0m on 10/13/16.
 */
@TeleOp(name = "SideHolo")
class SideHolonomicOpMode : KobaltForge() {
    @Device
    lateinit var xMotor1: DcMotor

    @Device
    lateinit var xMotor2: DcMotor

    @Device
    lateinit var yMotor1: DcMotor

    @Device
    lateinit var yMotor2: DcMotor

    @GamePad1(Component.RIGHT_STICK_X)
    internal var rightX: Float = 0f

    @GamePad1(Component.RIGHT_STICK_Y)
    internal var rightY: Float = 0f

    @GamePad1(Component.LEFT_STICK_X)
    internal var leftX: Float = 0f

    val xAcc = Accumulator(0.0) { value1, value2 -> value1 + value2 }

    val yAcc = Accumulator(0.0) { value1, value2 -> value1 + value2 }

    override fun construct() {
        onStart {
            xMotor1.direction = DcMotorSimple.Direction.FORWARD
            xMotor2.direction = DcMotorSimple.Direction.REVERSE
            yMotor1.direction = DcMotorSimple.Direction.FORWARD
            yMotor2.direction = DcMotorSimple.Direction.REVERSE
        }

        onLoop {
            xAcc.accumulate(rightX.toDouble())
            yAcc.accumulate(rightY.toDouble())

            xAcc.consume { value ->
                xMotor1.power = value
                xMotor2.power = value
                xAcc.accumulate(-value)
            }

            yAcc.consume { value ->
                yMotor1.power = value
                yMotor2.power = value
                yAcc.accumulate(-value)
            }

            false
        }
    }
}