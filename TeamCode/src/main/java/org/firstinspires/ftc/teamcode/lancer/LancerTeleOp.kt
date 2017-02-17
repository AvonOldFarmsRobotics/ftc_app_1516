package org.firstinspires.ftc.teamcode.lancer

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.TouchSensor
import framework.ftc.cobaltforge.kobaltforge.annotation.Component
import framework.ftc.cobaltforge.kobaltforge.annotation.Device
import framework.ftc.cobaltforge.kobaltforge.annotation.GamePad2
import framework.ftc.cobaltforge.kobaltforge.annotation.Inject
import framework.ftc.cobaltforge.kobaltforge.util.Listener
import framework.ftc.cobaltforge.kobaltforge.util.MotorGroup
import framework.ftc.cobaltforge.kobaltforge.util.RpmMeter

/**
 * Created by Dummyc0m on 2/4/17.
 */
@TeleOp(name = "LancerFull")
class LancerTeleOp : LancerHolonomicBase() {
    @Device lateinit var launcher0: DcMotor

    @Device lateinit var launcher1: DcMotor

    @Device lateinit var lift: DcMotor

    @Device lateinit var intake: DcMotor

    @Device lateinit var stop: Servo

    @Device lateinit var minStop: TouchSensor

    @Device lateinit var maxStop: TouchSensor

    @GamePad2(Component.X) var stopButton = false

    @GamePad2(Component.Y) var launchButton = false

    @GamePad2(Component.B) var intakeButton = false

    @GamePad2(Component.GUIDE) var homeButton = false

    @GamePad2(Component.DPAD_RIGHT) var tempResetButton = false

    @GamePad2(Component.RIGHT_BUMPER) var flipIntakeButton = false

    private var intakeDirection = true

//    @GamePad2(Component.DPAD_RIGHT) var resetRight = false

    // This has to be var!!! do not try to optimize
    @Inject private var launcher = LauncherProgram()

    @Inject private var stopper = StopperProgram()

    private lateinit var launcherGroup: MotorGroup

    private val intakeListener = Listener(false)

    private var intakeOn = false

    private val stopperListener = Listener(false)

    private val launchListener = Listener(false)

    private val flipIntakeListener = Listener(false)

    private lateinit var rpmLauncher0: RpmMeter

    private lateinit var rpmLauncher1: RpmMeter

    override fun construct() {
        super.construct()
        onInit {
            debug = true

            launcher0.direction = DcMotorSimple.Direction.FORWARD
            launcher1.direction = DcMotorSimple.Direction.REVERSE

            lift.direction = DcMotorSimple.Direction.REVERSE
            stop.direction = Servo.Direction.FORWARD

            intake.direction = DcMotorSimple.Direction.FORWARD

            launcherGroup = MotorGroup(launcher0, launcher1, lift)

            intakeListener.onChange { old, new -> if (new) intakeOn = !intakeOn }

            flipIntakeListener.onChange { old, new -> if (new) intakeDirection = !intakeDirection }

            stopperListener.onChange { old, new ->
                if (launcher.available && new) stopper.toggle()
            }
            launchListener.onChange { old, new ->
                stopper.pos2()
                if (new) launcher.launch()
            }
            rpmLauncher0 = RpmMeter(launcher0, telemetry)
            rpmLauncher1 = RpmMeter(launcher1, telemetry)
        }

        onLoop {
            if (homeButton || tempResetButton) {
                stopper.pos2()
                stop.position = stopper.pos2
                launcher.home()
            }
            if (intakeOn) {
                intake.power = if (intakeDirection) 1.0 else -1.0
            } else {
                intake.power = 0.0
            }
            intakeListener.newValue(intakeButton)
            stopperListener.newValue(stopButton)
            launchListener.newValue(launchButton)
            flipIntakeListener.newValue(flipIntakeButton)
            val launchPowers = launcher.update(minStop.isPressed, maxStop.isPressed)
            launcherGroup.setPowers(launchPowers)
            telemetry(launchPowers)
            rpmLauncher0.update()
            rpmLauncher1.update()
            if (!stop.position.isNaN()) {
                stop.position = stopper.update(stop.position)
            }
            false
        }
    }
}