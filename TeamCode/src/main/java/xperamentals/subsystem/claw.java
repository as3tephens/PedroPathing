package xperamentals.subsystem;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class claw extends SubsystemBase {
    private Servo armClaw;
    public claw(HardwareMap hMap, String name){
        armClaw = hMap.get(Servo.class,"armClaw");
    }
    public void armClawClose(){
        armClaw.setPosition((double) 0 /300);
    }
    public void armClawOpen(){
        armClaw.setPosition((double) 180/300);
    }
    @Override
    public void periodic(){
    }
}
