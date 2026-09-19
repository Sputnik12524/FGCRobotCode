package org.firstinspires.ftc.teamcode.modules;


import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.VoltageSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@Config
public class Shooter {

    public final DcMotorEx shooter;
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
        shooter = opMode.hardwareMap.get(DcMotorEx.class, "shooter");
        batteryVoltageSensor = opMode.hardwareMap.voltageSensor.iterator().next();

        shooter.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        shooter.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);

        shooter.setDirection(DcMotorSimple.Direction.REVERSE);

        setPIDFCoefficients(shooter, MOTOR_VELO_PID_SHOOTERS);
    }


    private void setPIDFCoefficients(DcMotorEx motor, PIDFCoefficients coefficients) {
        motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(
                coefficients.p, coefficients.i, coefficients.d, coefficients.f * 12 / batteryVoltageSensor.getVoltage()
        ));
    }

    public void setVelocityTarget(double targetInRPS) {
        velocityTarget = targetInRPS * TPR;
    }

    public void shootByVelocity() {
        shooter.setVelocity(velocityTarget);
    }

    public void shootStop() {
        shooter.setVelocity(0);
    }

    public double getAmps() {
        return shooter.getCurrent(CurrentUnit.AMPS);
    }

    //---------------------------------------------- GETTING

    public double getVelocityRPS() {
        return (shooter.getVelocity()) / (TPR * 2);
    }

    public double getVelocityUpper() {
        return shooter.getVelocity() / TPR;
    }

    public double getVelocityTPS() {
        return shooter.getVelocity();
    }


}