package org.firstinspires.ftc.teamcode.lancer

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import framework.ftc.cobaltforge.kobaltforge.KobaltForge
import framework.ftc.cobaltforge.kobaltforge.annotation.Component
import framework.ftc.cobaltforge.kobaltforge.annotation.Device
import framework.ftc.cobaltforge.kobaltforge.annotation.GamePad1
import framework.ftc.cobaltforge.kobaltforge.annotation.Inject
import framework.ftc.cobaltforge.kobaltforge.util.MotorGroup

/**
 * copied from HolonomicOpMode
 * Created by Dummyc0m on 2/4/17.
 */
open class LancerHolonomicBase : KobaltForge() {
    @Device
    lateinit var motor1: DcMotor

    @Device
    lateinit var motor2: DcMotor

    @Device
    lateinit var motor3: DcMotor

    @Device
    lateinit var motor4: DcMotor

    @GamePad1(Component.RIGHT_STICK_X)
    internal var rightX: Float = 0f

    @GamePad1(Component.RIGHT_STICK_Y)
    internal var rightY: Float = 0f

    @GamePad1(Component.LEFT_STICK_X)
    internal var leftX: Float = 0f

    @GamePad1(Component.LEFT_STICK_Y)
    internal var leftY: Float = 0f

    @Inject
    private val holonomicProgram = HolonomicProgram()

    lateinit var holonomicGroup: MotorGroup

    override fun construct() {
        onInit {
            motor1.direction = DcMotorSimple.Direction.FORWARD
            motor2.direction = DcMotorSimple.Direction.REVERSE
            motor3.direction = DcMotorSimple.Direction.REVERSE
            motor4.direction = DcMotorSimple.Direction.FORWARD
            holonomicGroup = MotorGroup(motor1, motor2, motor3, motor4)
        }

        onLoop {
            holonomicGroup.setPowers(holonomicProgram.calculateMotorPower(leftX, leftY, rightX, rightY))
            false
        }
    }
}