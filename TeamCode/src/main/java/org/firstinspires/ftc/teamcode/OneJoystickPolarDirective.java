package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import framework.ftc.cobaltforge.AbstractDirective;
import framework.ftc.cobaltforge.Inject;

/**
 * Created by peiqi on 2016/10/5.
 */

public class OneJoystickPolarDirective extends AbstractDirective {

    @Inject.DcMotor("motor1")
    DcMotor dcMotorL;

    @Inject.DcMotor("motor2")
    DcMotor dcMotorR;

    @Inject.GamePad1(Inject.GamePadComponent.LEFT_STICK_Y)
    float Y;
    //reverse

    @Inject.GamePad1(Inject.GamePadComponent.LEFT_STICK_X)
    float X;

    @Inject.GamePad1(Inject.GamePadComponent.B)
    boolean b;

    double power, angle;

    @Override
    public void onStart() {
        dcMotorL.setDirection(DcMotorSimple.Direction.FORWARD);
        dcMotorR.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void onLoop() {
        if (b){
            complete();
        }

        //if (-Y > 0.1f || X > 0.1f) {
        power = Math.sqrt(X * X + Y * Y);
        angle = Math.atan(-Y / X);//since Y is reverse



        //}

        telemetry(Y);
        telemetry(X);
    }
}
