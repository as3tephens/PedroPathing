package xperamentals.command.commands;

import static android.os.SystemClock.sleep;

import com.arcrobotics.ftclib.command.CommandBase;
import xperamentals.subsystem.claw;

public class armClawClose extends CommandBase {
    private final claw arm;

    public armClawClose(claw intake){
        arm = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize(){
        arm.armClawClose();
    }

    @Override
    public boolean isFinished(){
        sleep(500);
        return true;
    }
}
