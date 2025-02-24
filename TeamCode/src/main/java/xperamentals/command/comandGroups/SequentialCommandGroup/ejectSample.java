package xperamentals.command.comandGroups.SequentialCommandGroup;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import xperamentals.command.commands.clawExtend;
import xperamentals.command.commands.clawNuteral;
import xperamentals.subsystem.arm;
import xperamentals.subsystem.intake;

public class ejectSample extends SequentialCommandGroup {
    private final arm arm;
    private final intake intake;

    public ejectSample(arm Arm, intake Intake){
        arm = Arm;
        intake = Intake;
        addCommands(
                new clawExtend(intake),
                new clawNuteral(arm)
        );
        addRequirements(Arm);
    }
}
