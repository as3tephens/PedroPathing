package xperamentals.auto;

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
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous(name = "park")
public class park extends OpMode {
//defining poses
    private final Pose startPose = new Pose(8.5,64,Math.toRadians(0));
    private final Pose endPose = new Pose(8.64,16.7,Math.toRadians(0));
    private Path park;

    //making follower
    private Follower follower;
    private int pathState;
    private Timer pathTimer, actionTimer, opmodeTimer;

    public void buildPaths() {
        park = new Path(new BezierLine((new Point(startPose)),new Point(endPose)));
        park.setLinearHeadingInterpolation(startPose.getHeading(), endPose.getHeading());
    }

    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(park);
                pathState = 1;
                break;
        }
    }
    @Override
    public void init() {
        pathTimer = new Timer();
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);
        buildPaths();
    }

    @Override
    public void loop() {
        follower.update();
        autonomousPathUpdate();
        telemetry.addData("Path State", pathState);
        telemetry.addData("Position", follower.getPose().toString());
        telemetry.update();
    }

}
