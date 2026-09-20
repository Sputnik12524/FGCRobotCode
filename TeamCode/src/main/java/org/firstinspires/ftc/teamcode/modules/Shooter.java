package org.firstinspires.ftc.teamcode.modules;


import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@Config
public class Shooter {

    public final DcMotorEx shooterUpper, shooterLower;
    private final VoltageSensor batteryVoltageSensor;

    LinearOpMode opMode;
    public static double p = 20;
    public static double i = 0;
    public static double d = 20;
    public static double f = 15;

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
        shooterUpper = opMode.hardwareMap.get(DcMotorEx.class, "shooterUpper");
        shooterLower = opMode.hardwareMap.get(DcMotorEx.class, "shooterLower");
        batteryVoltageSensor = opMode.hardwareMap.voltageSensor.iterator().next();

        shooterUpper.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        shooterLower.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        shooterUpper.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
        shooterLower.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        shooterUpper.setDirection(DcMotorEx.Direction.REVERSE);

        setPIDFCoefficients(shooterUpper, MOTOR_VELO_PID_SHOOTERS);
        setPIDFCoefficients(shooterLower, MOTOR_VELO_PID_SHOOTERS);
    }


    private void setPIDFCoefficients(DcMotorEx motor, PIDFCoefficients coefficients) {
        motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(
                coefficients.p, coefficients.i, coefficients.d, coefficients.f * 12 / batteryVoltageSensor.getVoltage()
        ));
    }

    public void setVelocityTarget(double targetInRPS) {
        velocityTarget = targetInRPS * TPR;
    }


    public void shootUpperVelo(){
        shooterUpper.setVelocity(velocityTarget);
    }
    public void shootLowerVelo() {
        shooterLower.setVelocity(velocityTarget);
    }
    public void shootByVelocity() {
        shooterUpper.setVelocity(velocityTarget);
        shooterLower.setVelocity(velocityTarget);
    }

    public void shootStop() {
        shooterUpper.setVelocity(0);
        shooterLower.setVelocity(0);
    }

    //---------------------------------------------- GETTING


    public double getUpperAmps() {
        return shooterUpper.getCurrent(CurrentUnit.AMPS);
    }
    public double getLowerAmps() {
        return shooterLower.getCurrent(CurrentUnit.AMPS);
    }

    public double getVelocityRPS() {
        return (shooterUpper.getVelocity()) / (TPR * 2);
    }

    public double getVelocityUpper() {
        return shooterUpper.getVelocity() / TPR;
    }
    public double getVelocityLower() {
        return shooterLower.getVelocity()/TPR;
    }

    public double getUpperVelocityTPS() {
        return shooterUpper.getVelocity();
    }
    public double getLowerVelocityTPS(){return shooterLower.getVelocity();}


}