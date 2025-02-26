package xperamentals.command.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import xperamentals.subsystem.*;
public class resetEncoder extends CommandBase {
    private slide slide;
    public resetEncoder(slide slides){
        slide = slides;
        addRequirements(slides);
    }
    @Override
    public void initialize(){
        slide.resetSlideEncoder();
    }
    @Override
    public boolean isFinished(){
        return true;
    }

}
