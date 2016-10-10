package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import framework.ftc.cobaltforge.CobaltForge;
import framework.ftc.cobaltforge.examples.ExampleDirective;

/**
 * Created by Dummyc0m on 9/21/16.
 * Example File
 */
@TeleOp(name = "WOTOpMode")
public class WOTOpMode extends CobaltForge {
    @Override
    public void onInit() {
        addDirective(new OneJoystickPolarDirective());
    }
}
