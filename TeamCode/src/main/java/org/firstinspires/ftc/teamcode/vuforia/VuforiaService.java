package org.firstinspires.ftc.teamcode.vuforia;

import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import framework.ftc.cobaltforge.CobaltForge;
import framework.ftc.cobaltforge.Inject;

/**
 * Created by Dummyc0m on 10/3/16.
 */
public class VuforiaService {
    @Inject
    private CobaltForge cf;

    private OpenGLMatrix lastLocation;

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    private VuforiaTrackables trackables;

    private VuforiaLocalizer.Parameters parameters;

    private OpenGLMatrix phoneLocation;

    private float mmPerInch = 25.4f;
    private float mmBotWidth = 18 * mmPerInch;            // ... or whatever is right for your robot
    private float mmFTCFieldWidth = (12 * 12 - 2) * mmPerInch;   // the FTC field is ~11'10" center-to-center of the glass panels

    public void init(String license) {
        parameters = new VuforiaLocalizer.Parameters(com.qualcomm.ftcrobotcontroller.R.id.cameraMonitorViewId);
        parameters.vuforiaLicenseKey = license;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforia = ClassFactory.createVuforiaLocalizer(parameters);
    }

    public void setPhoneLocation(OpenGLMatrix phoneLocation) {
        RobotLog.ii("VuforiaService", "phone=%s", phoneLocation.formatAsTransform());
        this.phoneLocation = phoneLocation;
    }

    public void start() {
        for (VuforiaTrackable trackable : trackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(phoneLocation, parameters.cameraDirection);
        }
    }

    public void loop() {
        trackables.activate();
        for (VuforiaTrackable trackable : trackables) {
            /**
             * getUpdatedRobotLocation() will return null if no new information is available since
             * the last time that call was made, or if the trackable is not currently visible.
             * getRobotLocation() will return null if the trackable is not currently visible.
             */
            cf.telemetry.addData(trackable.getName(), ((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible() ? "Visible" : "Not Visible");    //

            OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
            if (robotLocationTransform != null) {
                lastLocation = robotLocationTransform;
            }
        }
        /**
         * Provide feedback as to where the robot was last located (if we know).
         */
        if (lastLocation != null) {
            //  RobotLog.vv(TAG, "robot=%s", format(lastLocation));
            cf.telemetry.addData("Pos", lastLocation.formatAsTransform());
        } else {
            cf.telemetry.addData("Pos", "Unknown");
        }
        cf.telemetry.update();
    }

    public VuforiaTrackables loadAssets(String name) {
        return trackables = this.vuforia.loadTrackablesFromAsset(name);
    }

    public float getMmPerInch() {
        return mmPerInch;
    }

    public float getMmBotWidth() {
        return mmBotWidth;
    }

    public float getMmFTCFieldWidth() {
        return mmFTCFieldWidth;
    }

    public void setBotWidth(float mmBotWidth) {
        this.mmBotWidth = mmBotWidth;
    }

    public OpenGLMatrix getLastLocation() {
        return lastLocation;
    }
}
