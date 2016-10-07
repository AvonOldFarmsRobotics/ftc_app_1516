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

    double power, angle, targetL, targetR;

    @Override
    public void onStart() {
        dcMotorL.setDirection(DcMotorSimple.Direction.REVERSE);
        dcMotorR.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    @Override
    public void onLoop() {
        if (b){
            complete();
        }

        //if (-Y > 0.1f || X > 0.1f) {
        power = Math.sqrt(X * X + Y * Y);
        if (X == 0f){
            if (-Y >= 0f){
                angle = Math.PI/2;
            }else{
                angle = Math.PI*1.5;
            }
        }else if(Y == 0f){
            if (X >= 0f){
                angle = 0d;
            }else {
                angle = Math.PI;
            }
        }else {
            angle = Math.atan(-Y / X);//since Y is reverse
            if (angle >= 0d){
                if (X < 0f){
                    angle += Math.PI;
                }
            }else {
                if (X < 0f){
                    angle += Math.PI/2;
                }else {
                    angle += Math.PI*1.5;
                }
            }
        }


        if (angle >= Math.PI*1.5){
        //[270,360)

        }else if (angle >= Math.PI){
        //[180,270)


        }else if (angle >= Math.PI/2){
        //[90,180)
            targetL = 1 - (angle - Math.PI/2) / (Math.PI/2);
            targetR = 1;
        }else {
        //[0,90)
            targetL = 1;
            targetR = angle / (Math.PI/2);
        }

        //}

        telemetry(X);
        telemetry(Y);

    }
}
