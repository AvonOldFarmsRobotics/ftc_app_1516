package org.firstinspires.ftc.teamcode.vuforia;

import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import framework.ftc.cobaltforge.AbstractDirective;
import framework.ftc.cobaltforge.Inject;

/**
 * Created by Dummyc0m on 10/3/16.
 */
public class VuforiaTestDirective extends AbstractDirective {
    private
    @Inject
    VuforiaService service;

    @Override
    public void onInit() {
        service.init("Ab8yFGP/////AAAAGQlfa7Qvw0Pav/J8eXn0o4Zqau61L3sjnqy7DfiS4f5kB8O/kUb0rPXj9b1tW3Gj04dF4WP1FLF6Ya41L/w9y58CrcG0FCTXUPyPs8lr436LDDzI9asKg9MXGPgEXjzqx7eEgy7fycAh1V+FQ+Iqq4jlOV3pzzJeezeMaM/JZkwIWofrjyKwjwIsCS4SpdjTw5plC7Hugj4I3Qj9dS3G/YiBmBe/3QnF/SQPCO2t5OVkqmNXW+7MT549ui3EbvTxDMxxv6S8yP/dmFmxu+bEHKKqJZzXfXAcZ5sjhDUpbCn1k96EkS4NNvNsxAgwynVKF7+B8wYUU7kToLd2nSw3nH0Z8gKNpwHGF/bi2yKUs06j");
        VuforiaTrackables trackables = service.loadAssets("FTC_2016-17");
        VuforiaTrackable wheelsTrackable = trackables.get(0);
        wheelsTrackable.setName("Wheel");
        VuforiaTrackable toolsTrackable = trackables.get(1);
        VuforiaTrackable legosTrackable = trackables.get(2);
        VuforiaTrackable gearsTrackable = trackables.get(3);

        OpenGLMatrix wheelsLocation = OpenGLMatrix
                .translation(0, service.getMmFTCFieldWidth() / 2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 0, 0));
        wheelsTrackable.setLocation(wheelsLocation);
        RobotLog.ii("VuforiaTestDirective", "wheels=%s", wheelsLocation.formatAsTransform());


        OpenGLMatrix toolsLocation = OpenGLMatrix
                .translation(0, service.getMmFTCFieldWidth() / 2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 0, 0));
        toolsTrackable.setLocation(toolsLocation);
        RobotLog.ii("VuforiaTestDirective", "tools=%s", toolsLocation.formatAsTransform());

        OpenGLMatrix legosLocation = OpenGLMatrix
                .translation(0, service.getMmFTCFieldWidth() / 2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 0, 0));
        legosTrackable.setLocation(legosLocation);
        RobotLog.ii("VuforiaTestDirective", "legos=%s", wheelsLocation.formatAsTransform());

        OpenGLMatrix gearsLocation = OpenGLMatrix
                .translation(0, service.getMmFTCFieldWidth() / 2, 0)
                .multiplied(Orientation.getRotationMatrix(
                        /* First, in the fixed (field) coordinate system, we rotate 90deg in X */
                        AxesReference.EXTRINSIC, AxesOrder.XZX,
                        AngleUnit.DEGREES, 90, 0, 0));
        gearsTrackable.setLocation(gearsLocation);
        RobotLog.ii("VuforiaTestDirective", "gears=%s", gearsLocation.formatAsTransform());

        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix
                .translation(service.getMmBotWidth() / 2, 0, 0)
                .multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.YZY,
                        AngleUnit.DEGREES, -90, 0, 0));
        service.setPhoneLocation(phoneLocationOnRobot);
        RobotLog.ii("VuforiaTestDirective", "phone=%s", phoneLocationOnRobot.formatAsTransform());
    }

    @Override
    public void onStart() {
        service.start();
    }

    @Override
    public void onLoop() {
        service.loop();
        OpenGLMatrix robotLocation = service.getLastLocation();
    }
}
