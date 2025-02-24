package xperamentals.command.comandGroups.SequentialCommandGroup;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import xperamentals.command.commands.armChamber;
import xperamentals.command.commands.armClawClose;
import xperamentals.subsystem.arm;
import xperamentals.subsystem.claw;

public class closeClawAndArmChamber extends SequentialCommandGroup{
    private arm arm;
    private claw intake;

    public closeClawAndArmChamber(arm Arm, claw Intake){
        arm = Arm;
        intake = Intake;
        addCommands(
                new armClawClose(intake),
                new armChamber(arm)
        );
        addRequirements(Arm);
    }
}
