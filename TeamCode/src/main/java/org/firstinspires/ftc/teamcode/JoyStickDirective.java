package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import framework.ftc.cobaltforge.AbstractDirective;
import framework.ftc.cobaltforge.Inject;

/**
 * Created by peiqi on 2016/9/21.
 */

public class JoyStickDirective extends AbstractDirective {

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
        dcMotor1.setDirection(DcMotorSimple.Direction.FORWARD);
        dcMotor2.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void onLoop() {
        if (b){
            complete();
        }
        dcMotor1.setPower(leftY);
        dcMotor2.setPower(rightY);
        telemetry(leftY);
        telemetry(rightY);
    }
}
