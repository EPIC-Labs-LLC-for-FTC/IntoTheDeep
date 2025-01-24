package org.firstinspires.ftc.teamcode.EXP_SPARK_AUTO;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RRsetup.SparkFunOTOSDrive;
import org.firstinspires.ftc.teamcode.components.Arm;
import org.firstinspires.ftc.teamcode.components.Claw;
import org.firstinspires.ftc.teamcode.components.Slides;

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

    public void barh(){
        Slides slides = new Slides(hardwareMap);
        slides.hBar();
    }

    public void barhReset(){
        Slides slides = new Slides(hardwareMap);
        slides.RESEThBar();
    }

    public void resetslides(){
        Slides slides = new Slides(hardwareMap);
        slides.resetslides();
    }

    public void anglearm(){
        Arm arm = new Arm(hardwareMap);
        arm.angle();
    }

    public void armdown(){
        Arm arm = new Arm(hardwareMap);
        arm.Horizontal();
    }

    public void slide0(){
        Slides slides = new Slides(hardwareMap);
        slides.start();
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

        Claw claw = new Claw(hardwareMap);
        claw.setParent(this);
        claw.setTelemetry(this.telemetry);
        claw.initialize();

        while (opModeInInit()){

            arm.initialize();
            claw.initialize();
            slides.initialize();

        }

        Pose2d startPose = new Pose2d(14.3, -63, Math.toRadians(90));
        SparkFunOTOSDrive sdrive = new SparkFunOTOSDrive(hardwareMap, startPose);

        waitForStart();

        Actions.runBlocking(

                sdrive.actionBuilder(startPose)

                        //PART1

                        .afterTime(0.1,this::barh)
                        .stopAndAdd(this::barh)

                        .afterTime(0.1,this::anglearm)
                        .stopAndAdd(this::anglearm)

                        .setTangent(90)
                        .splineToConstantHeading(new Vector2d(9,-38), Math.PI/2)

                        .waitSeconds(1)

                        .afterTime(0.1,this::barhReset)
                        .stopAndAdd(this::barhReset)

                        .waitSeconds(0.5)

                        .afterTime(0.1,this::open)
                        .stopAndAdd(this::open)

                        .strafeToConstantHeading(new Vector2d(9,-42))

                        //PART2

//                        .strafeToLinearHeading(new Vector2d(35,-45),-Math.PI/4)
//
//                        .afterTime(0.1,this::armdown)
//                        .stopAndAdd(this::armdown)
//
//                        .afterTime(0.1,this::slide0)
//                        .stopAndAdd(this::slide0)
//
//                        .afterTime(0.1,this::resetslides)
//                        .stopAndAdd(this::resetslides)
//
//                        .waitSeconds(1)
//
//                        .afterTime(0.1,this::close)
//                        .stopAndAdd(this::close)
//
//                        .afterTime(0.1,this::anglearm)
//                        .stopAndAdd(this::anglearm)
//
//                        .afterTime(0.1,this::barh)
//                        .stopAndAdd(this::barh)
//
//                        .strafeToConstantHeading(new Vector2d(9,-42))
//
//                        .strafeToLinearHeading(new Vector2d(9,-20),Math.PI/2)
//
//                        .afterTime(0.1,this::barhReset)
//                        .stopAndAdd(this::barhReset)
//
//                        .waitSeconds(0.5)
//
//                        .afterTime(0.1,this::open)
//                        .stopAndAdd(this::open)
//
//                        .strafeToConstantHeading(new Vector2d(9,-42))

                        //PART3
//
//                        .strafeToConstantHeading(new Vector2d(36,-40))
//                        .strafeToConstantHeading(new Vector2d(36,-10))
//
//                        .strafeToConstantHeading(new Vector2d(48,-10))
//                        .strafeToConstantHeading(new Vector2d(48,-65))
//
//                        .strafeToConstantHeading(new Vector2d(48,-10))
//                        .strafeToConstantHeading(new Vector2d(57,-10))
//                        .strafeToConstantHeading(new Vector2d(57,-65))
//
//                        .strafeToConstantHeading(new Vector2d(57,-10))
//                        .strafeToConstantHeading(new Vector2d(68,-10))
//                        .strafeToConstantHeading(new Vector2d(68,-65))
//
//                        //PART2
//
//                        .afterTime(0.1,this::barhReset)
//                        .stopAndAdd(this::barhReset)
//
//                        .afterTime(0.1,this::armdown)
//                        .stopAndAdd(this::armdown)
//
//                        .strafeToConstantHeading(new Vector2d(60,-42))
//                        .strafeToLinearHeading(new Vector2d(50,-40),-Math.PI/2)
//
//                        .afterTime(0.1,this::slide0)
//                        .stopAndAdd(this::slide0)
//
//                        .afterTime(0.1,this::resetslides)
//                        .stopAndAdd(this::resetslides)
//
//                        .waitSeconds(0.5)
//
//                        .afterTime(0.1,this::close)
//                        .stopAndAdd(this::close)
//
//                        .afterTime(0.1,this::anglearm)
//                        .stopAndAdd(this::anglearm)
//
//                        .afterTime(0.1,this::barh)
//                        .stopAndAdd(this::barh)
//
//                        .strafeToLinearHeading(new Vector2d(9,-38),Math.PI/2)
//
//                        .afterTime(0.1,this::barhReset)
//                        .stopAndAdd(this::barhReset)
//
//                        .waitSeconds(0.5)
//
//                        .afterTime(0.1,this::open)
//                        .stopAndAdd(this::open)
//
//                        //PART3
//
//                        .strafeToConstantHeading(new Vector2d(9,-40))
//                        .strafeToLinearHeading(new Vector2d(50,-40),-Math.PI/2)
//
//                        .afterTime(0.1,this::armdown)
//                        .stopAndAdd(this::armdown)
//
//                        .afterTime(0.1,this::slide0)
//                        .stopAndAdd(this::slide0)
//
//                        .afterTime(0.1,this::resetslides)
//                        .stopAndAdd(this::resetslides)
//
//                        .waitSeconds(0.5)
//
//                        .afterTime(0.1,this::close)
//                        .stopAndAdd(this::close)
//
//                        .afterTime(0.1,this::anglearm)
//                        .stopAndAdd(this::anglearm)
//
//                        .afterTime(0.1,this::barh)
//                        .stopAndAdd(this::barh)
//
//                        .strafeToLinearHeading(new Vector2d(9,-38),Math.PI/2)
//
//                        .afterTime(0.1,this::barhReset)
//                        .stopAndAdd(this::barhReset)
//
//                        .waitSeconds(0.5)
//
//                        .afterTime(0.1,this::open)
//                        .stopAndAdd(this::open)
//
//                        //PART4
//
//                        .strafeToConstantHeading(new Vector2d(9,-40))
//                        .strafeToLinearHeading(new Vector2d(50,-40),-Math.PI/2)
//
//                        .afterTime(0.1,this::armdown)
//                        .stopAndAdd(this::armdown)
//
//                        .afterTime(0.1,this::slide0)
//                        .stopAndAdd(this::slide0)
//
//                        .afterTime(0.1,this::resetslides)
//                        .stopAndAdd(this::resetslides)
//
//                        .waitSeconds(0.5)
//
//                        .afterTime(0.1,this::close)
//                        .stopAndAdd(this::close)
//
//                        .afterTime(0.1,this::anglearm)
//                        .stopAndAdd(this::anglearm)
//
//                        .afterTime(0.1,this::barh)
//                        .stopAndAdd(this::barh)
//
//                        .strafeToLinearHeading(new Vector2d(9,-38),Math.PI/2)
//
//                        .afterTime(0.1,this::barhReset)
//                        .stopAndAdd(this::barhReset)
//
//                        .waitSeconds(0.5)
//
//                        .afterTime(0.1,this::open)
//                        .stopAndAdd(this::open)

                        //PART5

                        .afterTime(0.1,this::slide0)
                        .stopAndAdd(this::slide0)

                        .afterTime(0.1,this::resetslides)
                        .stopAndAdd(this::resetslides)

                        .strafeToConstantHeading(new Vector2d(40,-65))

                        .build());

        telemetry.addData("Slide1Position", slides.slide1.getCurrentPosition());
        telemetry.addData("Slide2Position", slides.slide2.getCurrentPosition());
        telemetry.addData("Arm1Position", arm.arm1.getPosition());
        telemetry.addData("Arm2Position", arm.arm2.getPosition());
        telemetry.addData("Claw1", claw.claw1.getPosition());
        telemetry.addData("Claw2", claw.claw2.getPosition());
        telemetry.update();
    }
}