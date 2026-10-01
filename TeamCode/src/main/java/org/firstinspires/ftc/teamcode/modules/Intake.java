package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
public class Intake {
    private final DcMotor intake;

    private final DcMotor extensionLeft, extensionRight;
    LinearOpMode linearOpMode;
    public static double EXT_POSE_MAX = 1000;
    public static double EXT_POSE_MIN = 0;
    double rightError, leftError;
    public static double kP = 0;
    public static double kD = 0;
    public static double target;

    public double dErrorR, dErrorL;
    public double pastErrorR, pastErrorL;

    ElapsedTime timerL, timerR;

    public double currentLeftPoseOfExtension, currentRightPoseOfExtension;
    public static ExtensionController extensionController;

    public Intake(LinearOpMode aggregate) {
        linearOpMode = aggregate;
        intake = linearOpMode.hardwareMap.get(DcMotor.class, "intake");
        extensionLeft = linearOpMode.hardwareMap.get(DcMotor.class, "extL");
        extensionRight = linearOpMode.hardwareMap.get(DcMotor.class, "extR");

        extensionLeft.setDirection(DcMotor.Direction.REVERSE);

        extensionLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        extensionRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        extensionLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        extensionRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        timerL = new ElapsedTime();
        timerL.reset();

        timerR = new ElapsedTime();
        timerR.reset();
    }

    public void rotateIn(double power) {
        intake.setPower(power);
    }

    public void rotateOut(double power) {
        intake.setPower(-power);
    }

    public void rotateStop() {
        intake.setPower(0);
    }

    public void setExtensionLeftPower(double power) {
        extensionLeft.setPower(power);
    }

    public void setExtensionRightPower(double power) {
        extensionRight.setPower(power);
    }

    public class ExtensionController extends Thread {
        @Override
        public void run() {
            double powerR, powerL;
            while (!isInterrupted()) {
                currentLeftPoseOfExtension = getCurrentLeftPoseOfExtension();
                currentRightPoseOfExtension = getCurrentRightPoseOfExtension();

                rightError = target - currentRightPoseOfExtension;
                leftError = target - currentLeftPoseOfExtension;

                dErrorR = rightError - pastErrorR;
                dErrorL = leftError - pastErrorL;


                powerR = rightError * kP  + dErrorR * kD / timerR.milliseconds();
                powerL = leftError * kP + dErrorL * kD / timerL.milliseconds();

                turnInLimits(powerL, powerR);

                pastErrorR = rightError;
                pastErrorL = leftError;
                timerR.reset();
                timerL.reset();
            }
        }
    }

    public void setTarget(double target) {
        Intake.target = target;
    }

    public void turnInLimits(double powerL, double powerR) {
        boolean leftInLimits =
                currentLeftPoseOfExtension > EXT_POSE_MIN &&
                        currentLeftPoseOfExtension < EXT_POSE_MAX;

        boolean rightInLimits =
                currentRightPoseOfExtension > EXT_POSE_MIN &&
                        currentRightPoseOfExtension < EXT_POSE_MAX;
        if(leftInLimits && rightInLimits) {
           setExtensionPower(powerL, powerR);
        } else {
            setExtensionPower(0);
        }
    }

    public void setExtensionPower(double powerL, double powerR) {
        extensionRight.setPower(powerR);
        extensionLeft.setPower(powerL);
    }
    public void setExtensionPower(double power) {
        extensionRight.setPower(power);
        extensionLeft.setPower(power);
    }

    public double getCurrentLeftPoseOfExtension() {
        return extensionLeft.getCurrentPosition();
    }

    public double getCurrentRightPoseOfExtension() {
        return extensionRight.getCurrentPosition();
    }

}
