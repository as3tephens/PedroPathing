package xperamentals.teleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

import xperamentals.controller.servoController;

@TeleOp(name = "servoDebug")
public class servoDebug extends LinearOpMode{
    private static int mode = 0;
    private static double rotate = 0.0;
    private static double pow = 0.0;
    private static double pitch = 0.0;
    private static float leftTriggerPrevious = 0;
    private servoController claw;
    @Override
    public void runOpMode() throws InterruptedException {
        claw = new servoController(hardwareMap,telemetry);
        telemetry.addData("debug0",hardwareMap.toString());
        telemetry.addData("debug1",hardwareMap.get(Servo.class,"release").toString());
        telemetry.update();
        claw.initServos();
        waitForStart();
        while(opModeIsActive()){
            if(gamepad2.left_trigger > 0 && leftTriggerPrevious == 0 && mode == 0){
                mode = 1;
            } else if (gamepad2.left_trigger > 0 && leftTriggerPrevious == 0 && mode == 1){
                mode = 0;
            }
            leftTriggerPrevious = gamepad2.left_trigger;
            if(gamepad2.dpad_left){
                mode = 1;
            } else if (gamepad1.dpad_right){
                mode = 0;
            }

            if(mode == 0) {
                //intake claw controls

                //slides controls


                if (gamepad2.b) {
                    //open claw
                    claw.open();
                } else if (gamepad2.a) {
                    //close claw
                    claw.close();
                }
                //pitch claw
                if (gamepad2.left_bumper) {
                    claw.pitchDown();
                } else if (gamepad2.right_bumper) {
                    //pitch up
                    claw.pitchUp();
                }
            } else if(mode == 1) {
                //specimen claw controls

                //claw controls
                if (gamepad2.b) {
                    //open claw
                    claw.armClawOpen();
                } else if (gamepad2.a) {
                    //close claw
                    claw.armClawClose();
                }

                //arm controls
                if (gamepad2.left_bumper) {
                    //set arm to high chamber position
                    pow += 10;
                    sleep(250);
                } else if (gamepad2.right_bumper) {
                    //set arm to pickup off wall
                    pow -= 10/8;
                    sleep(250);
                }
                if (gamepad2.dpad_up) {
                    rotate +=0.1;
                    sleep(250);
                } else if (gamepad2.dpad_down) {
                    rotate -=0.1;
                    sleep(250);
                }
                if(rotate > 1){
                    rotate = 1;
                } else if (rotate < 0){
                    rotate = 0;
                }
            }
                claw.up(rotate);
                claw.pitchArm(pow);
                telemetry.addData("mode",mode);
                claw.servoTelemetry(telemetry);
                telemetry.addData("Rotate:",rotate);
                telemetry.addData("pow",pow);
                telemetry.update();


        }
    }
}
