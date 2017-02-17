package org.firstinspires.ftc.teamcode.samson

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.TouchSensor
import framework.ftc.cobaltforge.kobaltforge.annotation.Component
import framework.ftc.cobaltforge.kobaltforge.annotation.Device
import framework.ftc.cobaltforge.kobaltforge.annotation.GamePad2
import framework.ftc.cobaltforge.kobaltforge.annotation.State
import framework.ftc.cobaltforge.kobaltforge.extension.forward
import framework.ftc.cobaltforge.kobaltforge.extension.reverse
import framework.ftc.cobaltforge.kobaltforge.extension.run
import framework.ftc.cobaltforge.kobaltforge.extension.stop
import org.firstinspires.ftc.teamcode.holonomic.HolonomicOpMode

/**
 * The Samson robot with a lot more complicated code // much complex very wow
 * Created by Dummyc0m on 12/3/16.
 */
//@TeleOp(name = "SamsonPrimary", group = "Samson")
class SamsonPrimary : HolonomicOpMode() {
    @State
    var launchPosOn = 300

    @State
    var launchPosOff = 0

    @State
    var launchPower = 0.5

    @State
    var resetPower = -0.3

    @State
    var servoPosOn = 0.3

    @State
    var servoPosOff = 0.6

    @Device
    lateinit var blockingServo: Servo

    @Device
    lateinit var launchMotor: DcMotor

    @Device
    lateinit var longChairMotor1: DcMotor

    @Device
    lateinit var longChairMotor2: DcMotor

    @Device
    lateinit var intakeMotor: DcMotor

    private var intakeToggle = false

    private var intakeButtonPrevious = false

    @GamePad2(Component.X)
    var intakeButton = false

    private var longChairToggle = false

    private var longChairButtonPrevious = false

    @GamePad2(Component.Y)
    var longChairButton = false

    @GamePad2(Component.A)
    var longChairMotorButton = false

    private var longChairMotorToggle = false

    private var longChairMotorButtonPrevious = false

    @GamePad2(Component.LEFT_BUMPER)
    var homingButton = false

    @Device
    lateinit var endStop: TouchSensor

    @State
    var homingBouncePos = 100

    var homing = HomingState.UNKNOWN

    override fun construct() {
        super.construct()
        onInit {
            longChairMotor1.reverse()
            longChairMotor2.forward()
            intakeMotor.reverse()
            blockingServo.direction = Servo.Direction.FORWARD
            launchMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        }
        onLoop {
//            val calculatedY = if (leftY2.abs() > y2.abs()) leftY2 else y2
//            val calculatedX = if (leftY2.abs() > x2.abs()) -leftY2 else -x2
//
//            val signedAngle1 = dir1Cached - direction(calculatedX.toDouble(), y2.toDouble())
//            val signedAngle2 = dir2Cached - direction(calculatedX.toDouble(), y2.toDouble())
//
//            val joystickMagnitude = magnitude(calculatedX.toDouble(), y2.toDouble())
//
//            val val1 = clamp(dotProduct(motorMagnitude, joystickMagnitude, signedAngle1))
//            val val2 = clamp(dotProduct(motorMagnitude, joystickMagnitude, signedAngle2))
//
//            if (Math.abs(val1) > 0.1 || Math.abs(val2) > 0.1) {
//                motor1.power = val1
//                motor2.power = val2
//                motor3.power = val1
//                motor4.power = val2
//            } else if (Math.abs(leftX2) > 0.1) {
//                motor1.power = leftX2.toDouble()
//                motor2.power = (-leftX2).toDouble()
//                motor3.power = (-leftX2).toDouble()
//                motor4.power = leftX2.toDouble()
//            } else {
//                motor1.power = 0.0
//                motor2.power = 0.0
//                motor3.power = 0.0
//                motor4.power = 0.0
//            }
//            var compoundY = 0.0
//            var compoundX = 0.0
//            if (dPadLeft) {
//                compoundY += 1.0
//            }
//            if (dPadRight) {
//                compoundY -= 1.0
//            }
//            if (dPadUp) {
//                compoundX += 1.0
//            }
//            if (dPadDown) {
//                compoundX -= 1.0
//            }
//            if (dPadUp || dPadDown || dPadLeft || dPadRight) {
//                motor1.power = (compoundY - compoundX)
//                motor2.power = (compoundY + compoundX)
//                motor3.power = (-compoundY + compoundX)
//                motor4.power = (-compoundY - compoundX)
//            }
            when (homing) {
                HomingState.HOMING -> {
                    launchMotor.power = -0.1
                    if (endStop.isPressed) {
                        telemetry("homed, resetting")
                        launchMotor.stop()
                        homing = HomingState.RESETTING
                        launchMotor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
                    } else {
                        telemetry("homing ${launchMotor.currentPosition}")
                    }
                }
                HomingState.RESETTING -> {
                    if (launchMotor.currentPosition === 0) {
                        telemetry("reset, changing state")
                        launchMotor.mode = DcMotor.RunMode.RUN_USING_ENCODER
                        homing = HomingState.UNKNOWN
                    } else {
                        telemetry("resetting ${launchMotor.currentPosition}")
                    }
                }
                HomingState.UNKNOWN -> {
                    telemetry("launcher pos ${launchMotor.currentPosition}")
                    if (homingButton) {
                        launchMotor.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
                        telemetry("homing")
                        homing = HomingState.HOMING
                    }
                }
            }
            if (intakeButton !== intakeButtonPrevious) {
                if (intakeButton) {
                    intakeToggle = !intakeToggle
                }
                intakeButtonPrevious = intakeButton
            }
            if (longChairButton !== longChairButtonPrevious) {
                if (longChairButton) {
                    longChairToggle = !longChairToggle
                }
                longChairButtonPrevious = longChairButton
            }
            if (longChairMotorButton !== longChairMotorButtonPrevious) {
                if (longChairMotorButton) {
                    longChairMotorToggle = !longChairMotorToggle
                }
                longChairMotorButtonPrevious = longChairMotorButton
            }
            if (longChairMotorToggle) {
                telemetry("LongChairMotors On")
                longChairMotor1.run()
                longChairMotor2.run()
            } else {
                telemetry("LongChairMotors Off")
                longChairMotor1.stop()
                longChairMotor2.stop()
            }
            if (longChairToggle) {
                telemetry("LongChair On")
                if (!longChairMotorToggle) {
                    longChairMotorToggle = true
                } else {
                    blockingServo.position = servoPosOn
                    if (launchMotor.currentPosition < launchPosOn) {
                        launchMotor.power = launchPower
                    } else {
                        launchMotor.stop()
                    }
                    intakeMotor.stop()
                }
            } else if (homing === HomingState.UNKNOWN) {
                telemetry("LongChair Off")
                if (launchMotor.currentPosition > launchPosOff && !endStop.isPressed) {
                    launchMotor.power = resetPower
                } else {
                    blockingServo.position = servoPosOff
                    launchMotor.stop()
                }
                if (intakeToggle) {
                    intakeMotor.run()
                } else {
                    intakeMotor.stop()
                }
            } else {
                launchMotor.stop()
                telemetry("LongChair Disabled")
            }
            false
        }
    }
}
