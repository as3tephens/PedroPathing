package xperamentals.teleOp;

import android.provider.SyncStateContract;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ScheduleCommand;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.util.Constants;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

import xperamentals.controller.servoController;
import xperamentals.controller.slideControler;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import xperamentals.command.commands.*;
import xperamentals.command.comandGroups.SequentialCommandGroup.*;
import xperamentals.subsystem.*;

/**
 * This is an example teleop that showcases movement and robot-centric driving.
 *
 * @author Baron Henderson - 20077 The Indubitables
 * @version 2.0, 12/30/2024
 */

@TeleOp(name = "TeleOpLive", group = "TeleOp")
public class TeleOpLive extends OpMode {
    private Follower follower;

    private static int pathState = -1;
    private DcMotorEx slides;
    public static double p = 0.004, i = 0, d = 0.00001;
    public static int target = 0;
    private PIDController controller;
    private static Pose homePose = new Pose(8.8,9,Math.toRadians(0));

    private servoController claw;
    private double rotate = 0.0;
    private static int mode = 0;
    private arm arm;
    private claw claws;
    private GamepadEx toolOp;
    private intake intake;
    private slide slide;
    private Timer pathTimer, actionTimer, opmodeTimer;
  //  public static PathBuilder builder = new PathBuilder();
    //Added by Nathan Hall
    private static float leftTriggerPrevious = 0;
    public static final Pose startPose = new Pose(115,115.2,Math.toRadians(25));
 /**
    public static PathChain specPart1 = builder
    .addPath(
        //to chambers
        new BezierLine(
            new Point(132.600, 115.200, Point.CARTESIAN),
            new Point(105.5, 80.400, Point.CARTESIAN)
            )
        )
    .setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(0))
    .build();

    //go back to observation
    public static PathChain specPart2 = builder
    //go to observaiton
        .addPath(
            new BezierLine(
                new Point(101.5, 80.400, Point.CARTESIAN),
                new Point(132.600, 115.20, Point.CARTESIAN)
            )
    )
    .setLinearHeadingInterpolation(Math.toRadians(0),Math.toRadians(0))
    .build();

    public void PathUpdare() {
        switch (pathState) {
            case 0:
                claw.armHighChamber();
                follower.followPath(specPart1);
                setPathState(1);
                break;
            case 1:
                if (!follower.isBusy()) {
                    follower.followPath(specPart2);
                    claw.armWall();
                    setPathState(-1);
                }
        }
    }
        public void setPathState(int pState){
            pathState = pState;
            pathTimer.resetTimer();
        }
        
      */


    /**
     * This method is call once when init is played, it initializes the follower
     **/
    @Override
    public void init() {
        slide = new slide(hardwareMap,"mane");
        arm = new arm(hardwareMap,"sld");
        toolOp = new GamepadEx(gamepad2);
        claws = new claw(hardwareMap,"telemetry");
        intake = new intake(hardwareMap,"get in my belly");
        controller = new PIDController(p,i,d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        claw = new servoController(hardwareMap,telemetry);
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);
        CommandScheduler.getInstance().schedule(new retractSlides(slide));
    }

    /**
     * This method is called continuously after Init while waiting to be started.
     **/
    @Override
    public void init_loop() {

     // new  ScheduleCommand(new retractSlides(slide));
    CommandScheduler.getInstance().run();
    }

    /**
     * This method is called once at the start of the OpMode.
     **/
    @Override
    public void start() {
        follower.startTeleopDrive();
    }

    /**
     * This is the main loop of the opmode and runs continuously after play
     **/
    @Override
    public void loop() {

        /* Update Pedro to move the robot based on:
        - Forward/Backward Movement: -gamepad1.left_stick_y
        - Left/Right Movement: -gamepad1.left_stick_x
        - Turn Left/Right Movement: -gamepad1.right_stick_x
        - Robot-Centric Mode: true
        */

        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, false);
        //follower.setMaxPower(0.5);


        follower.update();

        //driver 2 controls
            //intake claw controls
            toolOp.getGamepadButton(GamepadKeys.Button.B)
                            .whenPressed(new ejectSample(arm,intake));
            //slides controls
            //pitch claw
            toolOp.getGamepadButton(GamepadKeys.Button.Y)
                            .whenHeld(new lowerClaw(intake));
            toolOp.getGamepadButton(GamepadKeys.Button.Y)
                            .whenInactive(new raseClaw(intake));
            if (gamepad1.a) {
                //follower.setCurrentPoseWithOffset(homePose);
                follower.setPose(homePose);
            }
            toolOp.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                            .whenPressed(new resetEncoder(slide));

            //specimen claw controls

            //arm controls
            toolOp.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                    .whenPressed(new closeClawAndArmChamber(arm,claws));
            toolOp.getGamepadButton((GamepadKeys.Button.DPAD_UP))
                            .whenActive(new armWall(arm));

            toolOp.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                    .whenPressed(new armWallAndOpenClaw(arm,claws));
            toolOp.getGamepadButton(GamepadKeys.Button.A)
                    .whenPressed(new extendSlides(slide));
            toolOp.getGamepadButton(GamepadKeys.Button.X)
                    .whenPressed(new retractSlides(slide));

        /**auto place spec on high chamber*/
       // if (gamepad1.b){
      //      setPathState(0);
       // }
        CommandScheduler.getInstance().run();

        /* Telemetry Outputs*/
        telemetry.addData("pathState:",pathState);
        //claw.servoTelemetry(telemetry);
        telemetry.addData("rotate",rotate);
        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading in Degrees", Math.toDegrees(follower.getPose().getHeading()));

        /* Update Telemetry to the Driver Hub */
        telemetry.update();

    }

    /**
     * We do not use this because everything automatically should disable
     **/
    @Override
    public void stop() {
       // ElapsedTime time = new ElapsedTime();
        //target = 0;
       // if(time.equals(5)){
        //    target = 0;
       // }

    }
}
