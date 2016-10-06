package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

import framework.ftc.cobaltforge.AbstractDirective;
import framework.ftc.cobaltforge.Inject;

/**
 * Created by peiqi on 2016/10/5.
 */

public class OneJoystickPolarDirective extends AbstractDirective {

    @Inject.DcMotor("motor1")
    DcMotor dcMotor1;

    @Inject.DcMotor("motor2")
    DcMotor dcMotor2;

    @Inject.GamePad1(Inject.GamePadComponent.LEFT_STICK_Y)
    float leftY;

    @Inject.GamePad1(Inject.GamePadComponent.RIGHT_STICK_Y)
    float rightY;

    @Inject.GamePad1(Inject.GamePadComponent.B)
    boolean b;


    @Override
    public void onStart() {

    }

    @Override
    public void onLoop() {

    }
}
