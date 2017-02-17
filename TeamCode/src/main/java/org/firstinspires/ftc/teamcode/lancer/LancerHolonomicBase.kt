package org.firstinspires.ftc.teamcode.lancer

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
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
@TeleOp(name = "LancerBase")
open class LancerHolonomicBase : KobaltForge() {
    @Device lateinit var motor1: DcMotor

    @Device lateinit var motor2: DcMotor

    @Device lateinit var motor3: DcMotor

    @Device lateinit var motor4: DcMotor

    @GamePad1(Component.RIGHT_STICK_X) var rightX: Float = 0f

    @GamePad1(Component.RIGHT_STICK_Y) var rightY: Float = 0f

    @GamePad1(Component.LEFT_STICK_X) var leftX: Float = 0f

    @GamePad1(Component.LEFT_STICK_Y) var leftY: Float = 0f

    @Inject private var holonomicProgram = HolonomicProgram()

    private lateinit var holonomicGroup: MotorGroup

    override fun construct() {
        name = "Lancer"

        onInit {
            motor1.direction = DcMotorSimple.Direction.REVERSE
            motor2.direction = DcMotorSimple.Direction.FORWARD
            motor3.direction = DcMotorSimple.Direction.FORWARD
            motor4.direction = DcMotorSimple.Direction.REVERSE

//            motor1.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
//            motor2.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
//            motor3.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
//            motor4.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
            motor1.mode = DcMotor.RunMode.RUN_USING_ENCODER
            motor2.mode = DcMotor.RunMode.RUN_USING_ENCODER
            motor3.mode = DcMotor.RunMode.RUN_USING_ENCODER
            motor4.mode = DcMotor.RunMode.RUN_USING_ENCODER

            holonomicGroup = MotorGroup(motor1, motor2, motor3, motor4)
        }

        onLoop {
            holonomicGroup.setPowers(holonomicProgram.calculateMotorPower(leftX, leftY, rightX, rightY))
            telemetry(motor1.currentPosition)
            telemetry(motor2.currentPosition)
            telemetry(motor3.currentPosition)
            telemetry(motor4.currentPosition)
            false
        }
    }
}