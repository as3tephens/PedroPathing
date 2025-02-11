package xperamentals.teleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import xperamentals.controller.servoController;

@TeleOp(name = "servoDebug")
public class servoDebug extends LinearOpMode{
    private static int mode = 0;
    private static float leftTriggerPrevious = 0;
    private servoController claw = new servoController(hardwareMap);
    @Override
    public void runOpMode() throws InterruptedException {
        while(opModeIsActive()){
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

                //arm controls
                if (gamepad2.left_bumper){
                    //set arm to high chamber position
                    claw.armHighChamber();
                } else if (gamepad2.right_bumper){
                    //set arm to pickup off wall
                    claw.armWall();
                }

            }
        }
    }
}
