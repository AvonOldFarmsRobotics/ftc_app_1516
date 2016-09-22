package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import framework.ftc.cobaltforge.AbstractDirective;
import framework.ftc.cobaltforge.Component;
import framework.ftc.cobaltforge.GamePad1;
import framework.ftc.cobaltforge.Inject;

/**
 * Sample JoystickDirective for 0.1.1 CobaltForge
 * Created by Dummyc0m on 9/21/16.
 */

public class JoystickDirective extends AbstractDirective {
    @Inject("motor1")
    DcMotor dcMotor1;

    @Inject("motor2")
    DcMotor dcMotor2;

    @GamePad1(Component.LEFT_STICK_Y)
    float leftY;

    @GamePad1(Component.RIGHT_STICK_Y)
    float rightY;

    @GamePad1(Component.B)
    boolean b;

    @Override
    public void onStart() {
        dcMotor1.setDirection(DcMotorSimple.Direction.REVERSE);
        dcMotor2.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    @Override
    public void onLoop() {
        if (b) {
            complete();
        }
        dcMotor1.setPower(leftY);
        dcMotor2.setPower(rightY);
        telemetry(leftY);
        telemetry(rightY);
    }
}
