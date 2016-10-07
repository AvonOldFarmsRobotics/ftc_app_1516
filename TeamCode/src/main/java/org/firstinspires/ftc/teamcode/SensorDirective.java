//package org.firstinspires.ftc.teamcode;
//
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.LightSensor;
//
//import framework.ftc.cobaltforge.AbstractDirective;
//import framework.ftc.cobaltforge.Device;
//import framework.ftc.cobaltforge.Inject;
//import framework.ftc.cobaltforge.samples.Logger;
//import framework.ftc.cobaltforge.samples.LoopStatistics;
//
///**
// * Created by Dummyc0m on 9/29/16.
// */
//public class SensorDirective extends AbstractDirective {
//    @Device
//    LightSensor lightSensor;
//
//    @Device
//    DcMotor leftMotor;
//
//    @Device
//    DcMotor rightMotor;
//
//    @Inject
//    Logger logger;
//
//    @Inject
//    LoopStatistics statistics;
//
//    @Inject
//    PIDController pidController;
//
//    @Override
//    public void onStart() {
//        lightSensor.enableLed(true);
//        logger.setSize(10);
//    }
//
//    @Override
//    public void onLoop() {
//        statistics.loops++;
//        telemetry("Raw: " + lightSensor.getRawLightDetected());
//        telemetry("Normal: " + lightSensor.getLightDetected());
//        if (statistics.loops % 100 == 0) {
//            logger.append("Light: ")
//                    .append(lightSensor.getLightDetected());
//        }
//        logger.refresh();
//    }
//}
