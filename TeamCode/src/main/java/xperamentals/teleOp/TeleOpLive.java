package xperamentals.teleOp;

import android.provider.SyncStateContract;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

import xperamentals.controller.servoController;
import xperamentals.controller.slideControler;
/**
 * This is an example teleop that showcases movement and robot-centric driving.
 *
 * @author Baron Henderson - 20077 The Indubitables
 * @version 2.0, 12/30/2024
 */

@TeleOp(name = "TeleOpLive", group = "TeleOp")
public class TeleOpLive extends OpMode {
    private Follower follower;
    private slideControler slides;
    private final Pose startPose = new Pose(0, 0, 0);

    private servoController claw;
    private double rotate = 0.0;
    private static int mode = 0;
    //Added by Nathan Hall
    private static float leftTriggerPrevious = 0;



    /**
     * This method is call once when init is played, it initializes the follower
     **/
    @Override
    public void init() {
        slides  = new slideControler(hardwareMap);
        claw = new servoController(hardwareMap,telemetry);
        claw.initServos();
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);
    }

    /**
     * This method is called continuously after Init while waiting to be started.
     **/
    @Override
    public void init_loop() {
        claw.initServos();
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

        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);
        follower.update();

        //driver 2 controls
if(gamepad2.dpad_left){
    mode = 1;
} else if(gamepad2.dpad_right){
    mode = 0;
}
        //switch mode
        if(gamepad2.left_trigger > 0 && leftTriggerPrevious == 0 && mode == 0){
            mode = 1;
        } else if (gamepad2.left_trigger > 0 && leftTriggerPrevious == 0 && mode == 1){
            mode = 0;
        }
        leftTriggerPrevious = gamepad2.left_trigger;

        if(mode == 0) {
            //intake claw controls

            //slides controls


            if (gamepad2.b) {
                //open claw
                claw.open();
            } else if (gamepad2.x) {
                //close claw
                claw.nuteral();
            }
            //pitch claw
            if (gamepad2.left_bumper) {
                claw.pitchDown();
            } else if (gamepad2.right_bumper) {
                //pitch up
                claw.pitchUp();
            }
        } else if(mode == 1){
            //specimen claw controls

            //claw controls
            if (gamepad2.b){
                //open claw
                claw.armClawOpen();
            } else if (gamepad2.a){
                //close claw
                claw.armClawClose();
            }
            if (gamepad2.dpad_up){
                rotate +=0.1;
            } else if (gamepad2.dpad_down){
                rotate -=0.1;
            }
            //arm controls
            if (gamepad2.left_bumper){
                //set arm to high chamber position
                claw.armHighChamber();
            } else if (gamepad2.right_bumper){
                //set arm to pickup off wall
                claw.armWall();
            }
            claw.angle(rotate);
        }

        slides.moveSlides(gamepad2.left_stick_y);


        /* Telemetry Outputs*/
        claw.servoTelemetry(telemetry);
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
    }
}