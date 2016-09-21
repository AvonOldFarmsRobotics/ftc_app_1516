package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import framework.ftc.cobaltforge.CobaltForge;
import framework.ftc.cobaltforge.examples.ExampleDirective;

/**
 * Created by Dummyc0m on 9/21/16.
 * Example File
 */
@TeleOp(name = "SampleOpMode")
public class SampleOpMode extends CobaltForge {
    @Override
    public void onInit() {
        addDirective(new ExampleDirective());
    }
}
