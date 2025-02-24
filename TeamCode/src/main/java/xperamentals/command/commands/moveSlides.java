package xperamentals.command.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDController;

import xperamentals.subsystem.slide;

public class moveSlides extends CommandBase {
    private xperamentals.subsystem.slide intake;
    private PIDController pid;
    public static double p = 0.004, i = 0, d = 0.00001;
    private int target = 0;
    public moveSlides(slide in, int targets){
        target = targets;
        intake = in;
        pid = new PIDController(p,i,d);
        addRequirements(in);
    }
    @Override
    public void initialize(){

        pid.setPID(p,i,d);
    }
    @Override
    public void execute(){
        intake.setPower(pid.calculate(intake.slidePose(),target));
    }
    @Override
    public boolean isFinished(){
        if (intake.slidePose() + target > 1440 && intake.slidePose() < 1460){
            return true;
        } else {
            return false;
        }
    }
}
