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

    public void barh(){
        Slides slides = new Slides(hardwareMap);
        slides.hBar();
    }

    public void bucketh2(){
        Slides slides = new Slides(hardwareMap);
        slides.hbucket2();
    }

    public void barhReset(){
        Slides slides = new Slides(hardwareMap);
        slides.RESEThBar();
    }

    public void hbucket(){
        Slides slides = new Slides(hardwareMap);
        slides.hBucket();
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

    public void armstart(){
        Arm arm = new Arm(hardwareMap);
        arm.start();
    }


    public void customSlides(){
        Slides slides = new Slides(hardwareMap);
        slides.custom(500);
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

        Pose2d startPose = new Pose2d(-14.3, -63, Math.toRadians(90));
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
                        .splineToConstantHeading(new Vector2d(-9,-37), Math.PI/2)

                        .afterTime(0.1,this::barhReset)
                        .stopAndAdd(this::barhReset)

                        .waitSeconds(0.5)

                        .afterTime(0.1,this::open)
                        .stopAndAdd(this::open)

                        .strafeToConstantHeading(new Vector2d(-28,-53))

                        //PART 1.5

                        .strafeToLinearHeading(new Vector2d(-28.5,-29),Math.PI)

                        .afterTime(0.1,this::armdown)
                        .stopAndAdd(this::armdown)

                        .afterTime(0.1,this::slide0)
                        .stopAndAdd(this::slide0)

                        .afterTime(0.1,this::resetslides)
                        .stopAndAdd(this::resetslides)

                        .waitSeconds(1)

                        .afterTime(0.1,this::close)
                        .stopAndAdd(this::close)

                        .strafeToLinearHeading(new Vector2d(-36,-42.5),Math.PI*1.25)

                        .afterTime(0.1,this::anglearm)
                        .stopAndAdd(this::anglearm)

                        .afterTime(0.1,this::hbucket)
                        .stopAndAdd(this::hbucket)

                        .waitSeconds(3)

                        .afterTime(0.1,this::open)
                        .stopAndAdd(this::open)

                        .strafeToConstantHeading(new Vector2d(-30,-40))

                        .waitSeconds(0.5)

                        .afterTime(0.1,this::slide0)
                        .stopAndAdd(this::slide0)

                        .afterTime(0.1,this::resetslides)
                        .stopAndAdd(this::resetslides)

                        //PART2

                        .strafeToLinearHeading(new Vector2d(-35,-30.5),Math.PI)

                        .afterTime(0.1,this::armdown)
                        .stopAndAdd(this::armdown)

                        .waitSeconds(1)

                        .afterTime(0.1,this::close)
                        .stopAndAdd(this::close)

                        .strafeToLinearHeading(new Vector2d(-33,-44),Math.PI*1.25)

                        .afterTime(0.1,this::anglearm)
                        .stopAndAdd(this::anglearm)

                        .afterTime(0.1,this::bucketh2)
                        .stopAndAdd(this::bucketh2)

                        .waitSeconds(3)

                        .afterTime(0.1,this::open)
                        .stopAndAdd(this::open)

                        .strafeToConstantHeading(new Vector2d(-34,-40))

                        .waitSeconds(0.5)

                        .afterTime(0.1,this::slide0)
                        .stopAndAdd(this::slide0)

                        .afterTime(0.1,this::resetslides)
                        .stopAndAdd(this::resetslides)

//                        .waitSeconds(2)
//
//                        .afterTime(0.1,this::customSlides)
//                        .stopAndAdd(this::customSlides)

                        //PART3

//                        .afterTime(0.1,this::barhReset)
//                        .stopAndAdd(this::barhReset)
//
//                        .afterTime(0.1,this::armdown)
//                        .stopAndAdd(this::armdown)
//
//                        .strafeToLinearHeading(new Vector2d(-36,-28.5),Math.PI)
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
//
//                        .afterTime(0.1,this::anglearm)
//                        .stopAndAdd(this::anglearm)
//
//                        .strafeToLinearHeading(new Vector2d(-40,-45),Math.PI*1.15)
//
//                        .afterTime(0.1,this::hbucket)
//                        .stopAndAdd(this::hbucket)
//
//                        .waitSeconds(3)
//
//                        .afterTime(0.1,this::open)
//                        .stopAndAdd(this::open)
//
//                        .strafeToConstantHeading(new Vector2d(-34,-41))
//
//                        .waitSeconds(0.5)
//
//                        .afterTime(0.1,this::slide0)
//                        .stopAndAdd(this::slide0)
//
//                        .afterTime(0.1,this::resetslides)
//                        .stopAndAdd(this::resetslides)
//
//                        .afterTime(0.1,this::armstart)
//                        .stopAndAdd(this::armstart)

                        //PART4

                        .strafeToLinearHeading(new Vector2d(-36,-34),Math.PI)
                        .splineToLinearHeading(new Pose2d(-27,-18,0), Math.PI/2)

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