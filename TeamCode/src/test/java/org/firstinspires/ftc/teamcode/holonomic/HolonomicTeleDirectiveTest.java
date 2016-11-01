//package org.firstinspires.ftc.teamcode.holonomic;
//
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorController;
//
//import org.junit.Test;
//
//import framework.ftc.cobaltforge.samples.Logger;
//
//import static org.mockito.Mockito.mock;
//
///**
// * Created by Dummyc0m on 10/5/16.
// */
//public class HolonomicTeleDirectiveTest {
//    HolonomicTeleDirective testing;
//
//    @org.junit.Before
//    public void setUp() throws Exception {
//        testing = new HolonomicTeleDirective();
//        DcMotor mockMotor = new DcMotor() {
//            @Override
//            public int getMaxSpeed() {
//                return 0;
//            }
//
//            @Override
//            public void setMaxSpeed(int encoderTicksPerSecond) {
//
//            }
//
//            @Override
//            public DcMotorController getController() {
//                return null;
//            }
//
//            @Override
//            public int getPortNumber() {
//                return 0;
//            }
//
//            @Override
//            public ZeroPowerBehavior getZeroPowerBehavior() {
//                return null;
//            }
//
//            @Override
//            public void setZeroPowerBehavior(ZeroPowerBehavior zeroPowerBehavior) {
//
//            }
//
//            @Override
//            public void setPowerFloat() {
//
//            }
//
//            @Override
//            public boolean getPowerFloat() {
//                return false;
//            }
//
//            @Override
//            public int getTargetPosition() {
//                return 0;
//            }
//
//            @Override
//            public void setTargetPosition(int position) {
//
//            }
//
//            @Override
//            public boolean isBusy() {
//                return false;
//            }
//
//            @Override
//            public int getCurrentPosition() {
//                return 0;
//            }
//
//            @Override
//            public RunMode getMode() {
//                return null;
//            }
//
//            @Override
//            public void setMode(RunMode mode) {
//
//            }
//
//            @Override
//            public Direction getDirection() {
//                return null;
//            }
//
//            @Override
//            public void setDirection(Direction direction) {
//
//            }
//
//            @Override
//            public double getPower() {
//                return 0;
//            }
//
//            @Override
//            public void setPower(double power) {
//                System.out.println(power);
//            }
//
//            @Override
//            public Manufacturer getManufacturer() {
//                return null;
//            }
//
//            @Override
//            public String getDeviceName() {
//                return null;
//            }
//
//            @Override
//            public String getConnectionInfo() {
//                return null;
//            }
//
//            @Override
//            public int getVersion() {
//                return 0;
//            }
//
//            @Override
//            public void resetDeviceConfigurationForOpMode() {
//
//            }
//
//            @Override
//            public void close() {
//
//            }
//        };
//        testing.motor1 = mockMotor;
//        testing.motor2 = mockMotor;
//        testing.motor3 = mockMotor;
//        testing.motor4 = mockMotor;
//        testing.logger = mock(Logger.class);
//    }
//
//    @Test
//    public void test() throws Exception {
//        testing.onStart();
//        System.out.println("run1");
//        testing.x = 1;
//        testing.y = 1;
//        testing.onLoop();
//        System.out.println("run2");
//        testing.x = 0;
//        testing.y = 1;
//        testing.onLoop();
//        System.out.println("run3");
//        testing.x = 1;
//        testing.y = 0;
//        testing.onLoop();
//        System.out.println("run4");
//        testing.x = -1;
//        testing.y = -1;
//        testing.onLoop();
//        System.out.println("run5");
//        testing.x = 1;
//        testing.y = -1;
//        testing.onLoop();
//        System.out.println("run6");
//        testing.x = 0;
//        testing.y = 0;
//        testing.onLoop();
//    }
//
//    @org.junit.After
//    public void tearDown() throws Exception {
//
//    }
//
//}