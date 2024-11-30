package org.firstinspires.ftc.teamcode.EPIC.Robot;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.EPIC.Components.*;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.*;
import org.firstinspires.ftc.teamcode.EPIC.Motion.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ClawStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.DriveStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.SliderStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.WristStates;
import org.firstinspires.ftc.teamcode.EPIC.Sensors.MyColorRangeSensor;
import org.firstinspires.ftc.teamcode.EPIC.Sensors.MyTouchSensor;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot implements IColorListener, ITouchListener, IClawListener, IArmListener, ISClawListener, IWristListener, ISliderListener, IMecanumListener {

    public Claw odysseyClaw;
    public Slider_PIDF odysseySlider;
    public Arm_PIDF odysseyArm;
    public Wrist odysseyWrist;
    public Mecanum_Wheels odysseyWheels;
    public MyTouchSensor touchSensor;

    public boolean isAutonomous = false;
    private LinearOpMode parent;
    private Telemetry telemetry;
    private String alliance = "";
    public MyColorRangeSensor colorSensor;

    public Robot(LinearOpMode parent, String alliance) {
        odysseyClaw = new Claw(parent.hardwareMap);
        odysseySlider = new Slider_PIDF(parent.hardwareMap);
        odysseyArm = new Arm_PIDF(parent.hardwareMap);
        odysseyWrist = new Wrist(parent.hardwareMap);
        odysseyWheels = new Mecanum_Wheels(parent.hardwareMap);
        this.parent = parent;
        this.telemetry = parent.telemetry;
        this.alliance = alliance;
    }

    public void setIsAutonomous(boolean isAutonomous) {
        this.isAutonomous = isAutonomous;
        odysseyClaw.setIsAutonomous(isAutonomous);
        odysseySlider.setIsAutonomous(isAutonomous);
        odysseyArm.setIsAutonomous(isAutonomous);
        odysseyWrist.setIsAutonomous(isAutonomous);
        odysseyWheels.setIsAutonomous(isAutonomous);
    }

    public void initialize() {
        odysseyClaw.setParent(this.parent);
        odysseySlider.setParent(this.parent);
        odysseyArm.setParent(this.parent);
        odysseyWrist.setParent(this.parent);
        odysseyWheels.setParent(this.parent);
        odysseyClaw.setTelemetry(this.telemetry);
        odysseySlider.setTelemetry(this.telemetry);
        odysseyArm.setTelemetry(this.telemetry);
        odysseyWrist.setTelemetry(this.telemetry);
        odysseyWheels.setTelemetry(this.telemetry);
        odysseyArm.addArmListener(this);
        odysseyWrist.addWristListener(this);
        odysseySlider.addSliderListener(this);
        odysseyWheels.addMecanumListener(this);
        odysseyClaw.addClawListener(this);
        odysseyClaw.initialize();
        odysseySlider.initialize(0, 0, 0);
        odysseyArm.initialize(0, 0, 0);
        odysseyWrist.initialize();
        odysseyWheels.initialize();
    }

    @Override
    public void colorPicker(ColorEventObject event) {
        if (this.parent.opModeIsActive()) {
//            double distance = event.getDistance();
//            telemetry.addData("distance", distance);
//            telemetry.addData("color", event.getColor());
//            telemetry.update();
//            parent.sleep(1000);
//            // odysseyWheels.encoderDrive(0.6, distance, distance, distance, distance, 1);
//            parent.sleep(2000);
        }
    }

    @Override
    public void touchClicked(TouchEventObject event) {
        if (this.parent.opModeIsActive()) {
            Thread tc = new Thread() {
                public void run() {
                    if (event.getButtonStatus()) {
                        // odysseyWheels.move(0.6, 0, 0, 0);
                    } else {
                        // odysseyWheels.move(0, 0, 0, 0);
                    }
                }
            };
            tc.start();
        }
    }

    @Override
    public void runClaw(ClawEventObject event) {
        if (this.parent.opModeIsActive()) {
            ClawStates newState = event.getNewState();
            Thread tc = new Thread() {
                public void run() {
                    switch (newState) {
                        case OPEN:
                            try {
                                Thread.sleep(25);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            if (odysseyArm.stateArm == ArmStates.DEPOSITING) {
                                odysseyArm.move(ArmStates.READY_TO_DEPOSIT);
                            }
                            break;
                        case HOLDING_SAMPLE_PORTRAIT:
                            break;
                        case HOLDING_SAMPLE_LANDSCAPE:
                            break;
                        default:
                            break;
                    }
                }
            };
            tc.start();
        }
    }

    @Override
    public void onArmMove(ArmEventObject event) {
        if (parent.opModeIsActive()) {
            ArmStates newState = event.getNewState();
            Thread tc = new Thread() {
                public void run() {
                    switch (newState) {
                        case LOWERED:
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            odysseyWrist.setPos(WristStates.DEPOSITING_SAMPLE);
                            telemetry.addData("Arm Thread", "Ready to pickup sample");
                            break;
                        case INITIALIZED:
                            System.out.println("Arm is initialized");
                            break;
                        case READY_TO_DEPOSIT:
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            if ((odysseyClaw.stateClaw != ClawStates.OPEN) && (odysseySlider.stateSlider == SliderStates.RETRACTED)) {
                                odysseyArm.move(ArmStates.DEPOSITING);
                            }
                            telemetry.addData("Arm Thread", "Ready to deposit sample");
                            break;
                        case DEPOSITING:
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            odysseyWrist.setPos(WristStates.DEPOSITING_SAMPLE);
                            telemetry.addData("Arm Thread", "Sample ready for deposit");
                        case NEUTRAL:
                            telemetry.addData("Arm Thread", "Arm is neutral");
                            break;
                        default:
                            System.out.println("Arm moved to an unknown state.");
                            break;
                    }
                }
            };
            tc.start();
        }
    }

    @Override
    public void onWristMove(WristEventObject event) {
        if (parent.opModeIsActive()) {// Implementing onWristMove method
            WristStates newState = event.getNewState();
            Thread tc = new Thread() {
                public void run () {
                    switch (newState) {
                        case NEUTRAL:
                            telemetry.addData("Wrist Thread", "Wrist at rest");
                            break;
                        case DEPOSITING_SAMPLE:
                            try {
                                Thread.sleep(60);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            if (odysseyArm.stateArm == ArmStates.DEPOSITING) {
                                odysseyClaw.move(ClawStates.OPEN);
                            }
                            break;
                        case PICKING_UP_SAMPLE:
                            telemetry.addData("Wrist Thread", "Ready to pickup sample");
                        case INITIALIZING:
                            break;
                        default:

                            break;
                    }
                }
            };
            tc.start();
        }
    }

    @Override
    public void onSliderMove(SliderEventObject event) { //Threading Complete. Do not modify!
        if (parent.opModeIsActive()) { // Ensure the opMode is active
            SliderStates newState = event.getSliderState();

            Thread tc = new Thread() {
                public void run() {
                    switch (newState) {
                        case RETRACTED:
                            telemetry.addData("Slider Thread", "Slider is retracted");
                            break;
                        case HIGH_BUCKET:
                            telemetry.addData("Slider Thread", "Slider is at high bucket");
                            break;
                        case LOW_HANG:
                            break;
                        case LOW_HANG_START:
                            telemetry.addData("Slider Thread", "Slider ready to hang! " +
                                    " Get in position for hanging!");
                            break;
                        default:
                            System.out.println("Slider moved to an unknown state.");
                            break;
                    }
                }
            };
            tc.start();
        }
    }

    @Override
    public void mecanumActivity(MecanumEventObject event) {
        if (parent.opModeIsActive()) {
            DriveStates newState = event.getNewState();

            Thread tc = new Thread() {
                public void run () {
                    switch (newState) {
                        case IDLE:

                            break;
                        case ROTATED:

                            break;
                        case STRAFING_RIGHT:

                            break;
                        case STRAFING_LEFT:

                            break;
                        case INITIALIZED:

                            break;
                        case MOVE_FORWARDS:

                            break;
                        case MOVE_BACKWARDS:

                            break;
                        default:

                            break;
                    }
                }
            };
            tc.start();
        }
    }

    @Override
    public void specClawMove(SClawEventObject event) {
        if (parent.opModeIsActive()) {
            SpecimenClaw.SClawStates newState = event.getNewState();

            Thread tc = new Thread() {
                public void run() {
                    switch(newState) {
                        case OPEN:
                            break;
                        case HOLDING_SPECIMEN:
                            break;
                        default:
                            break;
                    }
                }
            };
        }
    }
}