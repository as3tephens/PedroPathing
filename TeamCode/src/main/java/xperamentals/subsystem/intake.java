package xperamentals.subsystem;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class intake extends SubsystemBase {

    private Telemetry telemetry;

    private Servo clawPitch;

    private Servo release;

    public intake(HardwareMap hMap, String name){


        release = hMap.get(Servo.class,"release");

        clawPitch = hMap.get(Servo.class,"clawPitch");
    }



    public void drop(){
        release.setPosition(0);
    }
    public void lower(){
        clawPitch.setPosition(1);
    }
    public void rase() {
        clawPitch.setPosition(0.5);
    }
    @Override
    public void periodic(){

    }
}
