package org.firstinspires.ftc.teamcode.holonomic;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import framework.ftc.cobaltforge.AbstractDirective;
import framework.ftc.cobaltforge.Component;
import framework.ftc.cobaltforge.Device;
import framework.ftc.cobaltforge.GamePad1;

/**
 * Created by Dummyc0m on 10/6/16.
 */

public class HolonomicRotateDirective extends AbstractDirective {
    @Device
    DcMotor motor1;

    @Device
    DcMotor motor2;

    @Device
    DcMotor motor3;

    @Device
    DcMotor motor4;

    @GamePad1(Component.LEFT_STICK_X)
    float x;

    @Override
    public void onStart() {
        motor1.setDirection(DcMotorSimple.Direction.FORWARD);
        motor2.setDirection(DcMotorSimple.Direction.REVERSE);
        motor3.setDirection(DcMotorSimple.Direction.REVERSE);
        motor4.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    @Override
    public void onLoop() {
        if (Math.abs(x) > 0.1) {
            motor1.setPower(-x);
            motor2.setPower(x);
            motor3.setPower(x);
            motor4.setPower(-x);
        }
    }
}
