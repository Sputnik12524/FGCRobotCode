package org.firstinspires.ftc.teamcode.modules;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@Config
public class Shooter {

    public final DcMotorEx shooterRight, shooterLeft;
    private final VoltageSensor batteryVoltageSensor;

    LinearOpMode opMode;
    public static double p = 0.001;
    public static double i = 0;
    public static double d = 1;
    public static double f = 14;

    public static PIDFCoefficients MOTOR_VELO_PID_SHOOTERS = new PIDFCoefficients(p, i, d, f);


    //---------------------------------------------- DASHBOARD

    /// Shooter
    public static double VELOCITY_FOR_LONG_THROW = 60;
    public static double VELOCITY_FOR_SHORT_THROW = 48;
    public static double VELOCITY_FOR_MEDIUM_THROW = 60;


    //---------------------------------------------- CONSTANTS
    private final double TPR = 28;

    //----------------------------------------------

    public double velocityTarget = 0;


    //---------------------------------------------- BOOLEANS

    public Shooter(LinearOpMode opMode) {
        this.opMode = opMode;
        shooterRight = opMode.hardwareMap.get(DcMotorEx.class, "shooterRight");
        shooterLeft = opMode.hardwareMap.get(DcMotorEx.class, "shooterLeft");
        batteryVoltageSensor = opMode.hardwareMap.voltageSensor.iterator().next();

        shooterRight.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        shooterLeft.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        shooterRight.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooterLeft.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        shooterRight.setDirection(DcMotorEx.Direction.REVERSE);

        setPIDFCoefficients(shooterRight, MOTOR_VELO_PID_SHOOTERS);
        setPIDFCoefficients(shooterLeft, MOTOR_VELO_PID_SHOOTERS);
    }


    private void setPIDFCoefficients(DcMotorEx motor, PIDFCoefficients coefficients) {
        motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(
                coefficients.p, coefficients.i, coefficients.d, coefficients.f * 12 / batteryVoltageSensor.getVoltage()
        ));
    }

    public void setVelocityTarget(double targetInRPS) {
        velocityTarget = targetInRPS * TPR;
    }


    public void shootRightVelo(){
        shooterRight.setVelocity(velocityTarget);
    }
    public void shootLeftVelo() {
        shooterLeft.setVelocity(velocityTarget);
    }
    public void shootByVelocity() {
        shooterRight.setVelocity(velocityTarget);
        shooterLeft.setVelocity(velocityTarget);
    }

    public void shootStop() {
        shooterRight.setVelocity(0);
        shooterLeft.setVelocity(0);
    }

    //---------------------------------------------- GETTING


    public double getRightAmps() {
        return shooterRight.getCurrent(CurrentUnit.AMPS);
    }
    public double getLeftAmps() {
        return shooterLeft.getCurrent(CurrentUnit.AMPS);
    }

    public double getVelocityRPS() {
        return (shooterRight.getVelocity()) / (TPR * 2);
    }

    public double getVelocityRight() {
        return shooterRight.getVelocity() / TPR;
    }
    public double getVelocityLeft() {
        return shooterLeft.getVelocity()/TPR;
    }
    public void setMotorsPower(double power) {
        shooterRight.setPower(-power);
        shooterLeft.setPower(-power);
    }

    public double getRightVelocityTPS() {
        return shooterRight.getVelocity();
    }
    public double getLeftVelocityTPS(){return shooterLeft.getVelocity();}


}