package pedroPathing.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;

public class LConstants {
    static {
        ThreeWheelConstants.forwardTicksToInches = 0.0019;
        ThreeWheelConstants.strafeTicksToInches = 0.0019;
        ThreeWheelConstants.turnTicksToInches = 0.0017;
        ThreeWheelConstants.leftY = 7;
        ThreeWheelConstants.rightY = -7;
        ThreeWheelConstants.strafeX = -5.75;
        ThreeWheelConstants.leftEncoder_HardwareMapName = "FL";
        ThreeWheelConstants.rightEncoder_HardwareMapName = "FR";
        ThreeWheelConstants.strafeEncoder_HardwareMapName = "BR";
        ThreeWheelConstants.leftEncoderDirection = Encoder.FORWARD;
        ThreeWheelConstants.rightEncoderDirection = Encoder.FORWARD;
        ThreeWheelConstants.strafeEncoderDirection = Encoder.REVERSE;
    }
}




