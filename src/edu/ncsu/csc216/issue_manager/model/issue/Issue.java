package edu.ncsu.csc216.issue_manager.model.issue;

import java.util.ArrayList;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.issue_manager.model.command.Command.Resolution;

/**
 * The Issue class represents an issue in the issue management system. An issue
 * has various fields such as an ID, summary, owner, confirmation status,
 * resolution, and notes. It can transition through different states defined by
 * constants.
 * 
 * @author Nirvan Reddy Anumandla
 */
public class Issue {
	private int issueId;
	private String summary;
	private String owner;
	private boolean confirmed;
	private IssueType issueType;
	private ArrayList<String> notes = new ArrayList<String>();
	private Resolution resolution;
	public static final String I_ENHANCEMENT = "Enhancement";
	public static final String I_BUG = "Bug";
	public static final String NEW_NAME = "New";
	public static final String WORKING_NAME = "Working";
	public static final String CONFIRMED_NAME = "Confirmed";
	public static final String VERIFYING_NAME = "Verifying";
	public static final String CLOSED_NAME = "Closed";
	private IssueState state;
	private final NewState newState = new NewState();
	private final WorkingState workingState = new WorkingState();
	private final ConfirmedState confirmedState = new ConfirmedState();
	private final VerifyingState verifyingState = new VerifyingState();
	private final ClosedState closedState = new ClosedState();

	/**
	 * The IssueType enumeration represents different types of issues, such as
	 * ENHANCEMENT and BUG.
	 * 
	 */
	public enum IssueType {
		/** Represents type of issue. */
		ENHANCEMENT, BUG
	}

	/**
	 * Constructs an Issue object with values for all fields.
	 *
	 * @param issueId    The ID of the issue.
	 * @param state      The state of the issue.
	 * @param issueType  The type of the issue.
	 * @param summary    The summary of the issue.
	 * @param owner      The owner of the issue.
	 * @param confirmed  The confirmation status of the issue.
	 * @param resolution The resolution of the issue.
	 * @param notes      The notes associated with the issue.
	 */
	public Issue(int issueId, String state, String issueType, String summary, String owner, boolean confirmed,
			String resolution, ArrayList<String> notes) {
		if (issueId < 1 || state == null || issueType == null || summary == null || resolution == null || owner == null
				|| notes == null || "".equals(state) || "".equals(issueType) || "".equals(summary)) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
		if (notes.size() == 0) {
			throw new IllegalArgumentException("Notes list cannot be empty");
		}
		if ((state.equals(WORKING_NAME) || state.equals(VERIFYING_NAME)) && "".equals(owner)) {
			throw new IllegalArgumentException("Invalid owner information for state, cannot be empty here");
		}
		if ((state.equals(NEW_NAME) || state.equals(CONFIRMED_NAME)) && !("".equals(owner))) {
			throw new IllegalArgumentException("Invalid owner information for state, should be empty here");
		}
		if ((state.equals(CLOSED_NAME) || state.equals(VERIFYING_NAME)) && "".equals(resolution)) {
			throw new IllegalArgumentException("Invalid resolution for state, cannot be empty here");
		}
		if (issueType.equals(I_ENHANCEMENT) && state.equals(CONFIRMED_NAME)) {
			throw new IllegalArgumentException("Enhancement cannot be in confirmed state");
		}
		if (issueType.equals(I_BUG) && state.equals(WORKING_NAME) && !confirmed) {
			throw new IllegalArgumentException("Bug cannot be unconfirmed in working state");
		}
		if (state.equals(VERIFYING_NAME) && !(resolution.equals(Command.R_FIXED))) {
			throw new IllegalArgumentException("Resolution must be fixed for verifying state");
		}
		if (issueType.equals(I_ENHANCEMENT) && confirmed) {
			throw new IllegalArgumentException("Enhancement cannot be confirmed");
		}
		setIssueId(issueId);
		setState(state);
		setIssueTypeString(issueType);
		setSummary(summary);
		setOwner(owner);
		setConfirmed(confirmed);
		setResolution(resolution);
		setNotes(notes);
	}

	/**
	 * Creates a issue with the given issueId, issueType, state and type.
	 *
	 * @param issueId   The ID of the issue.
	 * @param issueType The type of the issue.
	 * @param summary   The summary of the issue.
	 * @param note      The note of the issue.
	 */
	public Issue(int issueId, IssueType issueType, String summary, String note) {
		if (issueId < 1 || issueType == null || summary == null || note == null || "".equals(note)
				|| "".equals(summary)) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
		setIssueId(issueId);
		setState(NEW_NAME);
		setIssueType(issueType);
		setSummary(summary);
		this.owner = null;
		this.confirmed = false;
		this.resolution = null;
		addNote(note);
	}

	/**
	 * sets the Issue's issueId.
	 * 
	 * @param issueId the issueId to set.
	 */
	private void setIssueId(int issueId) {
		this.issueId = issueId;
	}

	/**
	 * sets the Issue's state.
	 * 
	 * @param state the state to set.
	 */
	private void setState(String state) {
		if (state.equals(NEW_NAME)) {
			this.state = newState;
		} else if (state.equals(WORKING_NAME)) {
			this.state = workingState;
		} else if (state.equals(CONFIRMED_NAME)) {
			this.state = confirmedState;
		} else if (state.equals(VERIFYING_NAME)) {
			this.state = verifyingState;
		} else if (state.equals(CLOSED_NAME)) {
			this.state = closedState;
		} else {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
	}

	/**
	 * 
	 */
	private void setIssueTypeString(String issueType) {
		if (issueType.equals(I_BUG)) {
			this.issueType = IssueType.BUG;
		} else if (issueType.equals(I_ENHANCEMENT)) {
			this.issueType = IssueType.ENHANCEMENT;
		} else {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
	}

	/**
	 * sets the Issue's issueType.
	 * 
	 * @param issueType the issueType to set.
	 */
	private void setIssueType(IssueType issueType) {
		this.issueType = issueType;
	}

	/**
	 * sets the Issue's summary.
	 * 
	 * @param summary the summary to set.
	 */
	private void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * sets the Issue's owner.
	 * 
	 * @param owner the owner to set.
	 */
	private void setOwner(String owner) {
		if (owner == null || "".equals(owner)) {
			this.owner = null;
		} else {
			this.owner = owner;
		}
	}

	/**
	 * sets the Issue's confirmed.
	 * 
	 * @param confirmed the confirmed to set.
	 */
	private void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	/**
	 * sets the Issue's resolution.
	 * 
	 * @param resolution the resolution to set.
	 */
	private void setResolution(String resolution) {
		if (resolution == "" || resolution == null || resolution.length() == 0) {
			this.resolution = null;
		} else if (resolution.equals(Command.R_FIXED)) {
			this.resolution = Resolution.FIXED;
		} else if (resolution.equals(Command.R_DUPLICATE)) {
			this.resolution = Resolution.DUPLICATE;
		} else if (resolution.equals(Command.R_WONTFIX)) {
			this.resolution = Resolution.WONTFIX;
		} else if (resolution.equals(Command.R_WORKSFORME)) {
			this.resolution = Resolution.WORKSFORME;
		} else {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
	}

	/**
	 * Sets the notes associated with the issue.
	 *
	 * @param notes the notes to be associated with the issue.
	 */
	public void setNotes(ArrayList<String> notes) {
		this.notes = notes;
	}

	/**
	 * Gets the ID of the issue.
	 *
	 * @return The ID of the issue.
	 */
	public int getIssueId() {
		return issueId;
	}

	/**
	 * Gets the name of the current state of the issue.
	 *
	 * @return The name of the current state of the issue.
	 */
	public String getStateName() {
		if (state == newState) {
			return NEW_NAME;
		} else if (state == workingState) {
			return WORKING_NAME;
		} else if (state == confirmedState) {
			return CONFIRMED_NAME;
		} else if (state == verifyingState) {
			return VERIFYING_NAME;
		} else if (state == closedState) {
			return CLOSED_NAME;
		} else {
			throw new IllegalArgumentException("Undeclared state");
		}
	}

	/**
	 * Gets the type of the issue.
	 *
	 * @return The type of the issue.
	 */
	public String getIssueType() {
		if (issueType == IssueType.ENHANCEMENT) {
			return I_ENHANCEMENT;
		} else if (issueType == IssueType.BUG) {
			return I_BUG;
		} else {
			throw new IllegalArgumentException("Undeclared type");

		}
	}

	/**
	 * Gets the resolution of the issue.
	 *
	 * @return The resolution of the issue.
	 */
	public String getResolution() {
		if (resolution == Resolution.DUPLICATE) {
			return Command.R_DUPLICATE;
		} else if (resolution == Resolution.FIXED) {
			return Command.R_FIXED;
		} else if (resolution == Resolution.WONTFIX) {
			return Command.R_WONTFIX;
		} else if (resolution == Resolution.WORKSFORME) {
			return Command.R_WORKSFORME;
		} else if (resolution == null) {
			return null;
		} else {
			throw new IllegalArgumentException("Undeclared resolution");
		}
	}

	/**
	 * Gets the owner of the issue.
	 *
	 * @return The owner of the issue.
	 */
	public String getOwner() {
		if (owner == null || "".equals(owner)) {
			return null;
		} else {
			return owner;
		}
	}

	/**
	 * Gets the summary of the issue.
	 *
	 * @return The summary of the issue.
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Gets the notes associated with the issue.
	 *
	 * @return The notes associated with the issue.
	 */
	public ArrayList<String> getNotes() {
		return notes;
	}

	/**
	 * Checks if the issue is confirmed.
	 *
	 * @return true if the issue is confirmed, false otherwise.
	 */
	public boolean isConfirmed() {
		return confirmed;
	}

	/**
	 * Provides a string representation of the issue.
	 *
	 * @return A string representation of the issue.
	 */
	@Override
	public String toString() {
		if (getResolution() == null) {
			return "*" + issueId + "," + getStateName() + "," + getIssueType() + "," + summary + "," + owner + ","
					+ confirmed + "," + "\n" + getNotesString();
		} else {
			return "*" + issueId + "," + getStateName() + "," + getIssueType() + "," + summary + "," + owner + ","
					+ confirmed + "," + getResolution() + "\n" + getNotesString();
		}
	}

	/**
	 * Converts the notes associated with the issue to a string.
	 *
	 * @return The notes associated with the issue as a string.
	 */
	public String getNotesString() {
		String notesString = "";
		for (int i = 0; i < notes.size(); i++) {
			String s = notes.get(i);
			notesString += "-" + s + "\n";
		}
		return notesString;
	}

	/**
	 * Adds a note to the issue.
	 *
	 * @param note The note to be added to the issue.
	 * @return The added note.
	 */
	private void addNote(String note) {
		notes.add("[" + getStateName() + "] " + note);
	}

	/**
	 * Updates the issue based on the command.
	 *
	 * @param state The updated state
	 */
	public void update(Command command) {
		state.updateState(command);
	}

	/**
	 * The IssueState interface represents the state of an issue in the issue
	 * management system. It provides default methods for updating the state based
	 * on a command and getting the name of the current state.
	 * 
	 * @param state The state that need to be updated
	 */
	public interface IssueState {

		/**
		 * Updates the state based on the provided command.
		 *
		 * @param command The command
		 */
		void updateState(Command command);

		/**
		 * Gets the name of the current state.
		 */
		String getStateName();

	}

	/**
	 * The ConfirmedState class represents the state of a confirmed issue in the
	 * issue management system. It implements the IssueState interface and provides
	 * methods to update the state based on a command and get the name of the
	 * current state.
	 * 
	 */
	public class ConfirmedState implements IssueState {
		/**
		 * Private constructor for the ConfirmedState class.
		 */
		private ConfirmedState() {
		}

		/**
		 * Updates the state based on the provided command.
		 *
		 * @param state The state that need to be updated
		 */
		@Override
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.ASSIGN) {
				setOwner(command.getOwnerId());
				setState(WORKING_NAME);
				addNote(command.getNote());
			} else {
				throw new UnsupportedOperationException("Invalid command for the issue type.");
			}

			if (command.getCommand() == CommandValue.RESOLVE) {
				if (command.getResolution() == Resolution.WONTFIX) {
					setState(CLOSED_NAME);
					setResolution(Command.R_WONTFIX);
					addNote(command.getNote());
				} else {
					throw new UnsupportedOperationException("Invalid transition.");
				}
			}
		}

		/**
		 * Gets the name of the current state.
		 */
		@Override

		public String getStateName() {
			return CONFIRMED_NAME;
		}
	}

	/**
	 * The ClosedStatefvb class represents the state of a closed issue in the issue
	 * management system. It implements the IssueState interface and provides
	 * methods to update the state based on a command and get the name of the
	 * current state.
	 * 
	 */
	public class ClosedState implements IssueState {

		/**
		 * Private constructor for the ClosedState class.
		 */
		private ClosedState() {
		}

		/**
		 * Updates the state based on the provided command.
		 *
		 * @param command The command to update the state.
		 */
		@Override
		public void updateState(Command command) {
			if (command.getCommand() != Command.CommandValue.REOPEN) {
				throw new UnsupportedOperationException("Invalid transition.");
			}
			if (command.getCommand() == CommandValue.REOPEN) {
				if (getOwner() != null && !"".equals(getOwner())) {
					setState(WORKING_NAME);
					setResolution(null);
					addNote(command.getNote());
				} else if (getIssueType() == I_BUG && confirmed) {
					setState(CONFIRMED_NAME);
					setResolution(null);
					addNote(command.getNote());
				} else if (owner == null || "".equals(owner)) {
					setState(NEW_NAME);
					addNote(command.getNote());
					setResolution(null);
				} else {
					throw new UnsupportedOperationException("Invalid transition.");
				}
			}
		}

		/**
		 * Gets the name of the current state.
		 */
		@Override
		public String getStateName() {
			return CLOSED_NAME;
		}
	}

	/**
	 * The VerifyingState class represents the state of a verifying issue in the
	 * issue management system. It implements the IssueState interface and provides
	 * methods to update the state based on a command and get the name of the
	 * current state.
	 * 
	 */
	public class VerifyingState implements IssueState {

		/**
		 * Private constructor for the VerifyingState class.
		 */
		private VerifyingState() {
		}

		/**
		 * Updates the state based on the provided command.
		 *
		 * @param state the state that needs to be updated.
		 */
		@Override
		public void updateState(Command command) {
			Resolution resolution = command.getResolution();
			if (command.getCommand() == CommandValue.VERIFY) {
				if (resolution == Resolution.FIXED) {
					setState(CLOSED_NAME);
					addNote(command.getNote());
				} else if (command.getCommand() == CommandValue.REOPEN) {
					setState(WORKING_NAME);
					addNote(command.getNote());
				} else {
					throw new UnsupportedOperationException("Invalid transition");
				}
			}
		}

		/**
		 * Gets the name of the current state.
		 */
		@Override

		public String getStateName() {
			return VERIFYING_NAME;
		}
	}

	/**
	 * The WorkingState class represents the state of a working issue in the issue
	 * management system. It implements the IssueState interface and provides
	 * methods to update the state based on a command and get the name of the
	 * current state.
	 * 
	 */
	public class WorkingState implements IssueState {

		/**
		 * Private constructor for the WorkingState class.
		 */
		private WorkingState() {
		}

		/**
		 * Updates the state based on the provided command.
		 *
		 * @param state the state that needs to be updated.
		 */
		@Override
		public void updateState(Command command) {
			Resolution resolution = command.getResolution();
			if (command.getCommand() != CommandValue.RESOLVE) {
				throw new UnsupportedOperationException("Invalid information.");
			}
			if (command.getCommand() == CommandValue.RESOLVE) {
				if (resolution == Resolution.FIXED) {
					setState(VERIFYING_NAME);
					setResolution(Command.R_FIXED);
					addNote(command.getNote());
				}
				else if (resolution == Resolution.WORKSFORME && getIssueType() == I_BUG) {
						setState(CLOSED_NAME);
						setResolution("WorksForMe");
						addNote(command.getNote());
					} else if (resolution == Resolution.DUPLICATE) {
						setState(CLOSED_NAME);
						setResolution(Command.R_DUPLICATE);
						addNote(command.getNote());
					} else if (resolution == Resolution.WONTFIX) {
						setState(CLOSED_NAME);
						setResolution(Command.R_WONTFIX);
						addNote(command.getNote());
						System.out.print("here");
					} else {
						throw new UnsupportedOperationException("Invalid transition.");
					}
				}
			}

		/**
		 * Gets the name of the current state.
		 */
		@Override

		public String getStateName() {
			return WORKING_NAME;
		}
	}

	public class NewState implements IssueState {

		/**
		 * Private constructor for the NewState class.
		 */
		private NewState() {
		}

		/**
		 * Updates the state based on the provided command.
		 *
		 * @param state the state that needs to be updated.
		 */
		@Override
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.RESOLVE) {
				if (command.getResolution() == Resolution.FIXED) {
					throw new UnsupportedOperationException("Invalid transition.");
				}
				if (command.getResolution() == Resolution.WORKSFORME && getIssueType() == I_ENHANCEMENT) {
					throw new UnsupportedOperationException("Invalid transition.");
				}
				setState(CLOSED_NAME);
				resolution = command.getResolution();
				addNote(command.getNote());
		}
			if (command.getCommand() == CommandValue.CONFIRM) {
				if (getIssueType() == I_BUG) {
					confirmed = true;
					setState(CONFIRMED_NAME);
					addNote(command.getNote());
				}
			}

			if (command.getCommand() == CommandValue.ASSIGN) {
				if (getIssueType() == I_ENHANCEMENT) {
					setOwner(command.getOwnerId());
					setState(WORKING_NAME);
					addNote(command.getNote());
				}
				} else if (command.getCommand() == CommandValue.CONFIRM){
						if (getIssueType() == I_ENHANCEMENT) {
						setState(CONFIRMED_NAME);
						confirmed = true;
						addNote(command.getNote());
						}
					} else {
						throw new UnsupportedOperationException("Invalid information.");
					}
				}

		/**
		 * Gets the name of the current state.
		 */
		@Override
		public String getStateName() {
			return NEW_NAME;
		}

	}
}