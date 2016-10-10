package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import framework.ftc.cobaltforge.AbstractDirective;
import framework.ftc.cobaltforge.Inject;

/**
 * Created by peiqi on 2016/10/5.
 */

public class OneJoystickPolarDirective extends AbstractDirective {

    static double power, angle, targetL, targetR;
    @Inject.DcMotor("motor1")
    DcMotor dcMotorL;
    @Inject.DcMotor("motor2")
    DcMotor dcMotorR;
    //reverse
    @Inject.GamePad1(Inject.GamePadComponent.LEFT_STICK_Y)
    float Y;
    @Inject.GamePad1(Inject.GamePadComponent.LEFT_STICK_X)
    float X;
    @Inject.GamePad1(Inject.GamePadComponent.B)
    boolean b;

    @Override
    public void onStart() {
        dcMotorL.setDirection(DcMotorSimple.Direction.REVERSE);
        dcMotorR.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    @Override
    public void onLoop() {
        if (b) {
            complete();
        }

        power = Math.min(Math.sqrt(X * X + Y * Y),1d);
        if (X == 0f) {
            if (-Y >= 0f) {
                //angle = Math.PI/2;
                targetL = 1;
                targetR = 1;
            } else {
                //angle = Math.PI*1.5;
                targetL = -1;
                targetR = -1;
            }
        } else if (Y == 0f) {
            if (X >= 0f) {
                //angle = 0d;
                targetL = 1;
                targetR = -1;
            } else {
                //angle = Math.PI;
                targetL = -1;
                targetR = 1;
            }
        } else {
            angle = Math.atan(-Y / X);//since Y is reverse
            if (angle >= 0d) {
                if (X < 0f) {
                    //angle += Math.PI; quarter3
                    targetL = -angle / (Math.PI / 2);
                    targetR = -1;
                } else {
                    //quarter1
                    targetL = 1;
                    targetR = angle / (Math.PI / 2);
                }
            } else {
                if (X < 0f) {
                    //angle += Math.PI/2; quarter2
                    targetL = -angle / (Math.PI / 2);
                    targetR = 1;
                } else {
                    //angle += Math.PI*1.5; quarter4
                    targetL = -1;
                    targetR = angle / (Math.PI / 2);
                }
            }
        }

        dcMotorL.setPower(targetL * power);
        dcMotorR.setPower(targetR * power);

        telemetry(targetL);
        telemetry(targetR);
        telemetry(power);
//        System.out.println(targetL);
//        System.out.println(targetR);
//        System.out.println(power);

    }

}
