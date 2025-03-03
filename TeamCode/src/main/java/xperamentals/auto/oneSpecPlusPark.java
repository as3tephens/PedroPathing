package xperamentals.auto;

import static android.os.SystemClock.sleep;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

import xperamentals.command.comandGroups.SequentialCommandGroup.armWallAndOpenClaw;
import xperamentals.subsystem.*;

import xperamentals.command.commands.armChamber;
import xperamentals.controller.servoController;


import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name = "oneSpecamenPlusPark")
public class oneSpecPlusPark extends OpMode {
    //defining poses
    private final servoController servos = new servoController(hardwareMap,telemetry);
    private final Pose startPose = new Pose(8.5,64,Math.toRadians(180));
    private final Pose barPose = new Pose(40,67,Math.toRadians(180));
    private final Pose endPose = new Pose(8.64,16.7,Math.toRadians(180));
    private Path bar1;
    private Path park;
    private arm arm;
    private claw claw;

    //making follower
    private Follower follower;
    private int pathState;
    private Timer pathTimer, actionTimer, opmodeTimer;

    public void buildPaths() {
        bar1 = new Path(new BezierLine(new Point(startPose), new Point(barPose)));
        bar1.setLinearHeadingInterpolation(startPose.getHeading(),barPose.getHeading());
        park = new Path(new BezierLine((new Point(barPose)),new Point(endPose)));
        park.setLinearHeadingInterpolation(barPose.getHeading(), endPose.getHeading());
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                //fallow path
                follower.followPath(bar1);
                setPathState(1);
                //set arm position
                //servos.armHighChamber();
                CommandScheduler.getInstance().schedule(new armChamber(arm));
                break;
            case 1:
                if(!follower.isBusy()){
                    //open claw
                    //servos.armClawOpen();

                    //put arm in robot
                    //servos.armWall();
                    CommandScheduler.getInstance().schedule(new armWallAndOpenClaw(arm,claw));
                    sleep(500);
                    follower.followPath(park);
                    setPathState(-1);
                }
        }
    }
    @Override
    public void init() {
        pathTimer = new Timer();
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);
        claw = new claw(hardwareMap,"s");
        arm = new arm(hardwareMap,"a");
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());


        buildPaths();
    }

    public void setPathState(int pState){
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();
        CommandScheduler.getInstance().run();


        //telemetry
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }

}
