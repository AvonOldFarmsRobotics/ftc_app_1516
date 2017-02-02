package org.firstinspires.ftc.teamcode.max

import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import framework.ftc.cobaltforge.kobaltforge.annotation.Component
import framework.ftc.cobaltforge.kobaltforge.annotation.Device
import framework.ftc.cobaltforge.kobaltforge.annotation.GamePad2
import framework.ftc.cobaltforge.kobaltforge.annotation.State
import org.firstinspires.ftc.teamcode.holonomic.HolonomicOpMode

/**
 * Created by Dummyc0m on 11/10/16.
 */
//@TeleOp(name = "MaxDrive --> USE THIS <--")
class MaxDrive2 : HolonomicOpMode() {
    @Device
    lateinit var launcher: DcMotor

    @Device
    lateinit var lifter: DcMotor

    @State
    var inputScale = 0.9

    @GamePad2(Component.RIGHT_TRIGGER)
    var launchTrigger = 0f

    @GamePad2(Component.LEFT_BUMPER)
    var launchPowerUp = false

    @GamePad2(Component.LEFT_TRIGGER)
    var launchTriggerNegative = 0f

    @GamePad2(Component.A)
    var lifterToggle = false

    var lastLifter = false

    var lifting = false

    @GamePad2(Component.B)
    var lifterReverse = false

    override fun construct() {
        super.construct()
        onInit {
            launcher.direction = DcMotorSimple.Direction.REVERSE
            lifter.direction = DcMotorSimple.Direction.FORWARD
        }
        onLoop {
            if (!((lifterToggle && lastLifter) || (!lastLifter && !lifterToggle))) {
                lastLifter = lifterToggle
                if (lifterToggle) {
                    lifting = !lifting
                }
            }
            if (lifting && launchTrigger <= Double.MIN_VALUE) {
                if (lifterReverse) {
                    lifter.power = -1.0
                } else {
                    lifter.power = 1.0
                }
            } else {
                lifter.power = 0.0
            }
            launcher.power = (launchTrigger.toDouble() - launchTriggerNegative.toDouble()) * (if (launchPowerUp) 1.0 else inputScale)
            false
        }
    }
}