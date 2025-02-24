package xperamentals.subsystem;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class arm extends SubsystemBase {
    private Servo armPitchR;
    private Telemetry telemetry;
    private Servo armPitchL;
    private Servo release;

    public arm(HardwareMap hMap, String name){
        armPitchR = hMap.get(Servo.class,"armPitchR");
        armPitchL = hMap.get(Servo.class,"armPitchL");
        release = hMap.get(Servo.class,"release");
        armPitchR.setDirection(Servo.Direction.REVERSE);
    }
    public void armC(){
        armPitchR.setPosition((double) 190 /270);
        armPitchL.setPosition((double) 190 /270);
    }
    public void armW(){
        armPitchR.setPosition((double) 35 /270);
        armPitchL.setPosition((double) 35 /270);
    }
    public void nuteral(){
        release.setPosition(0.5);
    }

    @Override
    public void periodic(){
    }
}
