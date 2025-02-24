package xperamentals.command.trigger;

import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;

public class slideExtend extends Trigger {
    private boolean cool = false;
    private TriggerReader triggerReader;

    public slideExtend(GamepadEx gamepadEx){
        triggerReader = new TriggerReader(gamepadEx, GamepadKeys.Trigger.LEFT_TRIGGER);

    }

    @Override
    public boolean get(){
        return triggerReader.isDown();
    }
}
