package org.firstinspires.ftc.teamcode;

/**
 * Created by Dummyc0m on 9/30/16.
 */

public class PIDController {
    private double kP, kI, kD;

    public void setkP(double kP) {
        this.kP = kP;
    }

    public void setkI(double kI) {
        this.kI = kI;
    }

    public void setkD(double kD) {
        this.kD = kD;
    }
}
