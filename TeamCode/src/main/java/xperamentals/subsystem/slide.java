package xperamentals.subsystem;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class slide extends SubsystemBase {
    private DcMotorEx slides;
    private static int target;
    public slide(HardwareMap hMap,String name){
        slides = hMap.get(DcMotorEx.class,"slides");
    }
    public void extend(){
        target = -1450;
    }
    public int slidePose(){
        return slides.getCurrentPosition();
    }
    public void setPower(double power){
        slides.setPower(power);
    }
    public void retract(){
        target = 0;
    }
    @Override
    public void periodic(){

    }
}
