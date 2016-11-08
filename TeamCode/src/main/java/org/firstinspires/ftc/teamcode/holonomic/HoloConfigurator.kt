package org.firstinspires.ftc.teamcode.holonomic

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import framework.ftc.cobaltforge.kobaltforge.android.StateOpMode

/**
 * Example Configurator
 * Created by Dummyc0m on 11/7/16.
 */
@TeleOp(name = "Holonomic Configurator")
class HoloConfigurator : StateOpMode(ConfigurableHolonomic::class.java)