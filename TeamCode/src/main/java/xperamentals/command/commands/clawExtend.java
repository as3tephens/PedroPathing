package xperamentals.command.commands;

import static android.os.SystemClock.sleep;

import com.arcrobotics.ftclib.command.CommandBase;

import xperamentals.subsystem.intake;

public class clawExtend extends CommandBase {
    private final intake intake;

    public clawExtend(intake in){
        intake = in;
        addRequirements(in);
    }
    @Override
    public void initialize(){
        intake.drop();
    }
    @Override
    public boolean isFinished(){
        sleep(200);
        return true;
    }
}
