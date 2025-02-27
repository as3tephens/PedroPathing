package xperamentals.subsystem;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
@Config

public class arm extends SubsystemBase {
    private Servo armPitchR;
    private Telemetry telemetry;
    private Servo armPitchL;
    public static double angleWall = 35;
    public static double angleChamber = 190;
    private Servo release;

    public arm(HardwareMap hMap, String name){
        armPitchR = hMap.get(Servo.class,"armPitchR");
        armPitchL = hMap.get(Servo.class,"armPitchL");
        release = hMap.get(Servo.class,"release");
        armPitchR.setDirection(Servo.Direction.REVERSE);
    }
    public void armC(){
        armPitchR.setPosition((double) angleChamber /270);
        armPitchL.setPosition((double) angleChamber /270);
    }
    public void armW(){
        armPitchR.setPosition((double) angleWall /270);
        armPitchL.setPosition((double) angleWall /270);
    }
    public void nuteral(){
        release.setPosition(0.5);
    }
    public void moveArm(double angle){
        armPitchR.setPosition(angle / 270);
        armPitchL.setPosition(angle / 270);
    }

    @Override
    public void periodic(){
    }
}
