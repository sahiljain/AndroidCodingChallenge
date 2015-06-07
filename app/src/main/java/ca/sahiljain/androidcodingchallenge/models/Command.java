package ca.sahiljain.androidcodingchallenge.models;

public class Command {
    private int r, g, b;
    private boolean isActive = true;
    private final CommandType commandType;

    public Command(int r, int g, int b, CommandType type) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.commandType = type;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean b) {
        isActive = b;
    }

    public CommandType getCommandType() {
        return commandType;
    }

    public String toString() {
        if(commandType == CommandType.Absolute) {
            return "r: " + r + " g: " + g + " b: " + b;
        } else {
            return "dr: " + r + " dg: " + g + " db: " + b;
        }
    }
}
