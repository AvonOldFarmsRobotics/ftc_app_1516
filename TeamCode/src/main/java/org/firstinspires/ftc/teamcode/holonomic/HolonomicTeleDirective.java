package org.firstinspires.ftc.teamcode.holonomic;

import com.qualcomm.robotcore.hardware.DcMotor;

import framework.ftc.cobaltforge.AbstractDirective;
import framework.ftc.cobaltforge.Component;
import framework.ftc.cobaltforge.Device;
import framework.ftc.cobaltforge.GamePad1;
import framework.ftc.cobaltforge.Inject;
import framework.ftc.cobaltforge.samples.Logger;

/**
 * Created by Dummyc0m on 10/5/16.
 */

public class HolonomicTeleDirective extends AbstractDirective {
    @GamePad1(Component.RIGHT_STICK_X)
    float rightX;

    @GamePad1(Component.RIGHT_STICK_Y)
    float rightY;

    @Device
    DcMotor motor1;

    @Device
    DcMotor motor2;

    @Device
    DcMotor motor3;

    @Device
    DcMotor motor4;

    @Inject
    Logger logger;

    @Override
    public void onStart() {
        logger.setSize(10);
    }

    @Override
    public void onLoop() {

        logger.refresh();
    }

    private double dotProduct(double a, double b, double theta) {
        return a * b * Math.cos(theta);
    }

    private double magnitude(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

    // return theta
    private double direction(double x, double y) {
        return Math.atan2(y, x);
    }
}