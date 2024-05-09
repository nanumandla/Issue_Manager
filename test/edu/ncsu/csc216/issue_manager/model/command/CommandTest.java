package edu.ncsu.csc216.issue_manager.model.command;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.issue_manager.model.command.Command.Resolution;
import edu.ncsu.csc216.issue_manager.model.command.Command.CommandValue;

class CommandTest {

    @Test
    public void testValidConstructorParameters() {
        try {
            Command command = new Command(CommandValue.ASSIGN, "check owner", Resolution.FIXED, "Note");
            assertEquals(CommandValue.ASSIGN, command.getCommand());
            assertEquals("check owner", command.getOwnerId());
            assertEquals(Resolution.FIXED, command.getResolution());
            assertEquals("Note", command.getNote());
        } catch (IllegalArgumentException e) {
            fail("Unexpected IllegalArgumentException");
        }
    }

    @Test
    public void testInvalidConstructorParameters() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Command(null, "check owner", Resolution.FIXED, "Note"); 
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Command(CommandValue.ASSIGN, null, Resolution.FIXED, "Note");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Command(CommandValue.RESOLVE, "check owner", null, "Note");
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Command(CommandValue.ASSIGN, "check owner", Resolution.FIXED, null);
        });
    }

    @Test
    public void testGetCommand() {
        Command command = new Command(CommandValue.ASSIGN, "check owner", Resolution.FIXED, "Note");
        assertEquals(CommandValue.ASSIGN, command.getCommand()); 
    }

    @Test
    public void testGetOwnerId() {
        Command command = new Command(CommandValue.ASSIGN, "check owner", Resolution.FIXED, "Note");
        assertEquals("check owner", command.getOwnerId());
    }

    @Test
    public void testGetResolution() {
        Command command = new Command(CommandValue.RESOLVE, "check owner", Resolution.FIXED, "Note");
        assertEquals(Resolution.FIXED, command.getResolution());
    }

    @Test
    public void testGetNote() {
        Command command = new Command(CommandValue.ASSIGN, "check owner", Resolution.FIXED, "Note");
        assertEquals("Note", command.getNote());
    }
}