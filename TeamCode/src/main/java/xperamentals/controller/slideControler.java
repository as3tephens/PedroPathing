package xperamentals.controller;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
@Config
public class slideControler {
    private PIDController controller;
    public static double p = 0, i = 0, d = 0;

    public static int target = 0;

    private final double ticks_in_degrese = 360/537.6;
    //Motors varables
    private DcMotorEx slides;
    private static HardwareMap hardwareMap;

    //init slides
    public slideControler(HardwareMap oldhardwareMap,Telemetry telemetry){
//        hardwareMap = oldhardwareMap;
        controller = new PIDController(p,i,d);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        slides = oldhardwareMap.get(DcMotorEx.class,"slides");
    }

    //control slide motor
    public void moveSlides(int pos){
        controller.setPID(p,i,d);
        double pid = controller.calculate(slides.getCurrentPosition(), pos);
        slides.setPower(pid);
    }
}
