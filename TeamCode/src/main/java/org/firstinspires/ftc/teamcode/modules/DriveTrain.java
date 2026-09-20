package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@Config
public class DriveTrain {

    private final DcMotor leftFront;
    private final DcMotor leftBack;
    private final DcMotor rightFront;
    private final DcMotor rightBack;
    private final IMU imu;
    private final LinearOpMode linearOpMode;

    /** Overall drivetrain power limit. Change from Dashboard if needed. */
    public static double multiplier = 1.0;

    /** Optional turn sensitivity multiplier. */
    public static double turnMultiplier = 1.0;

    public static double p = 3;

    public DriveTrain(LinearOpMode aggregate) {
        linearOpMode = aggregate;

        leftFront = linearOpMode.hardwareMap.get(DcMotor.class, "leftFront");
        leftBack = linearOpMode.hardwareMap.get(DcMotor.class, "leftBack");
        rightFront = linearOpMode.hardwareMap.get(DcMotor.class, "rightFront");
        rightBack = linearOpMode.hardwareMap.get(DcMotor.class, "rightBack");
        imu = linearOpMode.hardwareMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                        RevHubOrientationOnRobot.UsbFacingDirection.UP
                )
        );

        imu.initialize(parameters);
        imu.resetYaw();

        /*
         * Kept from your original drivetrain.
         * If one or more wheels spin the wrong way during the wheel test,
         * change ONLY these direction settings, not the X-drive equations.
         */
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        leftFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightFront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBack.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightFront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightBack.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setSimplePower(double main, double rotate) {
        setMotorPowers(
                main + rotate,
                main + rotate,
                main - rotate,
                main - rotate
        );
    }

    /**
     * Robot-centric X-drive for four omni wheels.
     *
     * Wheel layout (viewed from above):
     *
     *          FRONT
     *       LF       RF
     *         \\     /
     *          \\   /
     *          /   \\
     *         /     \\
     *       LB       RB
     *
     * Adjacent wheel rolling directions are 90 degrees apart.
     *
     * @param forward +1 = robot forward
     * @param strafe  +1 = robot right
     * @param turn    +1 = rotate right / clockwise (normally)
     */
    public void driveRobotCentric(double forward, double strafe, double turn) {
        turn *= turnMultiplier;

        double lf = forward + strafe + turn;
        double lb = forward - strafe + turn;
        double rf = forward - strafe - turn;
        double rb = forward + strafe - turn;

        double denominator = Math.max(
                1.0,
                Math.max(
                        Math.max(Math.abs(lf), Math.abs(lb)),
                        Math.max(Math.abs(rf), Math.abs(rb))
                )
        );

        setMotorPowers(
                lf / denominator,
                lb / denominator,
                rf / denominator,
                rb / denominator
        );
    }

    /**
     * Field-centric X-drive.
     * Left stick commands movement relative to the FIELD rather than the robot.
     *
     * @param fieldForward +1 = away from the driver / field forward
     * @param fieldRight   +1 = field right
     * @param turn         rotation command
     */
    public void driveFieldCentric(double fieldForward, double fieldRight, double turn) {
        double heading = getYaw(AngleUnit.RADIANS);

        // Rotate the field vector by -heading to convert it to robot coordinates.
        double cos = Math.cos(heading);
        double sin = Math.sin(heading);

        double robotRight = fieldRight * cos + fieldForward * sin;
        double robotForward = -fieldRight * sin + fieldForward * cos;

        driveRobotCentric(robotForward, robotRight, turn);
    }

    /** Backwards-compatible method from your old class. */
    public void setPowerOmniSimple(double main, double side, double rotation) {
        driveRobotCentric(main, side, rotation);
    }

    public void setMotorsPowerNonLinear(double main, double side, double rotation) {
        double mainPower = main + (1 - Math.abs(main)) * main * Math.abs(Math.pow(main, p - 1));
        double sidePower = side + (1 - Math.abs(side)) * side * Math.abs(Math.pow(side, p - 1));
        double rotatePower = rotation + (1 - Math.abs(rotation)) * rotation * Math.abs(Math.pow(rotation, p - 1));

        driveRobotCentric(mainPower, sidePower, rotatePower);
    }

    /**
     * Backwards-compatible low-level mixer.
     * y/x must already be in ROBOT coordinates.
     */
    public void setPowerFieldCentric(double y, double x, double rx, double denominator) {
        double safeDenominator = Math.max(1.0, Math.abs(denominator));

        setMotorPowers(
                (y + x + rx) / safeDenominator,
                (y - x + rx) / safeDenominator,
                (y - x - rx) / safeDenominator,
                (y + x - rx) / safeDenominator
        );
    }

    private void setMotorPowers(double lf, double lb, double rf, double rb) {
        double scale = Range.clip(multiplier, 0.0, 1.0);

        leftFront.setPower(Range.clip(lf * scale, -1.0, 1.0));
        leftBack.setPower(Range.clip(lb * scale, -1.0, 1.0));
        rightFront.setPower(Range.clip(rf * scale, -1.0, 1.0));
        rightBack.setPower(Range.clip(rb * scale, -1.0, 1.0));
    }

    public void stop() {
        setMotorPowers(0, 0, 0, 0);
    }

    public void resetYaw() {
        imu.resetYaw();
    }

    public double getYaw(AngleUnit unit) {
        return imu.getRobotYawPitchRollAngles().getYaw(unit);
    }
}
