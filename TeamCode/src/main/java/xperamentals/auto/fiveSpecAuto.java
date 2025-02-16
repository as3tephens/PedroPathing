package xperamentals.auto;

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
import xperamentals.controller.servoController;
import xperamentals.controller.slideControler;

@Autonomous(name = "5 + 0")
public class fiveSpecAuto extends OpMode{
    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    private int pathSate;
    public static PathBuilder builder = new PathBuilder();
    private servoController servo;
    private slideControler slides;
    private final Pose startPose = new Pose(135.836,78.567);

    public static PathChain spec1 = builder
            .addPath(
                    // Line 1
                    new BezierLine(
                            new Point(135.836, 78.567, Point.CARTESIAN),
                            new Point(105.308, 74.307, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
            .build();

    /**push the samples into obser*/
    public static PathChain pushSamplesInPart1 = builder
            .addPath(
                    new BezierCurve(
                            new Point(105.190, 74.307, Point.CARTESIAN),
                            new Point(118.915, 107.083, Point.CARTESIAN),
                            new Point(84.483, 110.869, Point.CARTESIAN)
                    )
            )
            .build();
    public static PathChain getPushSamplesInPart2 = builder
            .addPath(
                    new BezierCurve(
                            new Point(84.483, 110.869, Point.CARTESIAN),
                            new Point(76.437, 116.076, Point.CARTESIAN),
                            new Point(86.731, 121.045, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(0))

            .addPath(
                    new BezierLine(
                            new Point(86.731, 121.045, Point.CARTESIAN),
                            new Point(130.866, 120.454, Point.CARTESIAN)
                    )
            )
            .setTangentHeadingInterpolation()

            .addPath(
                    new BezierCurve(
                            new Point(130.866, 120.454, Point.CARTESIAN),
                            new Point(92.056, 113.591, Point.CARTESIAN),
                            new Point(87.323, 128.145, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(0))

            .addPath(
                    new BezierLine(
                            new Point(87.323, 128.145, Point.CARTESIAN),
                            new Point(130.511, 130.038, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(0))

            .addPath(
                    new BezierCurve(
                            new Point(130.511, 130.038, Point.CARTESIAN),
                            new Point(81.880, 120.454, Point.CARTESIAN),
                            new Point(86.731, 134.8, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(0))
            .addPath(
                    new BezierLine(
                            new Point(86.731, 134.8, Point.CARTESIAN),
                            new Point(130.393, 134.8, Point.CARTESIAN)
                    )
            )
            .setTangentHeadingInterpolation()
            .build();
    public static PathChain spec2part1 = builder
            .addPath(
                    new BezierLine(
                            new Point(130.393, 134.800, Point.CARTESIAN),
                            new Point(104.800, 76.800, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
            .build();
    public static PathChain spec2Part2 = builder
//go back to observation
            .addPath(
                    new BezierLine(
                            new Point(104.800, 76.800, Point.CARTESIAN),
                            new Point(132.600, 115.200, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(0))
            .build();
    public static PathChain spec3part1 = builder
            .addPath(
                    //to chambers
                    new BezierLine(
                            new Point(132.600, 115.200, Point.CARTESIAN),
                            new Point(105.5, 80.400, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
            .build();
    public static PathChain spec3part2 = builder
//observation
            .addPath(
                    new BezierLine(
                            new Point(101.300, 80.400, Point.CARTESIAN),
                            new Point(132.600, 115.200, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(0))
            .build();
    public static PathChain spec4part1 = builder
            .addPath(
                    //bar
                    new BezierLine(
                            new Point(132.600, 115.200, Point.CARTESIAN),
                            new Point(105.500, 82.400, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
            .build();
    public static PathChain spec4part2 = builder
//observation
            .addPath(
                    new BezierLine(
                            new Point(105.500, 82.400, Point.CARTESIAN),
                            new Point(132.600, 115.200, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(0))
            .build();
    public static PathChain spec5part1 = builder
            .addPath(
                    new BezierLine(
                            new Point(132.600, 115.200, Point.CARTESIAN),
                            new Point(105.500, 84.100, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(0))
            .build();
    public static PathChain spec5part2 = builder
            .addPath(
                    new BezierLine(
                            new Point(105.500, 84.100, Point.CARTESIAN),
                            new Point(132.600, 115.200, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(0))
            .build();
    public void setPathState(int pState){
        pathSate = pState;
        pathTimer.resetTimer();
    }
public void autonumousPathUpdate(){
    switch(pathSate){
        case 0:
            servo.armHighChamber();
            follower.followPath(spec1);
            setPathState(1);
            break;
        case 1:
            if(!follower.isBusy()) {
                servo.armClawOpen();
                //the path is broken up so we can put the arm in
                follower.followPath(pushSamplesInPart1);
                setPathState(2);
                break;
            }
            break;
        case 2:
            if(!follower.isBusy()){
                servo.armWall();
                //hold end so we can gram sample
                follower.followPath(getPushSamplesInPart2, true);
                setPathState(3);
                break;
            }
        case 3:
            if(!follower.isBusy()){
                servo.armClawClose();
                if(!servo.isArmClawOpen()) {
                    servo.armHighChamber();
                    follower.followPath(spec2part1);
                    setPathState(4);
                    break;
                }
            }
        case 4:
            if(!follower.isBusy()){
             servo.armWall();
             servo.armClawOpen();
             follower.followPath(spec2Part2,true);
             setPathState(5);
             break;
            }
        case 5:
            if(!follower.isBusy()) {
                servo.armClawClose();
                servo.armHighChamber();
                follower.followPath(spec3part1);
                setPathState(6);
            }
        case 6:
            if (!follower.isBusy()){
                servo.armClawOpen();
                servo.armWall();
                follower.followPath(spec3part2,true);
                setPathState(7);
            }
        case 7:
            if(!follower.isBusy()){
                servo.armClawClose();
                servo.armHighChamber();
                follower.followPath(spec4part1);
                setPathState(8);
            }
        case 8:
            if (!follower.isBusy()){
                servo.armClawOpen();
                servo.armWall();
                follower.followPath(spec4part2,true);
                setPathState(9);
            }
        case 9:
            if (!follower.isBusy()){
                servo.armClawClose();
                servo.armHighChamber();
                follower.followPath(spec5part1);
                setPathState(10);
            }
        case 10:
            if (!follower.isBusy()){
                servo.armClawOpen();
                servo.armWall();
                slides.moveSlides(1);
                follower.followPath(spec5part2,true);
            }
    }
}
    @Override
    public void init() {
    //init servos and make servo controller
    servo = new servoController(hardwareMap, telemetry);
    servo.initServos();

    //init slides
        slides = new slideControler(hardwareMap);


    //init pedro things
        pathTimer = new Timer();
        opmodeTimer = new Timer();

        opmodeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);


    }

    @Override
    public void start(){
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    @Override
    public void loop() {
        //movment of robot
    follower.update();
    autonumousPathUpdate();

    //telemetrey
        servo.servoTelemetry(telemetry);
        telemetry.addData("path state", pathSate);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }
}
