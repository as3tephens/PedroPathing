package xperamentals.command.commands;

import static android.os.SystemClock.sleep;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.controller.PIDController;

import xperamentals.subsystem.intake;
import xperamentals.subsystem.slide;

public class extendSlides extends CommandBase {
    private slide slides;
    private PIDController pid;
    public static double p = 0.004, i = 0, d = 0.00001;
    private int target = 0;
    public extendSlides(slide in){
        slides = in;
        pid = new PIDController(p,i,d);
        addRequirements(in);
    }
    @Override
    public void initialize(){
        target = -1450;
        pid.setPID(p,i,d);
    }
    @Override
    public void execute(){
        slides.setPower(pid.calculate(slides.slidePose(),target));
    }
    @Override
    public boolean isFinished(){
        if (slides.slidePose() + target >= -10 && slides.slidePose() + target <=10 ){
            return true;
        } else {
            return false;
        }
    }
}
