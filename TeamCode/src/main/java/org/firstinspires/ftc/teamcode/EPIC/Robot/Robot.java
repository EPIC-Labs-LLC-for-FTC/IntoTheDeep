package org.firstinspires.ftc.teamcode.EPIC.Robot;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.EPIC.Components.Claw;
import org.firstinspires.ftc.teamcode.EPIC.Components.Slider;
import org.firstinspires.ftc.teamcode.EPIC.Components.Arm;
import org.firstinspires.ftc.teamcode.EPIC.Components.Wrist;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.ClawEventObject;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.ColorEventObject;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.IClawListener;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.IColorListener;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.ITouchListener;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.TouchEventObject;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.ArmEventObject;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.IArmListener;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.IWristListener;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.WristEventObject;
import org.firstinspires.ftc.teamcode.EPIC.Motion.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.WristStates;
import org.firstinspires.ftc.teamcode.EPIC.Sensors.MyColorRangeSensor;
import org.firstinspires.ftc.teamcode.EPIC.Sensors.MyTouchSensor;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot implements IColorListener, ITouchListener, IClawListener, IArmListener, IWristListener { // Implement IWristListener
    public Claw odysseyClaw;
    public Slider odysseySlider;
    public Arm odysseyArm;
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
        odysseySlider = new Slider(parent.hardwareMap);
        odysseyArm = new Arm(parent.hardwareMap);
        odysseyWrist = new Wrist(parent.hardwareMap);
        odysseyWheels = new Mecanum_Wheels(parent.hardwareMap);
        odysseyClaw.setParent(parent);
        odysseySlider.setParent(parent);
        odysseyArm.setParent(parent);
        odysseyWrist.setParent(parent);
        odysseyWheels.setParent(parent);
        odysseyClaw.setTelemetry(parent.telemetry);
        odysseySlider.setTelemetry(parent.telemetry);
        odysseyArm.setTelemetry(parent.telemetry);
        odysseyWrist.setTelemetry(parent.telemetry);
        odysseyWheels.setTelemetry(parent.telemetry);
        this.parent = parent;
        this.telemetry = parent.telemetry;
        this.alliance = alliance;


        odysseyArm.addArmListener(this);

   
        odysseyWrist.addWristListener(this);
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
        odysseyClaw.initialize();
        odysseySlider.initialize();
        odysseyArm.initialize();
        odysseyWrist.initialize();
        odysseyWheels.initialize();
    }

    @Override
    public void colorPicker(ColorEventObject event) {
        if (this.parent.opModeIsActive()) {
            double distance = event.getDistance();
            telemetry.addData("distance", distance);
            telemetry.addData("color", event.getColor());
            telemetry.update();
            parent.sleep(1000);
            // odysseyWheels.encoderDrive(0.6, distance, distance, distance, distance, 1);
            parent.sleep(2000);
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
    public void openClaw(ClawEventObject event) {
        if (this.parent.opModeIsActive()) {
            Thread tc = new Thread() {
                public void run() {
                    // Add code to open the claw if needed
                }
            };
            tc.start();
        }
    }

    @Override
    public void closeClaw(ClawEventObject event) {
        if (this.parent.opModeIsActive()) {
            Thread tc = new Thread() {
                public void run() {
                    // odysseyWheels.move(0.6, 0, 0, 0);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    // odysseyWheels.move(0, 0, 0, 0);
                }
            };
            tc.start();
        }
    }

    @Override
    public void onArmMove(ArmEventObject event) {
        ArmStates newState = event.getNewState();
        switch (newState) {
            case LOWERED:
                System.out.println("Arm is in lowered position, ready to grab sample.");
                break;
            case LOWERED_BACK:
                System.out.println("Arm is in lowered back position, ready to deposit.");
                break;
            case NEUTRAL:
                System.out.println("Arm is back to neutral after depositing.");
                break;
            default:
                System.out.println("Arm moved to an unknown state.");
                break;
        }
    }

    @Override
    public void onWristMove(WristEventObject event) { // Implementing onWristMove method
        WristStates newState = event.getNewState();
        switch (newState) {
            case INITIALIZING:
                System.out.println("Wrist is initializing.");
                break;
            case IDLE:
                System.out.println("Wrist is idle.");
                break;
            case DEPOSITING:
                System.out.println("Wrist needs to deposit a sample.");
                break;
            case PICKING_UP:
                System.out.println("Wrist needs to pick up a sample.");
                break;
            case ROTATED_FORWARDS:
                System.out.println("Wrist rotated forwards.");
                break;
            case ROTATED_BACKWARDS:
                System.out.println("Wrist rotated backwards.");
                break;
            default:
                System.out.println("Wrist moved to an unknown state.");
                break;
        }
    }
}
