package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Seconds;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.AddressableLEDBufferView;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.GitColor;

public class Leds extends SubsystemBase{
    private static final int kPort = 0;
    private static final int kLength = 64;
    private final AddressableLED ledStrip;
    private final AddressableLEDBuffer ledBuffer;
    private final AddressableLEDBufferView ShaBuffer;
    private final AddressableLEDBufferView topBuffer;
    private final AddressableLEDBufferView bottomBuffer;


    public LEDPattern topStripPattern;
    public LEDPattern bottomStripPattern;
    public LEDPattern shaPattern;

    public final LEDPattern blueBase;
    public final LEDPattern brownBase;
    public final LEDPattern orangeBase;
    public final LEDPattern redBase;
    public final LEDPattern redBreathe;
    public final LEDPattern redBlinkWithRsl;
    public final LEDPattern rainbowBase;
    public final LEDPattern rainbowBarf;

    public Leds(){
        ledBuffer = new AddressableLEDBuffer(kLength);
        ledStrip = new AddressableLED(kPort);
        ShaBuffer = ledBuffer.createView(52, 63);
        topBuffer = ledBuffer.createView(32, 51);
        bottomBuffer = ledBuffer.createView(0, 31).reversed();
        ledStrip.setLength(kLength);
        ledStrip.start();

        blueBase = LEDPattern.solid(Color.kBlue);
        brownBase = LEDPattern.solid(Color.kBrown);
        orangeBase = LEDPattern.solid(Color.kOrange);
        redBase = LEDPattern.solid(Color.kRed);
        redBreathe = redBase.breathe(Seconds.of(3));
        redBlinkWithRsl = redBase.synchronizedBlink(RobotController::getRSLState);
        rainbowBase = LEDPattern.rainbow(255, 125);
        rainbowBarf = rainbowBase.scrollAtAbsoluteSpeed(MetersPerSecond.of(0.1), Meters.of(1.0/120));
        topStripPattern = redBreathe;
        bottomStripPattern = redBreathe;
        shaPattern = GitColor.colorSegmentFromCharacter();
    }

    @Override
    public void periodic() {
        if(topStripPattern != null && bottomStripPattern != null){
        topStripPattern.applyTo(topBuffer);
        bottomStripPattern.applyTo(bottomBuffer);
        shaPattern.applyTo(ShaBuffer);
        ledStrip.setData(ledBuffer);
        }
    }

}
/*
LED plan:   individual strip length: 32 / 19 inches
            4 strips, 2 each side
            on the outside of wood part of intake
*/