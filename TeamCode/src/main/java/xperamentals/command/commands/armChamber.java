package xperamentals.command.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import xperamentals.subsystem.arm;

public class armChamber extends CommandBase {
    private final arm Arm;

    public armChamber(arm arm){
        Arm = arm;
        addRequirements(arm);
    }
    @Override
    public void initialize(){
        Arm.armC();
    }

    @Override
    public boolean isFinished(){
        return true;
    }
}
