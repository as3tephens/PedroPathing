package xperamentals.teleOp.test;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@Config
@TeleOp(name = "pidTuner")
public class pidTuner extends OpMode {

    private PIDController controller;
    public static double p = 0.004, i = 0, d = 0.00001;

    public static int target = 0;

    private final double ticks_in_degrese = 360/537.6;
    //Motors varables
    private DcMotorEx slides;


    @Override
    public void init() {
        controller = new PIDController(p,i,d);

        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        slides = hardwareMap.get(DcMotorEx.class,"slides");
        slides.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slides.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    @Override
    public void loop() {
        int slidePos = slides.getCurrentPosition();
        controller.setPID(p,i,d);
        double pid = controller.calculate(slidePos,target);
        slides.setPower(pid);


        telemetry.addData("pid:",pid);
        telemetry.addData("target",target);
        telemetry.addData("slides:",slidePos);
        telemetry.addData("p",p);
        telemetry.addData("i",i);
        telemetry.addData("d",d);
    }
}
