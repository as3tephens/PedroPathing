package xperamentals.command.commands;

import static android.os.SystemClock.sleep;

import com.arcrobotics.ftclib.command.CommandBase;
import xperamentals.subsystem.arm;
public class armWall extends CommandBase {

    private final arm Arm;

    public armWall(arm arm){
        Arm = arm;
        addRequirements(arm);
    }
    @Override
    public void initialize(){
        Arm.armW();
    }

    @Override
    public boolean isFinished(){
        sleep(50);
        return true;
    }
}
