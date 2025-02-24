package xperamentals.command.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import xperamentals.subsystem.arm;

public class clawNuteral extends CommandBase {
    private final arm arm;

    public clawNuteral(arm Arm){
        arm = Arm;
        addRequirements(Arm);
    }
    @Override
    public void initialize(){
        arm.nuteral();
    }
    @Override
    public boolean isFinished(){
        return true;
    }
}