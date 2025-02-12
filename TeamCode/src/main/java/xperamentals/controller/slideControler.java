package xperamentals.controller;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
public class slideControler {
    //Motors varables
    private DcMotor slides;
    private static HardwareMap hardwareMap;

    //init slides
    public slideControler(HardwareMap oldhardwareMap){
        hardwareMap = oldhardwareMap;
        slides = hardwareMap.get(DcMotor.class,"slides");
    }

    //control slide motor
    public void moveSlides(double power){
        slides.setPower(power);
    }
}
