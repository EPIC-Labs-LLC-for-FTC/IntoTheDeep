package org.firstinspires.ftc.teamcode.EXP_SPARK_AUTO;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RRsetup.PinpointDrive;
import org.firstinspires.ftc.teamcode.components.Arm;
import org.firstinspires.ftc.teamcode.components.Claw;
import org.firstinspires.ftc.teamcode.components.Slides;
import org.firstinspires.ftc.teamcode.components.Wrist;

@Config
@Autonomous(name = "EXP_LEFT_AUTO_SPARK")
public class EXP_LEFT_AUTO_SPARK extends LinearOpMode {

    public void close(){
        Claw claw = new Claw(hardwareMap);
        claw.close();
    }

    public void open(){
        Claw claw = new Claw(hardwareMap);
        claw.open();
    }

    public void specimenDrop(){
        Arm arm = new Arm(hardwareMap);
        arm.specimenDrop();
    }

    public void specimenWristDrop(){
        Wrist wrist = new Wrist(hardwareMap);
        wrist.specimenAutoDrop();
    }

    public void pick(){
        Arm arm = new Arm(hardwareMap);
        Wrist wrist = new Wrist(hardwareMap);
        arm.armPick();
        wrist.wristPick();
    }

    public void deliver(){
        Arm arm = new Arm(hardwareMap);
        Wrist wrist = new Wrist(hardwareMap);
        arm.armDrop();
        wrist.wristDrop();
    }

    public void slides0(){
        Slides slides = new Slides(hardwareMap);
        slides.moveDown(0);
    }

    public void highBar(){
        Slides slides = new Slides(hardwareMap);
        slides.moveUp(0);
    }

    public void highBucket(){
        Slides slides = new Slides(hardwareMap);
        slides.moveUp(0);
    }

    @Override
    public void runOpMode() throws InterruptedException {

        Slides slides = new Slides(hardwareMap);
        slides.setParent(this);
        slides.setTelemetry(this.telemetry);
        slides.initialize();

        Arm arm = new Arm(hardwareMap);
        arm.setParent(this);
        arm.setTelemetry(this.telemetry);
        arm.initialize();

        Wrist wrist = new Wrist(hardwareMap);
        wrist.setParent(this);
        wrist.setTelemetry(this.telemetry);
        wrist.initialize();

        Claw claw = new Claw(hardwareMap);
        claw.setParent(this);
        claw.setTelemetry(this.telemetry);
        claw.initialize();

        while (opModeInInit()){

            arm.initialize();
            claw.initialize();
            slides.initialize();

        }

        Pose2d startPose = new Pose2d(0, 0, Math.toRadians(-90));
        PinpointDrive drive = new PinpointDrive(hardwareMap,startPose);

        waitForStart();

        Actions.runBlocking(

                drive.actionBuilder(startPose)

                        //Drop pre load (specimen1)
                        .stopAndAdd(this::highBar)
                        .stopAndAdd(this::specimenDrop)
                        .stopAndAdd(this::specimenWristDrop)

                        .setTangent(-90)
                        .strafeToConstantHeading(new Vector2d(0,0))

                        .stopAndAdd(this::open)

                        //Pick Sample 1 and Drop
                        .strafeToConstantHeading(new Vector2d(0,0))
                        .strafeToConstantHeading(new Vector2d(0,0))

                        .afterTime(0.5, this::slides0)
                        .afterTime(0.5, this::pick)
                        .stopAndAdd(this::close)

                        .strafeToLinearHeading(new Vector2d(0,0), 0)

                        .stopAndAdd(this::deliver)
                        .stopAndAdd(this::highBucket)
                        .stopAndAdd(this::open)

                        //Pick Sample 2 and Drop
                        .afterTime(0.5, this::slides0)
                        .strafeToLinearHeading(new Vector2d(0,0), 0)

                        .afterTime(0.5, this::pick)
                        .stopAndAdd(this::close)

                        .strafeToLinearHeading(new Vector2d(0,0), 0)

                        .stopAndAdd(this::deliver)
                        .stopAndAdd(this::highBucket)
                        .stopAndAdd(this::open)

                        //Pick Sample 3 and Drop
                        .afterTime(0.5, this::slides0)
                        .strafeToLinearHeading(new Vector2d(0,0), 0)

                        .afterTime(0.5, this::pick)
                        .stopAndAdd(this::close)

                        .strafeToLinearHeading(new Vector2d(0,0), 0)

                        .stopAndAdd(this::deliver)
                        .stopAndAdd(this::highBucket)
                        .stopAndAdd(this::open)

                        //Park
                        .strafeToLinearHeading(new Vector2d(0,0),0)
                        .afterTime(0.5, this::slides0)

                        .build());

        telemetry.addData("Slide1Position", slides.slide1.getCurrentPosition());
        telemetry.addData("Slide2Position", slides.slide2.getCurrentPosition());
        telemetry.addData("ArmBase1Position", arm.armBase1.getPosition());
        telemetry.addData("ArmBase2Position", arm.armBase2.getPosition());
        telemetry.addData("Wrist1Position", wrist.wrist1.getPosition());
        telemetry.addData("Wrist2Position", wrist.wrist2.getPosition());
        telemetry.addData("Claw1", claw.claw1.getPosition());
        telemetry.addData("Claw2", claw.claw2.getPosition());
        telemetry.update();
    }
}