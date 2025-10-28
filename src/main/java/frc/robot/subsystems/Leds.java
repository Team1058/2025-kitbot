package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Meter;
import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.Seconds;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.*;

public class Leds extends SubsystemBase{
    private static final int kPort = 0;
    private static final int kLength = 39;
    private final AddressableLED ledStrip;
    private final AddressableLEDBuffer ledBuffer;

    public LEDPattern ledStripPattern;

    public final LEDPattern redBase;
    public final LEDPattern redBreathe;
    public final LEDPattern rainbowBase;
    public final LEDPattern rainbowBarf;

    public Leds(){
        ledBuffer = new AddressableLEDBuffer(kLength);
        ledStrip = new AddressableLED(kPort);
        ledStrip.setLength(kLength);
        ledStrip.start();

        redBase = LEDPattern.solid(Color.kRed);
        redBreathe = redBase.breathe(Seconds.of(3));
        rainbowBase = LEDPattern.rainbow(255, 125);
        rainbowBarf = rainbowBase.scrollAtAbsoluteSpeed(MetersPerSecond.of(1), Meter.of(1/120));
        ledStripPattern = redBreathe;
    }

    @Override
    public void periodic() {
        ledStripPattern.applyTo(ledBuffer);
        ledStrip.setData(ledBuffer);
    }

}
