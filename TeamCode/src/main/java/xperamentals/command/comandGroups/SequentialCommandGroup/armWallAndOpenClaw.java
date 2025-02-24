package xperamentals.command.comandGroups.SequentialCommandGroup;

import static android.os.SystemClock.sleep;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import xperamentals.command.commands.armClawClose;
import xperamentals.command.commands.armClawOpen;
import xperamentals.command.commands.armWall;
import xperamentals.subsystem.arm;
import xperamentals.subsystem.claw;

public class armWallAndOpenClaw extends SequentialCommandGroup {
    private arm arm;
    private claw intake;

    public armWallAndOpenClaw(arm Arm,claw Intake){
        arm = Arm;
        intake = Intake;
        addCommands(
                new armWall(arm),
                new armClawOpen(intake)
        );
        addRequirements(Arm);
    }
}
