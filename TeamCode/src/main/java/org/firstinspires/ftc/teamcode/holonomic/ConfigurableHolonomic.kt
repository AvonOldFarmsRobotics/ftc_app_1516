package org.firstinspires.ftc.teamcode.holonomic

import framework.ftc.cobaltforge.kobaltforge.annotation.Component
import framework.ftc.cobaltforge.kobaltforge.annotation.GamePad1
import framework.ftc.cobaltforge.kobaltforge.annotation.State

/**
 * Configurable Example
 * Created by Dummyc0m on 11/7/16.
 */
//@TeleOp(name = "Holonomic Configurable")
class ConfigurableHolonomic : HolonomicOpMode() {
    @State
    var variableVecUnit = 1 / Math.sqrt(2.0)
    lateinit var someString: String

    @GamePad1(Component.B)
    var b = false

    override fun construct() {
        super.construct()
        onInit {
            someString = get("someString", "this is the string") as String
            vecUnit = variableVecUnit
        }
        onLoop {
            if (b) {
                someString = "other"
                put("someString", someString)
                saveConfig()
            }
            telemetry(someString)
            telemetry(variableVecUnit)
            false
        }
    }
}