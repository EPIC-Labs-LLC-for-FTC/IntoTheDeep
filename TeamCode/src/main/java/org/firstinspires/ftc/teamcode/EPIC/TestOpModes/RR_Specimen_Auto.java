package org.firstinspires.ftc.teamcode.EPIC.TestOpModes;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TurnConstraints;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
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
        public double p1 = 0.018, i1 = 0, d1 = 0.00075;
        public double f1 = -0.2;
        private int target1 = 0;
        private final double tick_in_degrees1 = 2786.2/360;

        public Arm(HardwareMap hardwareMap) {
            armRight = hardwareMap.get(DcMotorEx.class, "armRight");
            armLeft = hardwareMap.get(DcMotorEx.class, "armLeft");
            armRight.setDirection(DcMotorSimple.Direction.REVERSE);
            controller = new PIDController(p1, i1, d1);
            armRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            armRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
                target1 = -840;
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
                target1 = -680;
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
                target1 = -375;
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
                target1 = -1630;
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

        public class ArmSamplePick implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                target1 = -220;
                return false;
            }

        }
        public Action armSamplePick() {
            return new Arm.ArmSamplePick();
        }

        public class ArmSampleDrop implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                target1 = -990;
                return false;
            }

        }
        public Action armSampleDrop() {
            return new Arm.ArmSampleDrop();
        }

        public class ArmTelemetryInit implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                telemetry.addData("Arm Pos", armRight.getCurrentPosition());
                telemetry.addData("Arm Target", target1);
                return false;
            }

        }
        public Action armTelemetryInit() {
            return new Arm.ArmTelemetryInit();
        }

        public class ArmTelemetryRunning implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                telemetry.addData("Arm Pos", armRight.getCurrentPosition());
                telemetry.addData("Arm Target", target1);
                return true;
            }

        }
        public Action armTelemetryRunning() {
            return new Arm.ArmTelemetryRunning();
        }

        public class ArmSampleMove implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                target1 = -330;
                return false;
            }

        }
        public Action armSampleMove() {
            return new Arm.ArmSampleMove();
        }



    }
    ////////////////////////////////////////////////



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
            slideRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            slideRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
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
                target2 = -1830;
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

        public class SlideSamplePick implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                target2 = -1240;
                return false;
            }

        }
        public Action slideSamplePick() {
            return new Slides.SlideSamplePick();
        }

        public class SlideSampleDrop implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                target2 = -3000;
                return false;
            }

        }
        public Action slideSampleDrop() {
            return new Slides.SlideSampleDrop();
        }

        public class SlideTelemetryInit implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                telemetry.addData("Slide Pos", slideRight.getCurrentPosition());
                telemetry.addData("Slide Target", target2);
                telemetry.update();
                return false;
            }

        }
        public Action slideTelemetryInit() {
            return new Slides.SlideTelemetryInit();
        }

        public class SlideTelemetryRunning implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                telemetry.addData("Arm Pos", slideRight.getCurrentPosition());
                telemetry.addData("Arm Target", target2);
                telemetry.update();
                return true;
            }

        }
        public Action slideTelemetryRunning() {
            return new Slides.SlideTelemetryRunning();
        }


        public class SlideSampleMove implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                target2 = -2230;
                return false;
            }

        }
        public Action slideSampleMove() {
            return new Slides.SlideSampleMove();
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
                clawRight.setPosition(0.57);
                clawLeft.setPosition(0.57);
                return false;
            }

        }
        public Action closeClaw() {
            return new CloseClaw();
        }

        public class OpenClaw implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                clawRight.setPosition(0.7);
                clawLeft.setPosition(0.7);
                return false;
            }
        }
        public Action openClaw() {
            return new OpenClaw();
        }

    }

    //Wrist
    public class Wrist {
        private Servo wrist;

        public Wrist(HardwareMap hardwareMap) {
            wrist = hardwareMap.get(Servo.class, "wrist");
        }

        public class WristReset implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                wrist.setPosition(0.4483);
                return false;
            }

        }

        public Action wristReset() {
            return new Wrist.WristReset();
        }

        public class WristSpecimen implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                wrist.setPosition(1);
                return false;
            }

        }

        public Action wristSpecimen() {
            return new Wrist.WristSpecimen();
        }

        public class WristBucket implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                wrist.setPosition(0.9);
                return false;
            }

        }

        public Action wristBucket() {
            return new Wrist.WristBucket();
        }

        public class WristUp implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                wrist.setPosition(0);
                return false;
            }

        }

        public Action wristUp() {
            return new Wrist.WristUp();
        }
    }



    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(4.1, -69.2, Math.toRadians(90));
        SparkFunOTOSDrive drive = new SparkFunOTOSDrive(hardwareMap, initialPose);
        Claw claw = new Claw(hardwareMap);
        Arm arm = new Arm(hardwareMap);
        Slides slide = new Slides(hardwareMap);
        Wrist wrist = new Wrist(hardwareMap);


        // actionBuilder builds from the drive steps passed to it
        Action specimen1 = drive.actionBuilder(initialPose)
                // Specimen
                .afterTime(0.1, arm.armSpecimenForward())
                .stopAndAdd(arm.armSpecimenForward())
                .afterTime(0.1, wrist.wristSpecimen())
                .stopAndAdd(wrist.wristSpecimen())
                .afterTime(0.5, slide.slideSpecimen())
                .stopAndAdd(slide.slideSpecimen())
                .waitSeconds(1)

                .strafeToLinearHeading(new Vector2d(4.1, -45), Math.toRadians(90))

                .afterTime(0.5, claw.openClaw())
                .stopAndAdd(claw.openClaw())
                .afterTime(1, wrist.wristUp())
                .stopAndAdd(wrist.wristUp())
                .waitSeconds(1)

                .strafeToLinearHeading(new Vector2d(4.1, -55.5), Math.toRadians(90))
                .afterTime(0.1, claw.closeClaw())
                .stopAndAdd(claw.closeClaw())
                .afterTime(0.1, slide.slideReset())
                .stopAndAdd(slide.slideReset())
                .afterTime(1, wrist.wristReset())
                .stopAndAdd(wrist.wristReset())
                .waitSeconds(1)

                .strafeToLinearHeading(new Vector2d(30, -45), Math.toRadians(45))
                .afterTime(0.01, claw.openClaw())
                .stopAndAdd(claw.openClaw())
                .afterTime(0.01, arm.armSamplePick())
                .stopAndAdd(arm.armSamplePick())
                .waitSeconds(0.5)
                .afterTime(0.1, slide.slideSamplePick())
                .stopAndAdd(slide.slideSamplePick())
                .waitSeconds(1)
                .afterTime(0.5, claw.closeClaw())
                .stopAndAdd(claw.closeClaw())
                .waitSeconds(1)

                .strafeToLinearHeading(new Vector2d(30, -54), Math.toRadians(-45))

                .afterTime(0.5, claw.openClaw())
                .stopAndAdd(claw.openClaw())
                .afterTime(2, slide.slideReset())
                .stopAndAdd(slide.slideReset())
                .waitSeconds(2)

                .strafeToLinearHeading(new Vector2d(35, -40), Math.toRadians(90))
                .afterTime(0.1, arm.armResetBackward())
                .stopAndAdd(arm.armResetBackward())
                .waitSeconds(1)

                .strafeToLinearHeading(new Vector2d(35, -63), Math.toRadians(90))
                .afterTime(0.01, claw.closeClaw())
                .stopAndAdd(claw.closeClaw())
                .waitSeconds(1)
                .afterTime(0.1, arm.armSpecimenForward())
                .stopAndAdd(arm.armSpecimenForward())
                .afterTime(3, slide.slideSpecimen())
                .stopAndAdd(slide.slideSpecimen())
                .afterTime(3, wrist.wristSpecimen())
                .stopAndAdd(wrist.wristSpecimen())
                .waitSeconds(2)


                .setTangent(180)
                .splineToConstantHeading(new Vector2d(9.1, -47), Math.toRadians(90))
                .afterTime(0.01, claw.openClaw())
                .stopAndAdd(claw.openClaw())

                .waitSeconds(10)

                .strafeToConstantHeading(new Vector2d(40, -60))
                .afterTime(0.01, claw.openClaw())
                .stopAndAdd(claw.openClaw())
                .waitSeconds(1.5)
                .afterTime(0.01, claw.closeClaw())
                .stopAndAdd(claw.closeClaw())
                .build();


        Actions.runBlocking(claw.closeClaw());
        Actions.runBlocking(arm.armTelemetryInit());
        Actions.runBlocking(slide.slideTelemetryInit());

        waitForStart();
        if (isStopRequested()) return;

        Actions.runBlocking(
                new ParallelAction(
                        arm.armPID(),
                        slide.slidePID(),
                        slide.slideTelemetryRunning(),
                        arm.armTelemetryRunning(),
                        new SequentialAction(
                                specimen1
                        )
                )
        );
    }


}