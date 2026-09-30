package org.firstinspires.ftc.teamcode.modules;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Intake {
    private final DcMotor intake;

    private final DcMotor extensionLeft, extensionRight;
    LinearOpMode linearOpMode;
    public static double EXT_POSE_MAX = 1;
    public static double EXT_POSE_MIN = 0;

    public Intake(LinearOpMode aggregate) {
        linearOpMode = aggregate;
        intake = linearOpMode.hardwareMap.get(DcMotor.class, "intake");
        extensionLeft = linearOpMode.hardwareMap.get(DcMotor.class, "extL");
        extensionRight = linearOpMode.hardwareMap.get(DcMotor.class, "extR");

        extensionLeft.setDirection(DcMotor.Direction.REVERSE);
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

//    public void setExtensionPose(double pose) {
//        if (pose < EXT_POSE_MAX && pose > EXT_POSE_MIN) {
//            extensionLeft.setPosition(pose);
//            extensionRight.setPosition(pose);
//        } else if (pose > EXT_POSE_MAX) {
//            extensionLeft.setPosition(EXT_POSE_MAX);
//            extensionRight.setPosition(EXT_POSE_MAX);
//        } else if (pose < EXT_POSE_MIN) {
//            extensionLeft.setPosition(EXT_POSE_MIN);
//            extensionRight.setPosition(EXT_POSE_MIN);
//        }
//    }

  //  public double getLeftExtensionPose() {
//        return extensionLeft.getPosition();
//    }

//    public double getRightExtensionPose() {
//        return extensionRight.getPosition();
//    }
}
