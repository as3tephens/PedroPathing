package xperamentals.auto;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.ScheduleCommand;
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
//import xperamentals.controller.servoController;
//import xperamentals.controller.slideControler;
import xperamentals.command.commands.*;
import xperamentals.command.comandGroups.SequentialCommandGroup.*;
import xperamentals.subsystem.arm;
import xperamentals.subsystem.claw;
import xperamentals.subsystem.intake;
import xperamentals.subsystem.slide;
@Config
@Autonomous(name = "4 + 0")
public class fourPlusOAuto extends OpMode {
    private Follower follower;
    private CommandScheduler commander;                                                           //Nathan Change
    private xperamentals.subsystem.arm arm;
    private xperamentals.subsystem.claw claw;
    private xperamentals.subsystem.slide slide;
    private Timer pathTimer, actionTimer,opmodeTimer;
    public static double speed = 0.5;
    private int pathState;
//    private servoController servo;
//    private slideControler slides;
    private final Pose startPose = new Pose(135.3,80.8,Math.toRadians(90));
    public static PathBuilder builder = new PathBuilder();

    public static PathChain line1 = builder
            .addPath(
                    new BezierLine(
                            new Point(135.300, 80.800, Point.CARTESIAN),
                            new Point(124.948, 77.095, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(90))
            .build();

    public static PathChain line2 = builder
            .addPath(
                    new BezierLine(
                            new Point(124.948, 77.095, Point.CARTESIAN),
                            new Point(113.700, 75.600, Point.CARTESIAN)
                    )
            )
            .setLinearHeadingInterpolation(Math.toRadians(90), Math.toRadians(0))
            .build();

    public static PathChain line3 = builder
            .addPath(
                    new BezierLine(
                            new Point(113.700, 75.600, Point.CARTESIAN),
                            new Point(104.000, 66.800, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(0))
            .build();

    public static PathChain line4 = builder
            .addPath(
                    new BezierCurve(
                            new Point(104.000, 66.800, Point.CARTESIAN),
                            new Point(130.043, 96.591, Point.CARTESIAN),
                            new Point(110.200, 105.900, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(0))
            .build();

    public static PathChain line5 = builder
            .addPath(
                    new BezierLine(
                            new Point(110.200, 105.900, Point.CARTESIAN),
                            new Point(86.000, 108.200, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(0))
            .build();

    public static PathChain line6 = builder
            .addPath(
                    new BezierLine(
                            new Point(86.000, 108.200, Point.CARTESIAN),
                            new Point(86.467, 117.062, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(0))
            .build();

    public static PathChain line7 = builder
            .addPath(
                    new BezierLine(
                            new Point(86.467, 117.062, Point.CARTESIAN),
                            new Point(130.000, 118.800, Point.CARTESIAN)
                    )
            )
            .setTangentHeadingInterpolation()
            .build();

    public static PathChain line8 = builder
            .addPath(
                    new BezierCurve(
                            new Point(130.000, 118.800, Point.CARTESIAN),
                            new Point(69.506, 106.420, Point.CARTESIAN),
                            new Point(85.957, 126.000, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(0))
            .build();

    public static PathChain line9 = builder
            .addPath(
                    new BezierLine(
                            new Point(85.957, 126.000, Point.CARTESIAN),
                            new Point(130.000, 126.000, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(0))
            .build();

    public static PathChain line10 = builder
            .addPath(
                    new BezierLine(
                            new Point(130.000, 126.000, Point.CARTESIAN),
                            new Point(103.600, 68.900, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(0))
            .build();

    public static PathChain line11 = builder
            .addPath(
                    new BezierLine(
                            new Point(103.600, 68.900, Point.CARTESIAN),
                            new Point(134.200, 118.800, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(0))
            .build();

    public static PathChain line12 = builder
            .addPath(
                    new BezierLine(
                            new Point(134.200, 118.800, Point.CARTESIAN),
                            new Point(103.680, 71.000, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(0))
            .build();

    public static PathChain line13 = builder
            .addPath(
                    new BezierLine(
                            new Point(103.680, 71.000, Point.CARTESIAN),
                            new Point(133.000, 118.800, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(0))
            .build();

    public static PathChain line14 = builder
            .addPath(
                    new BezierLine(
                            new Point(133.000, 118.800, Point.CARTESIAN),
                            new Point(103.680, 73.000, Point.CARTESIAN)
                    )
            )
            .setConstantHeadingInterpolation(Math.toRadians(0))
            .build();

    public static PathChain line15 = builder
            .addPath(
                    new BezierLine(
                            new Point(103.680, 73.000, Point.CARTESIAN),
                            new Point(120.800, 102.800, Point.CARTESIAN)
                    )
            )
            .setTangentHeadingInterpolation()
            .build();
    public void setPathState(int pState){
        pathState = pState;
        pathTimer.resetTimer();
    }
    public void autonumousPathUpdate(){
        switch(pathState){
            case 0:
                new armChamber(arm).schedule();
                follower.followPath(line1);
                setPathState(1);
                break;
            case 1:
                if(!follower.isBusy()){

                    follower.followPath(line2);
                    setPathState(2);
                    break;
                }
            case 2:
                if(!follower.isBusy()){
                    follower.followPath(line3);
                    setPathState(3);
                    break;
                }
            case 3:
                if(!follower.isBusy()) {
                    //CommandScheduler.getInstance().schedule(new armWallAndOpenClaw(arm,claw));
                    new armWallAndOpenClaw(arm,claw).schedule();
                    follower.followPath(line4);
                    setPathState(4);
                    break;
                }
            case 4:
                if(!follower.isBusy()){
                    follower.followPath(line5,1,false);
                    setPathState(5);
                    break;
                }
            case 5:
                if(!follower.isBusy()){
                    follower.followPath(line6);
                    setPathState(6);
                    break;
                }
            case 6:
                if(!follower.isBusy()){
                    follower.followPath(line7);
                    setPathState(7);
                    break;
                }
            case 7:
                if(!follower.isBusy()){
                    follower.followPath(line8);
                    setPathState(8);
                    break;
                }
            case 8:
                if(!follower.isBusy()){
                    follower.followPath(line9);
                    setPathState(11);
                    break;
                }
           case 9:
                if(!follower.isBusy()){
                    follower.followPath(line10);
                    setPathState(10);
                    break;
                }
            case 10:
                if(!follower.isBusy()){
                    follower.followPath(line11);
                    setPathState(11);
                    break;
                }
            case 11:
                if(!follower.isBusy()){
                    //CommandScheduler.getInstance().schedule(new closeClawAndArmChamber(arm,claw));
                    new closeClawAndArmChamber(arm,claw).schedule();
                    follower.followPath(line12);
                    setPathState(12);
                    break;
                }
            case 12:
                if(!follower.isBusy()){
                    //CommandScheduler.getInstance().schedule(new armWallAndOpenClaw(arm,claw));
                    new armWallAndOpenClaw(arm,claw).schedule();
                    follower.followPath(line13,true);
                    setPathState(13);
                    break;
                }
            case 13:
                if(!follower.isBusy()){
                    //CommandScheduler.getInstance().schedule(new armWallAndOpenClaw(arm,claw));
                    new closeClawAndArmChamber(arm,claw).schedule();
                    follower.followPath(line14,true);
                    setPathState(14);
                    break;
                }
            case 14:
                if(!follower.isBusy()){
                    //CommandScheduler.getInstance().schedule(new closeClawAndArmChamber(arm,claw));
                    new armWallAndOpenClaw(arm,claw).schedule();
                    follower.followPath(line15,true);
                    new extendSlides(slide).schedule();
                    setPathState(55);
                    break;
                }
        }
    }

@Override
public void init_loop(){
       // CommandScheduler.getInstance().run();                                                     //Nathan Change
}
    @Override
    public void init() {
        arm = new arm(hardwareMap,"cliper");
        slide = new slide(hardwareMap,"extender");
        claw = new claw(hardwareMap,"clawer");
        //CommandScheduler.getInstance().schedule(new armClawClose(claw));
        new armClawClose(claw).schedule();
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);
        commander = CommandScheduler.getInstance();                                               //Nathan Change
        commander.run();                                                                          //Nathan Change
    }
    @Override
    public void start(){
        follower.setMaxPower(speed);
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    @Override
    public void loop() {
        follower.update();
        autonumousPathUpdate();
        CommandScheduler.getInstance().run();
        commander.run();
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();

    }
}
