package xperamentals.controller;



import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class servoController {
    private Telemetry telemetry;
    private Servo release;       // Servo controlling the claw grip
    private Servo clawPitch;  // Servo controlling the claw pitch

    /** 1800 degree servo*/
    private Servo armPitchR;
    /** 300 degree servo*/
    private Servo armPitchL;
    private Servo armClaw;
    private Servo armRotate;

    // Claw positions
    private static final double CLAW_OPEN = 1.0;
    private static final double CLAW_CLOSED = 0.0;

    // Claw up and down positions
    private static final double PITCH_UP = 1.0;
    private static final double PITCH_DOWN = 0.0;

    //arm pitch positions
    private static final double ARM_WALL = 0.0;
    private static final double ARM_HIGH_CHAMBER = 1.0;

    //arm claw positions
    private static final double ARM_CLAW_CLOSED = 0.0;
    private static final double ARM_CLAW_OPEN = 1.0;

    //arm pitch positions
    private static final double ARM_PITCH = 0.0;

    //universial init positon
    private static final double INIT_POSE = 0;


    public servoController(HardwareMap hardwareMap) {
        release = hardwareMap.get(Servo.class, "release");
        clawPitch = hardwareMap.get(Servo.class, "clawPitch");
        armPitchL = hardwareMap.get(Servo.class,"armPitchL");
        armPitchR = hardwareMap.get(Servo.class,"armPitchR");
        armClaw = hardwareMap.get(Servo.class,"armClaw");
        armRotate = hardwareMap.get(Servo.class,"armRotate");
    }

    /**open claw*/
    public void open() {
        release.setPosition(CLAW_OPEN);

        release.setPosition(INIT_POSE);

    }


    /**close claw*/
    public void close() {
        release.setPosition(CLAW_CLOSED);
    }

    // Pitch the claw up
    public void pitchUp() {
        clawPitch.setPosition(PITCH_UP);
    }

    // Pitch the claw down
    public void pitchDown() {
        clawPitch.setPosition(PITCH_DOWN);
    }

    //Pitch the arm to wall pickup position
    public void armWall() {
        pitchArm(ARM_WALL);
    }

    //pitch the arm to high chamber position
    public void armHighChamber(){
        pitchArm(ARM_HIGH_CHAMBER);
    }

    //open arm claw
    public void armClawOpen() {
        armClaw.setPosition(ARM_CLAW_OPEN);
    }

    //close arm claw
    public void armClawClose() {
        armClaw.setPosition(ARM_CLAW_CLOSED);
    }

    //sets arm pivot to out position
    public void armRotateUp(){
        armRotate.setPosition(ARM_PITCH);
    }

    /**
     * method to rotate the arm a certan amount of degres
     * @param d degres for the arm to rotate
     */

    public void pitchArm(double d) {
        armPitchR.setPosition(d/1800);
        armPitchL.setPosition(d/300);
    }

    /**intalize all servos*/
    public void initServos(){
        release.setPosition(INIT_POSE);
        clawPitch.setPosition(INIT_POSE);
        armPitchR.setPosition(INIT_POSE);
        armPitchL.setPosition(INIT_POSE);
        armClaw.setPosition(INIT_POSE);
        armRotate.setPosition(INIT_POSE);
    }

    /**Telemetry class for all servos*/
    public  void servoTelemetry(){
        telemetry.addData("clawServo: ",release.getPosition());
        telemetry.addData("clawPitch: ",clawPitch.getPosition());
        telemetry.addData("armPitchR: ",armPitchR.getPosition());
        telemetry.addData("armPitchL: ",armPitchR.getPosition());
        telemetry.addData("armClaw: ",armClaw.getPosition());
        telemetry.addData("armPitch: ",armRotate.getPosition());
        telemetry.update();
    }

}