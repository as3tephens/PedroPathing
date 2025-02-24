package xperamentals.command.commands;

import static android.os.SystemClock.sleep;

import com.arcrobotics.ftclib.command.CommandBase;

import xperamentals.subsystem.intake;

public class lowerClaw extends CommandBase {
    private final xperamentals.subsystem.intake intake;

    public lowerClaw(intake in){
        intake = in;
        addRequirements(in);
    }
    @Override
    public void initialize(){
        intake.lower();
    }
    @Override
    public boolean isFinished(){
        sleep(1000);
        return true;
    }
}
