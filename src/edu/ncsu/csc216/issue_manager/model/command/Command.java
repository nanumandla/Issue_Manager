package edu.ncsu.csc216.issue_manager.model.command;

/**
 * The Command class represents a command with a specific value, owner ID, resolution, and note.
 * 
 * @author Nirvan Reddy Anumandla
 */
public class Command {
    /** A constant string for the "Fixed" resolution. */
    public static final String R_FIXED = "FIXED";
    /** A constant string for the "Duplicate" resolution. */
    public static final String R_DUPLICATE = "DUPLICATE";
    /** A constant string for the "WontFix" resolution. */
    public static final String R_WONTFIX = "WONTFIX";
    /** A constant string for the "WorksForMe" resolution. */
    public static final String R_WORKSFORME = "WorksForMe";

    /** Owner ID. */
    private String ownerId;
    /** Note associated with the command. */
    private String note;
    /**Resolution type. */
    private Resolution resolution;
    /**Command value. */
    private CommandValue command;

    /**
     * Constructs a Command object with specified values.
     * 
     * @param c      The command value.
     * @param ownerId The owner's ID.
     * @param r      The resolution type.
     * @param note   A note associated with the command.
     * @throws IllegalArgumentException if the parameters are invalid
     */
    public Command(CommandValue c, String ownerId, Resolution r, String note) {
    	if (c == null) { 
            throw new IllegalArgumentException("Invalid information.");
        }
        if (c == CommandValue.ASSIGN && (ownerId == null || ownerId.isEmpty())) {
            throw new IllegalArgumentException("Invalid information.");
        }
        if (c == CommandValue.RESOLVE && r == null) {
            throw new IllegalArgumentException("Invalid information.");
        }
        if (note == null || note.isEmpty()) {
            throw new IllegalArgumentException("Invalid information.");
        }
        
    	this.command = c;
    	this.ownerId = ownerId;
    	this.resolution = r;
    	this.note = note;
    }

    /**
     * Gets the command value.
     * 
     * @return The command value.
     */
    public CommandValue getCommand() {
        return command;
    }

    /**
     * Gets the owner's ID.
     * 
     * @return The owner's ID.
     */
    public String getOwnerId() {
        return ownerId;
    }

    /**
     * Gets the resolution type.
     * 
     * @return The resolution type.
     */
    public Resolution getResolution() {
        return resolution;
    }

    /**
     * Gets the note associated with the command.
     * 
     * @return The note associated with the command.
     */
    public String getNote() {
        return note;
    }
    
    public enum CommandValue {
        /** Represents the ASSIGN command value. */
        ASSIGN,
        /** Represents the CONFIRM command value. */
        CONFIRM,
        /** Represents the RESOLVE command value. */
        RESOLVE,
        /** Represents the VERIFY command value. */
        VERIFY,
        /** Represents the REOPEN command value. */
        REOPEN;
    }
    
    public enum Resolution {
        /** Represents the FIXED resolution. */
        FIXED,
        /** Represents the DUPLICATE resolution. */
        DUPLICATE,
        /** Represents the WONTFIX resolution. */
        WONTFIX,
        /** Represents the WORKFORME resolution. */
        WORKSFORME;
    }
}