package xperamentals.command.commands;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import xperamentals.subsystem.*;
@Config
public class armSlide extends CommandBase {
private arm arm;
private double target;


    public armSlide(arm Arm){
        arm = Arm;
        addRequirements(Arm);
    }
    @Override
    public void initialize(){
        arm.moveArm(target);
    }
    @Override
    public boolean isFinished(){
        //sleep(1000);
        return true;
    }
}


