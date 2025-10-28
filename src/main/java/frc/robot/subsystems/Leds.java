package frc.robot.subsystems;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Leds extends SubsystemBase{
    private static final int kPort = 0;
    private static final int kLength = 39;
    private final AddressableLED ledStrip;
    private final AddressableLEDBuffer ledBuffer;
    private LEDPattern ledStripPattern;

    public final LEDPattern redBase = LEDPattern.solid(Color.kRed);
    
    public Leds(){
        ledBuffer = new AddressableLEDBuffer(kLength);
        ledStrip = new AddressableLED(kPort);
        ledStrip.setLength(kLength);
        ledStripPattern = redBase;
        ledStrip.start();
    }

    @Override
    public void periodic() {
        ledStripPattern.applyTo(ledBuffer);
        ledStrip.setData(ledBuffer);
    }
}
