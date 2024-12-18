package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.EPIC.SparkFunOTOSDrive;
import com.arcrobotics.ftclib.controller.PIDController;

@Config
@Autonomous(name = "RR_Specimen_Auto", group = "Autonomous")
public class RR_Specimen_Auto extends LinearOpMode {

    // lift class
    public class Arm {
        private DcMotorEx armRight;
        private DcMotorEx armLeft;
        private PIDController controller;
        public double p1 = 0.01, i1 = 0, d1 = 0.00075;
        public double f1 = -0.2;
        private int target1 = 0;
        private final double tick_in_degrees1 = 2786.2/360;

        public Arm(HardwareMap hardwareMap) {
            armRight = hardwareMap.get(DcMotorEx.class, "armRight");
            armLeft = hardwareMap.get(DcMotorEx.class, "armLeft");
            armRight.setDirection(DcMotorSimple.Direction.REVERSE);
            controller = new PIDController(p1, i1, d1);
        }

        public class ArmPID implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                controller.setPID(p1, i1, d1);
                int armPos = armRight.getCurrentPosition();
                double pid1 = controller.calculate(armPos, target1);
                double ff1 = Math.cos(Math.toRadians(target1 / tick_in_degrees1)) * f1;

                double power1 = pid1 + ff1;

                armRight.setPower(power1);
                armLeft.setPower(power1);
                return true;
            }

        }
        public Action armPID() {
            return new Arm.ArmPID();
        }
//////////////////////////////////////
        public class ArmSpecimenForward implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                target1 = -780;
                return false;
            }

        }
        public Action armSpecimenForward() {
            return new Arm.ArmSpecimenForward();
        }
//////////////////////////////////////
        public class ArmSpecimenBackward implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                target1 = -690;
                return false;
            }

        }
        public Action armSpecimenBackward() {
            return new Arm.ArmSpecimenBackward();
        }
        ////////////////////////////////////

        public class ArmResetForward implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                target1 = -1120;
                return false;
            }

        }
        public Action armResetForward() {
            return new Arm.ArmResetForward();
        }
        //////////////////////////////////////////

        public class ArmResetBackward implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                target1 = -1700;
                return false;
            }

        }
        public Action armResetBackward() {
            return new Arm.ArmResetBackward();
        }
        //////////////////////////////////////////
        public class ArmZero implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                target1 = -10;
                return false;
            }

        }
        public Action armZero() {
            return new Arm.ArmZero();
        }

    }

    public class Slides {
        private DcMotorEx slideRight;
        private DcMotorEx slideLeft;
        private PIDController controller2;
        public double p2 = 0.017, i2 = 0, d2 = 0.0001;
        public double f2 = -0.02;
        public int target2 = 0;
        private final double tick_in_degrees2 = 537.7/360;

        public Slides(HardwareMap hardwareMap) {
            slideRight = hardwareMap.get(DcMotorEx.class, "slideRight");
            slideLeft = hardwareMap.get(DcMotorEx.class, "slideLeft");
            slideRight.setDirection(DcMotorSimple.Direction.REVERSE);
            controller2 = new PIDController(p2, i2, d2);
        }

        public class SlidePID implements Action {
            public boolean run(@NonNull TelemetryPacket packet) {
                controller2.setPID(p2, d2, i2);
                int slidePos = slideRight.getCurrentPosition();
                double pid2 = controller2.calculate(slidePos, target2);
                double ff2 = Math.cos(Math.toRadians(target2 / tick_in_degrees2)) * f2;

                double power2 = pid2 + ff2;

                slideRight.setPower(power2);
                slideLeft.setPower(power2);
                return true;
            }
        }
        public Action slidePID() {
            return new Slides.SlidePID();
        }

        public class SlideSpecimen implements Action {
            public boolean run(@NonNull TelemetryPacket packet) {
                target2 = -950;
                return false;
            }
        }
        public Action slideSpecimen() {
            return new Slides.SlideSpecimen();
        }

        public class SlideSpecimen2 implements Action {
            public boolean run(@NonNull TelemetryPacket packet) {
                target2 = -580;
                return false;
            }
        }
        public Action slideSpecimen2() {
            return new Slides.SlideSpecimen2();
        }
        public class SlideReset implements Action {
            public boolean run(@NonNull TelemetryPacket packet) {
                target2 = -30;
                return false;
            }
        }
        public Action slideReset() {
            return new Slides.SlideReset();
        }


    }

    // claw class
    public class Claw {
        private Servo clawRight;
        private Servo clawLeft;

        public Claw(HardwareMap hardwareMap) {
            clawLeft = hardwareMap.get(Servo.class, "clawLeft");
            clawRight = hardwareMap.get(Servo.class, "clawRight");
            clawLeft.setDirection(Servo.Direction.REVERSE);
        }

        public class CloseClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                clawRight.setPosition(0.45);
                clawLeft.setPosition(0.65);
                return false;
            }

        }
        public Action closeClaw() {
            return new CloseClaw();
        }

        public class OpenClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                clawRight.setPosition(0.73);
                clawLeft.setPosition(0.9);
                return false;
            }
        }
        public Action openClaw() {
            return new OpenClaw();
        }

    }

    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(4.1, -69.2, Math.toRadians(90));
        SparkFunOTOSDrive drive = new SparkFunOTOSDrive(hardwareMap, initialPose);
        Claw claw = new Claw(hardwareMap);
        Arm arm = new Arm(hardwareMap);
        Slides slide = new Slides(hardwareMap);


        // actionBuilder builds from the drive steps passed to it
        TrajectoryActionBuilder specimen1 = drive.actionBuilder(initialPose)
                // Specimen 1
                .waitSeconds(1)
                .afterTime(0.1, arm.armSpecimenForward())
                .stopAndAdd(arm.armSpecimenForward())
                .afterTime(0.5, slide.slideSpecimen())
                .stopAndAdd(slide.slideSpecimen())
                .waitSeconds(1)

                .strafeToLinearHeading(new Vector2d(4.1, -42.5), Math.toRadians(90))

                .afterTime(0.1, claw.openClaw())
                .stopAndAdd(claw.openClaw())
                .waitSeconds(1)

                // Specimen 2
                .strafeToLinearHeading(new Vector2d(4.1, -53), Math.toRadians(90))

                .afterTime(0.1, slide.slideReset())
                .stopAndAdd(slide.slideReset())
                .afterTime(0.5, arm.armResetBackward())
                .stopAndAdd(arm.armResetBackward())
                .waitSeconds(1)

                .strafeToLinearHeading(new Vector2d(47, -70.5), Math.toRadians(270))

                .waitSeconds(2)
                .afterTime(0.1, claw.closeClaw())
                .stopAndAdd(claw.closeClaw())
                .afterTime(1, arm.armSpecimenBackward())
                .stopAndAdd(arm.armSpecimenBackward())
                .waitSeconds(1)

                .strafeToLinearHeading(new Vector2d(-5, -50), Math.toRadians(270))

                .afterTime(0.1, slide.slideSpecimen2())
                .stopAndAdd(slide.slideSpecimen2())
                .waitSeconds(1)

                .strafeToLinearHeading(new Vector2d(-5, -39), Math.toRadians(270))

                .afterTime(1, claw.openClaw())
                .stopAndAdd(claw.openClaw())
                .waitSeconds(2);


        Action trajectoryActionCloseOut = specimen1.endTrajectory().fresh()
                .build();




        Actions.runBlocking(claw.closeClaw());

        waitForStart();
        if (isStopRequested()) return;

        Actions.runBlocking(
                new ParallelAction(
                        arm.armPID(),
                        slide.slidePID(),
                    new SequentialAction(
                            specimen1.build(),
                            trajectoryActionCloseOut
                )
        )
        );
    }


}

