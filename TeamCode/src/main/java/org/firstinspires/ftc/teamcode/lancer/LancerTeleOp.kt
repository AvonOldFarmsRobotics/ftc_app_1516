package org.firstinspires.ftc.teamcode.lancer

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo
import framework.ftc.cobaltforge.kobaltforge.annotation.Component
import framework.ftc.cobaltforge.kobaltforge.annotation.Device
import framework.ftc.cobaltforge.kobaltforge.annotation.GamePad2
import framework.ftc.cobaltforge.kobaltforge.annotation.Inject
import framework.ftc.cobaltforge.kobaltforge.util.Listener
import framework.ftc.cobaltforge.kobaltforge.util.MotorGroup

/**
 * Created by Dummyc0m on 2/4/17.
 */
@TeleOp(name = "Lancer TeleOp")
class LancerTeleOp : LancerHolonomicBase() {
    @Device lateinit var launcher0: DcMotor

    @Device lateinit var launcher1: DcMotor

    @Device lateinit var lift: DcMotor

    @Device lateinit var intake: DcMotor

    @Device lateinit var stop: Servo

    @Device var minStop = false

    @Device var maxStop = false

    @GamePad2(Component.X) var stopButton = false

    @GamePad2(Component.Y) var launchButton = false

    @GamePad2(Component.B) var intakeButton = false

    @GamePad2(Component.DPAD_LEFT) var resetLeft = false

    @GamePad2(Component.DPAD_RIGHT) var resetRight = false

    @Inject private val launcher = LauncherProgram()

    @Inject private val stopper = StopperProgram()

    private lateinit var launcherGroup: MotorGroup

    private val intakeListener = Listener(false)

    private var intakeOn = false

    private val stopperListener = Listener(false)

    private val launchListener = Listener(false)

    override fun construct() {
        super.construct()
        onInit {
            launcher0.direction = DcMotorSimple.Direction.FORWARD
            launcher1.direction = DcMotorSimple.Direction.REVERSE

            lift.direction = DcMotorSimple.Direction.FORWARD
            stop.direction = Servo.Direction.FORWARD

            intake.direction = DcMotorSimple.Direction.FORWARD

            launcherGroup = MotorGroup(launcher0, launcher1, lift)

            intakeListener.onChange { old, new -> if (new) intakeOn = !intakeOn }
            stopperListener.onChange { old, new -> if (new) stopper.toggle() }
            launchListener.onChange { old, new -> if (new) launcher.launch() }
        }

        onLoop {
            if (resetLeft && resetRight) {
                stopper.pos2()
                launcher.home()
            }
            if (intakeOn) {
                intake.power = 1.0
            } else {
                intake.power = 0.0
            }
            intakeListener.newValue(intakeButton)
            stopperListener.newValue(stopButton)
            launchListener.newValue(launchButton)
            launcherGroup.setPowers(launcher.update(minStop, maxStop))
            stop.position = stopper.update(stop.position)
            false
        }
    }
}