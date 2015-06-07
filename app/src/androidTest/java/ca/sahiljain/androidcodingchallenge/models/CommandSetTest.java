package ca.sahiljain.androidcodingchallenge.models;

import junit.framework.TestCase;

import org.junit.Test;

public class CommandSetTest extends TestCase {

    @Test
    public void testAddRelativeCommandsAreSelected() throws Exception {
        CommandSet set = new CommandSet();
        set.add(new Command(5, 10, 15, CommandType.Relative));
        set.add(new Command(5, 11, 15, CommandType.Relative));
        set.add(new Command(5, 12, 15, CommandType.Relative));
        for (Command c : set.getList()) {
            assertTrue(c.isActive());
        }
    }

    @Test
    public void testAdd3Relative1Absolute3Relative() throws Exception {
        CommandSet set = new CommandSet();
        set.add(new Command(5, 10, 15, CommandType.Relative));
        set.add(new Command(5, 11, 15, CommandType.Relative));
        set.add(new Command(5, 12, 15, CommandType.Relative));
        set.add(new Command(120, 120, 165, CommandType.Absolute));
        set.add(new Command(5, 10, 15, CommandType.Relative));
        set.add(new Command(5, 11, 15, CommandType.Relative));
        set.add(new Command(5, 12, 15, CommandType.Relative));
        assertFalse(set.get(0).isActive());
        assertFalse(set.get(1).isActive());
        assertFalse(set.get(2).isActive());
        assertTrue(set.get(3).isActive());
        assertTrue(set.get(4).isActive());
        assertTrue(set.get(5).isActive());
        assertTrue(set.get(6).isActive());
    }

    @Test
    public void testUnselectRelativeCommand() throws Exception {
        CommandSet set = new CommandSet();
        set.add(new Command(5, 10, 15, CommandType.Relative));
        set.add(new Command(5, 11, 15, CommandType.Relative));
        set.add(new Command(5, 12, 15, CommandType.Relative));
        assertTrue(set.get(0).isActive());
        assertTrue(set.get(1).isActive());
        assertTrue(set.get(2).isActive());
        set.toggleActive(0);
        assertFalse(set.get(0).isActive());
        assertTrue(set.get(1).isActive());
        assertTrue(set.get(2).isActive());
    }

    @Test
    public void testUnselectAbsoluteCommand() throws Exception {
        CommandSet set = new CommandSet();
        set.add(new Command(5, 10, 15, CommandType.Relative));
        set.add(new Command(120, 120, 165, CommandType.Absolute));
        set.add(new Command(5, 10, 15, CommandType.Relative));
        assertFalse(set.get(0).isActive());
        assertTrue(set.get(1).isActive());
        assertTrue(set.get(2).isActive());
        set.toggleActive(1); //turn the absolute command off, it should stay on
        assertFalse(set.get(0).isActive());
        assertTrue(set.get(1).isActive());
        assertTrue(set.get(2).isActive());
    }

    @Test
    public void testSelectAbsoluteCommand() throws Exception {
        CommandSet set = new CommandSet();
        set.add(new Command(120, 120, 165, CommandType.Absolute));
        set.add(new Command(5, 10, 15, CommandType.Relative));
        set.add(new Command(120, 120, 165, CommandType.Absolute));
        set.add(new Command(5, 10, 15, CommandType.Relative));
        assertFalse(set.get(0).isActive());
        assertFalse(set.get(1).isActive());
        assertTrue(set.get(2).isActive());
        assertTrue(set.get(3).isActive());
        set.toggleActive(0); //turn the absolute command on, it should turn off the other absolute
        assertTrue(set.get(0).isActive());
        assertFalse(set.get(1).isActive());
        assertFalse(set.get(2).isActive());
        assertTrue(set.get(3).isActive());
    }

}