package xperamentals.command.commands;

import static android.os.SystemClock.sleep;

import com.arcrobotics.ftclib.command.CommandBase;

import xperamentals.subsystem.intake;

public class raseClaw extends CommandBase {
    private final intake intake;

    public raseClaw(intake in){
        intake = in;
        addRequirements(in);
    }
    @Override
    public void initialize(){
        intake.rase();
    }
    @Override
    public boolean isFinished(){
        //sleep(1000);
        return true;
    }
}
