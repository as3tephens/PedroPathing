package xperamentals.command.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import xperamentals.subsystem.claw;

public class armClawOpen extends CommandBase {
    private final claw arm;

    public armClawOpen(claw Intake){
        arm = Intake;
        addRequirements(Intake);
    }

    @Override
    public void initialize(){
        arm.armClawOpen();
    }
    @Override
    public boolean isFinished(){
        return true;
    }
}
