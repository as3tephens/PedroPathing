package xperamentals.auto;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ScheduleCommand;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.ServoController;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;
import xperamentals.command.comandGroups.SequentialCommandGroup.armWallAndOpenClaw;
import xperamentals.command.commands.armChamber;
import xperamentals.command.commands.armClawClose;
import xperamentals.controller.servoController;
import xperamentals.controller.slideControler;
import xperamentals.subsystem.*;

@Autonomous(name = "barPark")
public class specPark extends OpMode{
    private static servoController servo;
    private claw claw;
    private arm arm;

    private Follower follower;

    private Timer pathTimer, actionTimer, opmodeTimer;

    private final Pose startPose = new Pose(135.835661462613, 78.5669679539852, Math.toRadians(90));


        public static PathBuilder builder = new PathBuilder();

        public static PathChain line1 = builder
                .addPath(
                        new BezierLine(
                                new Point(135.836, 78.567, Point.CARTESIAN),
                                new Point(124.948, 77.095, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(90))
                .build();

        public static PathChain line2 = builder
                .addPath(
                        new BezierLine(
                                new Point(124.948, 77.095, Point.CARTESIAN),
                                new Point(113.649, 75.545, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(0))
                .build();

        public static PathChain line3 = builder
                .addPath(
                        new BezierLine(
                                new Point(113.649, 75.545, Point.CARTESIAN),
                                new Point(104.788, 72.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        public static PathChain line4 = builder
                .addPath(
                        new BezierLine(
                                new Point(104.788, 72.000, Point.CARTESIAN),
                                new Point(135.138, 126.055, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();


    private int pathState;

    public void setPathState(int pState){
        pathState = pState;
        pathTimer.resetTimer();
    }
    public void autonumousPathUpdate() {
        switch(pathState)
        {
            case 0:
                //servo.armHighChamber();
                CommandScheduler.getInstance().schedule(new armChamber(arm));
                follower.followPath(line1);
                setPathState(1);
                break;

            case 1:
                if(!follower.isBusy())
                {
                    follower.followPath(line2);
                    setPathState(2);
                    break;
                }
            case 2:
                if(!follower.isBusy())
                {
                    follower.followPath(line3,true);
                    setPathState(3);
                    break;
                }
            case 3:
                if(!follower.isBusy())
                {
                    //servo.armWall();
                   // servo.armClawOpen()
                    CommandScheduler.getInstance().schedule(new armWallAndOpenClaw(arm,claw));
                    CommandScheduler.getInstance().run();
                    follower.followPath(line4,true);
                    setPathState(-1);
                    break;
                }
        }
    }

    @Override
    public void init()
    {
        //Servo initialize//
       // servo = new servoController(hardwareMap, telemetry);
       // servo.initServos();

        //Follower hardware map//
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);
        claw = new claw(hardwareMap,"s");
        arm = new arm(hardwareMap,"s");
        CommandScheduler.getInstance().schedule(new armClawClose(claw));

        //Timers//
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

    }
    @Override
    public  void start()
    {
        follower.setMaxPower(0.5);

        opmodeTimer.resetTimer();

        setPathState(0);
    }


    @Override
    public void loop()
    {
        //movement yap bullshit//
        follower.update();
        autonumousPathUpdate();
        CommandScheduler.getInstance().run();

        //telemetry//
       // servo.servoTelemetry(telemetry);
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();

    }
}
