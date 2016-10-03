package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import framework.ftc.cobaltforge.AbstractDirective;
import framework.ftc.cobaltforge.Component;
import framework.ftc.cobaltforge.Device;
import framework.ftc.cobaltforge.GamePad1;

/**
 * Sample JoystickDirective for 0.1.1 CobaltForge
 * Created by Dummyc0m on 9/21/16.
 */

public class JoystickDirective extends AbstractDirective {
    @Device
    DcMotor leftMotor;

    @Device
    DcMotor rightMotor;

    @GamePad1(Component.LEFT_STICK_Y)
    float leftY;

    @GamePad1(Component.RIGHT_STICK_Y)
    float rightY;

    @GamePad1(Component.B)
    boolean b;

    @Override
    public void onStart() {
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        rightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    @Override
    public void onLoop() {
        if (b) {
            complete();
        }
        leftMotor.setPower(leftY);
        rightMotor.setPower(rightY);
        telemetry(leftY);
        telemetry(rightY);
    }
}
