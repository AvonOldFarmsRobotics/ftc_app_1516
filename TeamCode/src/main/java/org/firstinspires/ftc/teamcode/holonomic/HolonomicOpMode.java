package org.firstinspires.ftc.teamcode.holonomic;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import framework.ftc.cobaltforge.CobaltForge;

/**
 * Created by Dummyc0m on 10/5/16.
 */
@TeleOp(name = "Holonomic")
public class HolonomicOpMode extends CobaltForge {
    @Override
    public void onInit() {
        //addDirective(new HolonomicRotateDirective());
        addDirective(new HolonomicTeleDirective());
    }
}
