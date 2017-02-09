package org.firstinspires.ftc.teamcode.lancer;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by Dummyc0m on 2/4/17.
 */
@TeleOp(name = "LancerJava")
public class LancerHolonomicJava extends OpMode {
    private HolonomicProgram program = new HolonomicProgram();

    private DcMotor motor1;

    private DcMotor motor2;

    private DcMotor motor3;

    private DcMotor motor4;

    @Override
    public void init() {
        motor1 = hardwareMap.dcMotor.get("motor1");
        motor2 = hardwareMap.dcMotor.get("motor2");
        motor3 = hardwareMap.dcMotor.get("motor3");
        motor4 = hardwareMap.dcMotor.get("motor4");

        motor1.setDirection(DcMotorSimple.Direction.REVERSE);
        motor2.setDirection(DcMotorSimple.Direction.FORWARD);
        motor3.setDirection(DcMotorSimple.Direction.FORWARD);
        motor4.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Override
    public void loop() {
        double[] powerArray = program.calculateMotorPower(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.right_stick_y);
        motor1.setPower(powerArray[0]);
        motor2.setPower(powerArray[1]);
        motor3.setPower(powerArray[2]);
        motor4.setPower(powerArray[3]);
    }
}
