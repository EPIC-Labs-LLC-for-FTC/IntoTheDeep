package org.firstinspires.ftc.teamcode.EPIC.Robot;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.EPIC.Components.Claw;
import org.firstinspires.ftc.teamcode.EPIC.Components.Slider;
import org.firstinspires.ftc.teamcode.EPIC.Components.Arm;
import org.firstinspires.ftc.teamcode.EPIC.Components.Wrist;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.ArmEventObject;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.ClawEventObject;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.ColorEventObject;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.IArmListener;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.IClawListener;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.IColorListener;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.ITouchListener;
import org.firstinspires.ftc.teamcode.EPIC.EventListeners.TouchEventObject;
import org.firstinspires.ftc.teamcode.EPIC.Motion.Mecanum_Wheels;
import org.firstinspires.ftc.teamcode.EPIC.RobotStates.ArmStates;
import org.firstinspires.ftc.teamcode.EPIC.Sensors.MyColorRangeSensor;
import org.firstinspires.ftc.teamcode.EPIC.Sensors.MyTouchSensor;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot implements IColorListener, ITouchListener, IClawListener, IArmListener {
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

    public Robot(LinearOpMode parent, String alliance){
        odysseyClaw = new Claw(parent.hardwareMap);
        odysseySlider = new Slider(parent.hardwareMap);
        odysseyArm = new Arm(parent.hardwareMap);
        odysseyWrist = new Wrist(parent.hardwareMap);
        odysseyWheels = new Mecanum_Wheels(parent.hardwareMap);
        this.parent = parent;
        this.telemetry = parent.telemetry;
        this.alliance = alliance;
        odysseyArm.addArmListener(this);
//        odysseyClaw.addClawListener(this);
    }

    public void setIsAutonomous(boolean isAutonomous){
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
        odysseyClaw.initialize();
        odysseySlider.initialize();
        odysseyArm.initialize();
        odysseyWrist.initialize();
        odysseyWheels.initialize();
    }

    @Override
    public void colorPicker(ColorEventObject event) {
        if(this.parent.opModeIsActive()) {
            double distance = event.getDistance();
            telemetry.addData("distance", distance);

            telemetry.addData("color", event.getColor());
            telemetry.update();
            parent.sleep(1000);
            //odysseyWheels.encoderDrive(0.6,distance,distance,distance,distance,1);
            parent.sleep(2000);
        }
    }

    @Override
    public void touchClicked(TouchEventObject event) {
        if(this.parent.opModeIsActive()) {
            Thread tc = new Thread() {
                public void run() {

                    if (event.getButtonStatus()) {
                        //odysseyWheels.move(0.6, 0, 0, 0);
                    } else {
                        //odysseyWheels.move(0, 0, 0, 0);
                    }
                }
            };
            tc.start();

        }
    }

    @Override
    public void openClaw(ClawEventObject event) {
        if(this.parent.opModeIsActive()) {
            Thread tc = new Thread() {
                public void run() {


                }
            };
            tc.start();

        }

    }

    @Override
    public void closeClaw(ClawEventObject event) {
        if(this.parent.opModeIsActive()) {
            Thread tc = new Thread() {
                public void run() {

                    //odysseyWheels.move(0.6, 0, 0, 0);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
//                    odysseyWheels.move(0, 0, 0, 0);

                }
            };
            tc.start();

        }

    }

    @Override
    public void onArmMove(ArmEventObject event) {
        if(this.parent.opModeIsActive()) {
            Thread tc = new Thread() {
               public void run() {
                   ArmStates newState = event.getNewState();
                   switch (newState) {
                       case LOWERED:
                           
                           break;
                       case LOWERED_BACK:

                           break;
                       case NEUTRAL:

                           break;
                       default:

                           break;
                   }
               }
            };
        }

    }
}