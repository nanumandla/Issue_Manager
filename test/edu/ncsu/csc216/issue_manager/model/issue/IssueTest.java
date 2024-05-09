package edu.ncsu.csc216.issue_manager.model.issue;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.issue_manager.model.command.Command.Resolution;
import edu.ncsu.csc216.issue_manager.model.issue.Issue;
import edu.ncsu.csc216.issue_manager.model.issue.Issue.IssueType;
import edu.ncsu.csc216.issue_manager.model.issue.Issue.WorkingState;

class IssueTest {

	@Test
	public void testSmallConstructor() {
		// Testing small constructor
		try {
			Issue issue = new Issue(1, Issue.IssueType.BUG, "Test Summary", "Test Note");

			assertEquals(1, issue.getIssueId());
			assertEquals("New", issue.getStateName());
			assertEquals("Bug", issue.getIssueType());
			assertEquals("Test Summary", issue.getSummary());
			assertNull(issue.getOwner());
			assertFalse(issue.isConfirmed());
			assertNull(issue.getResolution());

			ArrayList<String> notes = new ArrayList<String>();
			notes.add("[New] Test Note");
			assertEquals(notes, issue.getNotes());

		} catch (IllegalArgumentException e) {
			fail("Issue cannot be created.");
		}
	}

	@Test
	public void testLongConstructor() {
		// Testing long constructor
		ArrayList<String> notes = new ArrayList<>();
		notes.add("Test Note");

		Issue issue = new Issue(1, "New", "Bug", "Summary", "", false, "FIXED", notes);

		assertEquals(1, issue.getIssueId());
		assertEquals("New", issue.getStateName());
		assertEquals("Bug", issue.getIssueType());
		assertEquals("Summary", issue.getSummary());
		assertEquals(null, issue.getOwner());
		assertFalse(issue.isConfirmed());
		assertEquals("FIXED", issue.getResolution());
		assertEquals(notes, issue.getNotes());
	}

	@Test
	void testInvalidIssueConstructor() {
		ArrayList<String> notes = new ArrayList<>();
		notes.add("Test Note");
		// issueId is less than 1
		assertThrows(IllegalArgumentException.class, () -> {
			new Issue(-1, "New", "Enhancement", "Summary", "Owner", false, "DUPLICATE", notes);
		});

		// state is null
		assertThrows(IllegalArgumentException.class, () -> {
			new Issue(1, null, "Enhancement", "Summary", "Owner", false, "DUPLICATE", notes);
		});

		assertThrows(IllegalArgumentException.class, () -> {
			new Issue(1, "New", null, "Summary", "Owner", false, "DUPLICATE", notes);
		});

		assertThrows(IllegalArgumentException.class, () -> {
			new Issue(1, "New", "Bug", null, "Owner", false, "DUPLICATE", notes);
		});

		// Having an owner when state is "New"
		assertThrows(IllegalArgumentException.class, () -> {
			new Issue(1, "New", "Enhancement", "Summary", "Owner", false, "DUPLICATE", notes);
		});
	}

	// Add more test methods to cover other functionalities of the Issue class

	@Test
	public void testGetIssueId() {
		ArrayList<String> notes = new ArrayList<>();
		notes.add("Test Note");
		Issue issue = new Issue(1, "Verifying", "Bug", "Summary", "Owner", false, Resolution.FIXED.toString(), notes);
		assertEquals(1, issue.getIssueId());
	}

	@Test
	public void testGetStateName() {
		ArrayList<String> notes = new ArrayList<>();
		notes.add("Test Note");
		Issue issue = new Issue(1, "New", "Bug", "Summary", "", false, Resolution.FIXED.toString(), notes);
		assertEquals("New", issue.getStateName());
	}

	@Test
	public void testGetIssueType() {
		ArrayList<String> notes = new ArrayList<>();
		notes.add("Test Note");
		Issue issue = new Issue(1, "New", "Bug", "Summary", "", false, Resolution.FIXED.toString(), notes);
		assertEquals("Bug", issue.getIssueType());
	}

	@Test
	public void testGetResolution() {
		ArrayList<String> notes = new ArrayList<>();
		notes.add("Test Note");
		Issue issue = new Issue(1, "New", "Bug", "Summary", "", false, Resolution.FIXED.toString(), notes);
		assertEquals(Resolution.FIXED.toString(), issue.getResolution());
	}

	@Test
	public void testGetOwner() {
		ArrayList<String> notes = new ArrayList<>();
		notes.add("Test Note");
		Issue issue = new Issue(1, "New", "Bug", "Summary", "", false, Resolution.FIXED.toString(), notes);
		assertEquals(null, issue.getOwner());
	}

	@Test
	public void testGetSummary() {
		ArrayList<String> notes = new ArrayList<>();
		notes.add("Test Note");
		Issue issue = new Issue(1, "New", "Bug", "Summary", "", false, Resolution.FIXED.toString(), notes);
		assertEquals("Summary", issue.getSummary());
	}

	@Test
	public void testGetNotes() {
		ArrayList<String> notes = new ArrayList<>();
		notes.add("Note 1");
		notes.add("Note 2");
		Issue issue = new Issue(1, "New", "Bug", "Summary", "", false, Resolution.FIXED.toString(), notes);
		assertEquals(notes, issue.getNotes());
	}

	@Test
	public void testIsConfirmed() {
		ArrayList<String> notes = new ArrayList<>();
		notes.add("Note 1");
		Issue issue = new Issue(1, "New", "Bug", "Summary", "", false, Resolution.FIXED.toString(), notes);
		assertFalse(issue.isConfirmed());
	}

	@Test
	public void testUpdateState() {
		// Checks that the new Issue is a New State
		Issue issueNew = new Issue(1, IssueType.ENHANCEMENT, "Summary", "This is a note");
		assertEquals("New", issueNew.getStateName());

		// Transitions to Working state when issue is assigned
		Command test2 = new Command(CommandValue.ASSIGN, "Owner", null, "Notes");
		issueNew.update(test2);
		assertEquals("Working", issueNew.getStateName());
		assertEquals("Owner", issueNew.getOwner());

		// Trasitions to Verifying
		Command test3 = new Command(CommandValue.RESOLVE, "Owner", Resolution.FIXED, "Notes");
		issueNew.update(test3);
		assertEquals("Verifying", issueNew.getStateName());
		assertEquals("FIXED", issueNew.getResolution());
		assertEquals("Owner", issueNew.getOwner());

		// Transitions to Closed
		Command test4 = new Command(CommandValue.VERIFY, null, Resolution.FIXED, "Notes");
		issueNew.update(test4);
		assertEquals("Closed", issueNew.getStateName());
		assertEquals("FIXED", issueNew.getResolution());
		assertEquals("Owner", issueNew.getOwner());

	}

	@Test
	public void testWorkingUpdateState() {
		Issue issueNew = new Issue(1, IssueType.BUG, "Summary", "This is a note");
		Command test1 = new Command(CommandValue.RESOLVE, "Owner", Resolution.WORKSFORME, "Notes");
		issueNew.update(test1);
		assertEquals("Closed", issueNew.getStateName());
		assertEquals("WORKSFORME", issueNew.getResolution());
		
		Command test2 = new Command(CommandValue.RESOLVE, "Owner", Resolution.DUPLICATE, "Notes");
		issueNew.update(test2);
		assertEquals("Closed", issueNew.getStateName());
		assertEquals("DUPLICATE", issueNew.getResolution());
		
		Command test3 = new Command(CommandValue.RESOLVE, "Owner", Resolution.WONTFIX, "Notes");
		issueNew.update(test3);
		assertEquals("Closed", issueNew.getStateName());
		assertEquals("WONTFIX", issueNew.getResolution());
    }
	
	@Test
	public void testNewUpdateState() {
		Issue issueNew = new Issue(1, IssueType.BUG, "Summary", "This is a note");
		Command test1 = new Command(CommandValue.CONFIRM, "Owner", null, "Notes");
		issueNew.update(test1);
		assertEquals("Confirmed", issueNew.getStateName());
		
		//UnsupportedOperationException thrown
		Issue issueNew1 = new Issue(1, IssueType.ENHANCEMENT, "Summary", "This is a note");
		Command error = new Command(CommandValue.RESOLVE, "Owner", Resolution.WORKSFORME, "Notes");
		// Use assertThrows to check for the expected exception
	    UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class, () -> {
	    	issueNew1.update(error);
	    });

	    // Check if the exception message matches the expected message
	    assertEquals("Invalid transition.", exception.getMessage());
	    
	  
	    //UnsupportedOperationException thrown if resolution is fixed
	  		Issue issueNew2 = new Issue(1, IssueType.ENHANCEMENT, "Summary", "This is a note");
	  		Command error2 = new Command(CommandValue.RESOLVE, "Owner", Resolution.FIXED, "Notes");
	  		// Use assertThrows to check for the expected exception
	  	    UnsupportedOperationException exception2 = assertThrows(UnsupportedOperationException.class, () -> {
	  	    	issueNew2.update(error2);
	  	    });

	  	    // Check if the exception message matches the expected message
	  	    assertEquals("Invalid transition.", exception2.getMessage());
	}
	
	@Test
	public void testClosedUpdateState() {
		Issue issueNew = new Issue(1, IssueType.ENHANCEMENT, "Summary", "This is a note");
		Command test1 = new Command(CommandValue.REOPEN, "Owner", Resolution.FIXED, "Notes");
		issueNew.update(test1);
		assertEquals("Working", issueNew.getStateName());
		assertEquals(null, issueNew.getResolution());
		
		Issue issueNew1 = new Issue(2, IssueType.BUG, "Summary", "Note");
		Command test2 = new Command(CommandValue.CONFIRM, "Owner", Resolution.FIXED, "Notes");
		issueNew1.update(test2);
		assertEquals("Confirmed", issueNew1.getStateName());
		assertEquals(null, issueNew1.getResolution());
		}
	
	@Test
	public void testVerifyingUpdateState() {
		Issue issueNew = new Issue(1, IssueType.BUG, "Summary", "Note");
		Command test1 = new Command(CommandValue.VERIFY, "Owner", Resolution.FIXED, "Notes");
		issueNew.update(test1);
		assertEquals("Closed", issueNew.getStateName());
	}
}

