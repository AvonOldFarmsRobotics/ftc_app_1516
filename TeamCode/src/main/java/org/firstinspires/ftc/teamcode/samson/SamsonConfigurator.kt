package org.firstinspires.ftc.teamcode.samson

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import framework.ftc.cobaltforge.kobaltforge.android.StateOpMode

/**
 * Configures the two servo angles for Samson
 * Created by Dummyc0m on 12/4/16.
 */
@TeleOp(name = "SamsonTeleOpConfigurator", group = "Config")
class SamsonConfigurator : StateOpMode(SamsonPrimary::class.java)