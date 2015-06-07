package ca.sahiljain.androidcodingchallenge.models;

import android.graphics.Color;

import java.util.LinkedList;
import java.util.List;

public class CommandSet {
    private List<Command> commands;

    public CommandSet() {
        commands = new LinkedList<>();
    }

    public Command get(int i) {
        return commands.get(i);
    }

    public void add(Command command) {
        commands.add(command);
        recalculateActiveCommands();
    }

    public void clear() {
        commands.clear();
    }

    public int size() {
        return commands.size();
    }

    private void recalculateActiveCommands() {
        boolean absoluteFound = false;
        for(int i = commands.size()-1; i >= 0; i--) {
            Command c = commands.get(i);
            if (absoluteFound) {
                c.setActive(false);
            } else if(c.getCommandType() == CommandType.Absolute && c.isActive()) {
                absoluteFound = true;
            }
        }
    }

    public void setActive(int i, boolean b) {
        commands.get(i).setActive(b);
        recalculateActiveCommands();
    }

    public boolean isEmpty() {
        return commands.isEmpty();
    }

    public String getResultColorString() {
        int rOffset = 0, gOffset = 0, bOffset = 0;
        int absoluteR = 127, absoluteG = 127, absoluteB = 127;
        for(Command c : commands) {
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

    public int getResultColor() {
        int rOffset = 0, gOffset = 0, bOffset = 0;
        int absoluteR = 127, absoluteG = 127, absoluteB = 127;
        for(Command c : commands) {
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

    public List<Command> getList() {
        return commands;
    }
}
