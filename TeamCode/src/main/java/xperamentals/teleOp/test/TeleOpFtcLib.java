package xperamentals.teleOp.test;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.ScheduleCommand;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import xperamentals.command.commands.*;
import xperamentals.command.comandGroups.SequentialCommandGroup.*;
import xperamentals.command.commands.extendSlides;
import xperamentals.command.commands.retractSlides;
import xperamentals.command.trigger.slideExtend;
import xperamentals.subsystem.*;
@TeleOp(name = "CommandOpMode")
public class TeleOpFtcLib extends CommandOpMode {
    private GamepadEx toolOp;;
    private arm arm;
    private claw claw;
    private slide slide;
    private intake intake;
    private static int mode = 0;
    private slideExtend extend;

private TriggerReader triggerReaderL;
    @Override
    public void initialize() {
        toolOp = new GamepadEx(gamepad2);
        arm = new arm(hardwareMap,"cliper");
        intake = new intake(hardwareMap,"picuper");
        slide = new slide(hardwareMap,"extender");
        claw = new claw(hardwareMap,"clawer");
        extend = new slideExtend(toolOp);
        triggerReaderL = new TriggerReader(toolOp, GamepadKeys.Trigger.LEFT_TRIGGER);
        if (mode == 0) {
            /**extend slides*/

            /**retract slides*/
            toolOp.getGamepadButton(GamepadKeys.Button.DPAD_DOWN)
                    .whenPressed(new retractSlides(slide));
            /**eject sample from intake*/
            toolOp.getGamepadButton(GamepadKeys.Button.A)
                    .whenPressed(new ejectSample(arm,intake));


            Trigger trigger = new Trigger(extend::get);
            trigger.whenActive(new extendSlides(slide));

            toolOp.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                            .whenPressed(new lowerClaw(intake));
            toolOp.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                            .whenInactive(new raseClaw(intake));

            toolOp.getGamepadButton(GamepadKeys.Button.X)
                    .whenPressed(new extendSlides(slide));
        }
            toolOp.getGamepadButton(GamepadKeys.Button.LEFT_BUMPER)
                    .whenPressed(new closeClawAndArmChamber(arm,claw));

            toolOp.getGamepadButton(GamepadKeys.Button.RIGHT_BUMPER)
                    .whenPressed(new armWallAndOpenClaw(arm,claw));
            if(gamepad1.left_stick_button){
               // new Command(String.valueOf(new armWallAndOpenClaw(arm, claw)));
                //schedule(new armWallAndOpenClaw(arm, claw));
                new ScheduleCommand(new extendSlides(slide));
            }

        //MecanumDrive mecanum = new MecanumDrive(FL,FR,BL,BR);
       // mecanum.driveRobotCentric(1,1,1);



        }
    }
