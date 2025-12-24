package frc.robot.utils;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.BuildConstants;

public class GitColor {
        static Map<Character, Color[]> colorsChart;
    
    static {colorsChart = new HashMap<Character, Color[]>();
        colorsChart.put('0', new Color[]{Color.kBlue, Color.kBlue});
        colorsChart.put('1', new Color[]{Color.kBlue, Color.kGreen});
        colorsChart.put('2', new Color[]{Color.kBlue, Color.kRed});
        colorsChart.put('3', new Color[]{Color.kBlue, Color.kWhite});
        colorsChart.put('4', new Color[]{Color.kGreen, Color.kBlue});
        colorsChart.put('5', new Color[]{Color.kGreen, Color.kGreen});
        colorsChart.put('6', new Color[]{Color.kGreen, Color.kRed});
        colorsChart.put('7', new Color[]{Color.kGreen, Color.kWhite});
        colorsChart.put('8', new Color[]{Color.kRed, Color.kBlue});
        colorsChart.put('9', new Color[]{Color.kRed, Color.kGreen});
        colorsChart.put('A', new Color[]{Color.kRed, Color.kRed});
        colorsChart.put('B', new Color[]{Color.kRed, Color.kWhite});
        colorsChart.put('C', new Color[]{Color.kWhite, Color.kBlue});
        colorsChart.put('D', new Color[]{Color.kWhite, Color.kGreen});
        colorsChart.put('E', new Color[]{Color.kWhite, Color.kRed});
        colorsChart.put('F', new Color[]{Color.kWhite, Color.kWhite});
    }
       public static LEDPattern colorSegmentFromCharacter(){
        Color[] char1Colors = colorsChart.get(Character.toUpperCase(BuildConstants.GIT_SHA.charAt(36)));
        Color[] char2Colors = colorsChart.get(Character.toUpperCase(BuildConstants.GIT_SHA.charAt(37)));
        Color[] char3Colors = colorsChart.get(Character.toUpperCase(BuildConstants.GIT_SHA.charAt(38)));
        Color[] char4Colors = colorsChart.get(Character.toUpperCase(BuildConstants.GIT_SHA.charAt(39)));
        Map<Double, Color> colorsMap = new HashMap<Double, Color>();
        colorsMap.put(0.0, Color.kBlack);
        colorsMap.put(1/11.0, char1Colors[0]);
        colorsMap.put(2/11.0, char1Colors[1]);
        colorsMap.put(3/11.0, Color.kBlack);
        colorsMap.put(4/11.0, char2Colors[0]);
        colorsMap.put(5/11.0, char2Colors[1]);
        colorsMap.put(6/11.0, Color.kBlack);
        colorsMap.put(7/11.0, char3Colors[0]);
        colorsMap.put(8/11.0, char3Colors[1]);
        colorsMap.put(9/11.0, Color.kBlack);
        colorsMap.put(10/11.0, char4Colors[0]);
        colorsMap.put(0.99, char4Colors[1]);

        return LEDPattern.steps(colorsMap);
    }
}
