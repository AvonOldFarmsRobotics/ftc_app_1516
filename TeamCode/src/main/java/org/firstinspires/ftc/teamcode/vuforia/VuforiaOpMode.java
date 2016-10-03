package org.firstinspires.ftc.teamcode.vuforia;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import framework.ftc.cobaltforge.CobaltForge;

/**
 * Created by Dummyc0m on 10/3/16.
 */
@Autonomous(name = "VuforiaOpMode")
public class VuforiaOpMode extends CobaltForge {
    @Override
    public void onInit() {
        setName("Vuforia");
        addDirective(new VuforiaTestDirective());
    }
}
