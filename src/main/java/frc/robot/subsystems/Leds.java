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

    }

    public LEDPattern colorSegmentFromCharacter(String last4OfSha){

        Color[] char1Colors = getColorsFromCharacter(last4OfSha.charAt(0));
        Color[] char2Colors = getColorsFromCharacter(last4OfSha.charAt(1));
        Color[] char3Colors = getColorsFromCharacter(last4OfSha.charAt(2));
        Color[] char4Colors = getColorsFromCharacter(last4OfSha.charAt(3));
        Map<Double, Color> colorsMap = new HashMap<Double, Color>();
        colorsMap.put(0.0, char1Colors[0]);
        colorsMap.put(0.083, char1Colors[1]);
        colorsMap.put(0.166, Color.kBlack);
        colorsMap.put(.25, char2Colors[0]);
        colorsMap.put(0.333, char2Colors[1]);
        colorsMap.put(0.416, Color.kBlack);
        colorsMap.put(.5,char3Colors[0]);
        colorsMap.put(0.583, char3Colors[1]);
        colorsMap.put(0.666, Color.kBlack);
        colorsMap.put(0.75,char4Colors[0]);
        colorsMap.put(0.833, char4Colors[1]);
        colorsMap.put(0.916, Color.kBlack);



        // return LEDPattern.steps(Map.of(0, char1Colors[0], 0.083, char1Colors[1], 0.166, Color.kBlack,
        // .25, char2Colors[0], 0.333, char2Colors[1], 0.416, Color.kBlack,
        // .5,char3Colors[0], 0.583, char3Colors[1], 0.666, Color.kBlack,
        // 0.75,char4Colors[0], 0.833, char4Colors[1], 0.916, Color.kBlack));

        return LEDPattern.steps(colorsMap);
    }

    public Color[] getColorsFromCharacter(char character){
        Color[] colorsForCharacter = {, Color.kBlue};
        return colorsForCharacter;
    }

    @Override
    public void periodic() {
        if(topStripPattern != null && bottomStripPattern != null){
        topStripPattern.applyTo(topBuffer);
        bottomStripPattern.applyTo(bottomBuffer);
        ledStrip.setData(ledBuffer);
        }
    }

}
/*
LED plan:   individual strip length: 32 / 19 inches
            4 strips, 2 each side
            on the outside of wood part of intake
W = White
R = Red
G = Green
Bl = Blue
Br = Brown
P = Pink

0: W W
1: W R
2: W G
3: W Bl
4: W Br
5: W P
6: R R
7: R G
8: R Bl
9: R Br
A: R P
B: G G
C: G Bl
D: G Br
E: G P
F: Bl Bl
*/