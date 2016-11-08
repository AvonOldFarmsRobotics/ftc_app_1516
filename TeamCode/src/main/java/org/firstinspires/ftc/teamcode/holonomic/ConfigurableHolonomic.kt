package org.firstinspires.ftc.teamcode.holonomic

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import framework.ftc.cobaltforge.kobaltforge.annotation.State

/**
 * Configurable Example
 * Created by Dummyc0m on 11/7/16.
 */
@TeleOp(name = "Holonomic Configurable")
class ConfigurableHolonomic : HolonomicOpMode() {
    @State
    var variableVecUnit = 1 / Math.sqrt(2.0)

    override fun construct() {
        super.construct()
        onInit {
            vecUnit = variableVecUnit
        }
        onLoop {
            telemetry(variableVecUnit)
            false
        }
    }
}