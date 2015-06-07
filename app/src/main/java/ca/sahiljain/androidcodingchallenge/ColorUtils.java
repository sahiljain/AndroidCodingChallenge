package ca.sahiljain.androidcodingchallenge;

import android.graphics.Color;

import java.util.ArrayList;

import ca.sahiljain.androidcodingchallenge.models.Command;
import ca.sahiljain.androidcodingchallenge.models.CommandType;

public class ColorUtils {
    public static String getColorString(ArrayList<Command> commandList) {

        int rOffset = 0, gOffset = 0, bOffset = 0;
        int absoluteR = 127, absoluteG = 127, absoluteB = 127;
        for(Command c : commandList) {
            if (c.isActive()) {
                if(c.getCommandType() == CommandType.Absolute) {
                    absoluteR = c.getR();
                    absoluteG = c.getG();
                    absoluteB = c.getB();
                } else {
                    rOffset += c.getR();
                    gOffset += c.getG();
                    bOffset += c.getB();
                }
            }
        }
        absoluteR += rOffset;
        absoluteR%= 255;
        absoluteG += gOffset;
        absoluteG%= 255;
        absoluteB += bOffset;
        absoluteB%= 255;
        return "r: " + absoluteR + " g: " + absoluteG + " b: " + absoluteB;
    }

    public static int getColorInt(ArrayList<Command> commandList) {
        int rOffset = 0, gOffset = 0, bOffset = 0;
        int absoluteR = 127, absoluteG = 127, absoluteB = 127;
        for(Command c : commandList) {
            if (c.isActive()) {
                if(c.getCommandType() == CommandType.Absolute) {
                    absoluteR = c.getR();
                    absoluteG = c.getG();
                    absoluteB = c.getB();
                } else {
                    rOffset += c.getR();
                    gOffset += c.getG();
                    bOffset += c.getB();
                }
            }
        }
        absoluteR += rOffset;
        absoluteR%= 255;
        absoluteG += gOffset;
        absoluteG%= 255;
        absoluteB += bOffset;
        absoluteB%= 255;
        return Color.rgb(absoluteR, absoluteG, absoluteB);
    }

}
