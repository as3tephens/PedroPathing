package xperamentals.teleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import xperamentals.controller.servoController;

@TeleOp(name = "servoDebug")
public class servoDebug extends LinearOpMode{
    private servoController servo = new servoController();
    @Override
    public void runOpMode() throws InterruptedException {
        while(opModeIsActive()){
            if (gamepad2.a) {
                servo.pitchDown();
            } else if (gamepad2.b) {
                servo.pitchUp();
            } else if (gamepad2.x){
                servo.open();
            }
        }
    }
}
