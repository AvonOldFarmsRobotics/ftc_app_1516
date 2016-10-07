package org.firstinspires.ftc.teamcode.holonomic;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

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
    double root2 = 0.5; //Math.sqrt(2);
    double vec1X = -1;
    double vec1Y = -1;
    double vec2X = -1;
    double vex2Y = 1;
    double dir2Cached = direction(vec2X, vex2Y);
    double dir1Cached = direction(vec1X, vec1Y);

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
    @GamePad1(Component.RIGHT_STICK_X)
    float x;
    @GamePad1(Component.RIGHT_STICK_Y)
    float y;
    @GamePad1(Component.LEFT_STICK_X)
    float leftX;

    @Override
    public void onStart() {
        logger.setSize(10);
        logger.refresh();
        motor1.setDirection(DcMotorSimple.Direction.FORWARD);
        motor2.setDirection(DcMotorSimple.Direction.REVERSE);
        motor3.setDirection(DcMotorSimple.Direction.REVERSE);
        motor4.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    @Override
    public void onLoop() {
        x = -x;
        double signedAngle2 = dir2Cached - direction(x, y);
        double signedAngle1 = dir1Cached - direction(x, y);

        double magnitudeCached = magnitude(x, y);

        double val1 = clamp(dotProduct(root2, magnitudeCached, signedAngle1));
        double val2 = clamp(dotProduct(root2, magnitudeCached, signedAngle2));

        if (Math.abs(val1) > 0.1 || Math.abs(val2) > 0.1) {
            motor1.setPower(val1);
            motor2.setPower(val2);
            motor3.setPower(val1);
            motor4.setPower(val2);
        } else if (Math.abs(leftX) > 0.1) {
            motor1.setPower(leftX);
            motor2.setPower(-leftX);
            motor3.setPower(-leftX);
            motor4.setPower(leftX);
        } else {
            motor1.setPower(0);
            motor2.setPower(0);
            motor3.setPower(0);
            motor4.setPower(0);
        }

        logger.refresh();
    }

    private double dotProduct(double a, double b, double theta) {
        return a * b * Math.cos(theta);
    }

    private double magnitude(double x, double y) {
        return Math.sqrt(x * x + y * y);
    }

    /**
     * @param x
     * @param y
     * @return theta
     */
    private double direction(double x, double y) {
        return Math.atan2(y, x);
    }

    private double clamp(double a) {
        return a > 1 ? 1 : a < -1 ? -1 : a;
        //return a;
    }
}