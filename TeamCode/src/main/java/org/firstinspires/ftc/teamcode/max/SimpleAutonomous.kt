package org.firstinspires.ftc.teamcode.max

import org.firstinspires.ftc.teamcode.holonomic.HolonomicOpMode

/**
 * Created by Dummyc0m on 11/11/16.
 */
//@Autonomous(name = "Just to have it")
class SimpleAutonomous : HolonomicOpMode() {
    override fun construct() {
        onLoop {
            telemetry("We are great!")
            false
        }
    }
}