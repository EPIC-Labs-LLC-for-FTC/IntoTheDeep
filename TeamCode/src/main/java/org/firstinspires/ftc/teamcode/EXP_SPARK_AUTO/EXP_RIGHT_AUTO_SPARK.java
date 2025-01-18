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
@Autonomous(name = "EXP_RIGHT_AUTO_SPARK")
public class EXP_RIGHT_AUTO_SPARK extends LinearOpMode {


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
        wrist.wristAutoPick();
    }

    public void deliver(){
        Arm arm = new Arm(hardwareMap);
        Wrist wrist = new Wrist(hardwareMap);
        arm.armDrop();
        wrist.wristDrop();
    }

    public void specimenPick(){
        Arm arm = new Arm(hardwareMap);
        Wrist wrist = new Wrist(hardwareMap);
        arm.specimenPick();
        wrist.specimenPick();
    }

    public void slides0(){
        Slides slides = new Slides(hardwareMap);
        slides.moveTo();
        slides.slidesGo(250);
    }

    public void highBar(){
        Slides slides = new Slides(hardwareMap);
        slides.moveTo();
        slides.slidesGo(1650);
    }

    public void highBucket(){
        Slides slides = new Slides(hardwareMap);
        slides.moveTo();
        slides.slidesGo(2000);
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

            arm.armRest2();
            wrist.wristDrop();
            claw.initialize();
            slides.initialize();

        }

        Pose2d startPose = new Pose2d(-9.5122, 62.6138, -90);
        PinpointDrive drive = new PinpointDrive(hardwareMap,startPose);

        waitForStart();

        Actions.runBlocking(

                drive.actionBuilder(startPose)

                        //Drop pre load (specimen1)
                        .stopAndAdd(this::highBar)
                        .stopAndAdd(this::specimenDrop)
                        .stopAndAdd(this::specimenWristDrop)

                        .setTangent(-90)
                        .strafeToConstantHeading(new Vector2d(-21.799,41.1814))

                        .stopAndAdd(this::open)

                        //Pick Specimen 2
//                        .strafeToConstantHeading(new Vector2d(-37.2552,60.6072))
//
//                        .afterTime(0.5, this::slides0)
//                        .afterTime(0.5, this::specimenPick)
//                        .stopAndAdd(this::close)

                        //Place Specimen 2
//                        .stopAndAdd(this::highBar)
//                        .stopAndAdd(this::specimenDrop)
//                        .stopAndAdd(this::specimenWristDrop)
//
//                        .strafeToConstantHeading(new Vector2d(0,0))
//
//                        .stopAndAdd(this::open)

                        //Pick red1 and drop
//                        .strafeToConstantHeading(new Vector2d(0,0))
//                        .strafeToConstantHeading(new Vector2d(0,0))
//
//                        .stopAndAdd(this::pick)
//                        .stopAndAdd(this::slides0)
//                        .stopAndAdd(this::close)
//
//                        .strafeToConstantHeading(new Vector2d(0,0))
//
//                        .stopAndAdd(this::deliver)
//                        .stopAndAdd(this::open)

                        //Pick red2 and drop
//                        .strafeToConstantHeading(new Vector2d(0,0))
//                        .strafeToConstantHeading(new Vector2d(0,0))
//
//                        .stopAndAdd(this::pick)
//                        .stopAndAdd(this::close)
//
//                        .strafeToConstantHeading(new Vector2d(0,0))
//
//                        .stopAndAdd(this::deliver)
//                        .stopAndAdd(this::open)

                        //Pick red3 and drop
//                        .strafeToConstantHeading(new Vector2d(0,0))
//                        .strafeToConstantHeading(new Vector2d(0,0))
//
//                        .stopAndAdd(this::pick)
//                        .stopAndAdd(this::close)
//
//                        .strafeToConstantHeading(new Vector2d(0,0))
//
//                        .stopAndAdd(this::deliver)
//                        .stopAndAdd(this::open)

                        //Pick and drop specimen 3
//                        .strafeToConstantHeading(new Vector2d(0,0))
//                        .stopAndAdd(this::specimenPick)
//
//                        .strafeToConstantHeading(new Vector2d(0,0))
//                        .stopAndAdd(this::close)
//
//                        .stopAndAdd(this::highBar)
//                        .stopAndAdd(this::specimenDrop)
//                        .stopAndAdd(this::specimenWristDrop)
//
//                        .strafeToConstantHeading(new Vector2d(0,0))
//                        .stopAndAdd(this::open)

                        //Pick and drop specimen 4
//                        .strafeToConstantHeading(new Vector2d(0,0))
//                        .afterTime(0.5, this::slides0)
//                        .afterTime(0.5, this::specimenPick)
//                        .stopAndAdd(this::close)
//
//                        .stopAndAdd(this::highBar)
//                        .stopAndAdd(this::specimenDrop)
//                        .stopAndAdd(this::specimenWristDrop)
//
//                        .strafeToConstantHeading(new Vector2d(0,0))
//                        .stopAndAdd(this::open)
//
//                        //Pick and drop specimen 5
//                        .strafeToConstantHeading(new Vector2d(0,0))
//                        .afterTime(0.5, this::slides0)
//                        .afterTime(0.5, this::specimenPick)
//                        .stopAndAdd(this::close)
//
//                        .stopAndAdd(this::highBar)
//                        .stopAndAdd(this::specimenDrop)
//                        .stopAndAdd(this::specimenWristDrop)
//
//                        .strafeToConstantHeading(new Vector2d(0,0))
//                        .stopAndAdd(this::open)

                        //Pick and place sample
//                        .strafeToLinearHeading(new Vector2d(0,0), 180)
//
//                        .afterTime(0.5,this::slides0)
//                        .afterTime(0.5,this::pick)
//                        .stopAndAdd(this::close)
//                        .stopAndAdd(this::deliver)
//
//                        .strafeToLinearHeading(new Vector2d(0,0), 110)
//                        .strafeToConstantHeading(new Vector2d(0,0))
//
//                        .stopAndAdd(this::highBucket)
//                        .stopAndAdd(this::open)
//
//                        .stopAndAdd(this::pick)
//                        .stopAndAdd(this::slides0)

                        //Park
//                        .strafeToLinearHeading(new Vector2d(0,0), -180)
//
//                        .afterTime(0.5,this::slides0)
//                        .afterTime(0.5,this::pick)

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