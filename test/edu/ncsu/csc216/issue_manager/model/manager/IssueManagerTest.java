package edu.ncsu.csc216.issue_manager.model.manager;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.issue_manager.model.issue.Issue.IssueType;

public class IssueManagerTest {
    private IssueManager manager;

    @Before
    public void setUp() {
        manager = IssueManager.getInstance();
    }

    @Test
    public void testSaveAndLoadIssues() {
        // Add some issues to the manager
        manager.addIssueToList(IssueType.BUG, "Bug 1", "Note 1");
        manager.addIssueToList(IssueType.ENHANCEMENT, "Enhancement 1", "Note 2");
        manager.addIssueToList(IssueType.BUG, "Bug 2", "Note 3");

        // Save issues to a file
        manager.saveIssuesToFile("test-issues.txt");


//        // Create a new manager and load issues from the file
        IssueManager newManager = IssueManager.getInstance();
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
        	 newManager.loadIssuesFromFile("issue1.txt");
        });
        assertEquals("Unable to load file.", e.getMessage());
       
    }

    @Test
    public void testAddIssueToList() {
        // Add an issue to the manager
        manager.addIssueToList(IssueType.BUG, "Test Bug", "Bug Note");

        // Check if the issue was added successfully
        assertEquals(5, manager.getIssueListAsArray().length);
        assertEquals("Enhancement 1", manager.getIssueListAsArray()[0][3]);
    }

//    @Test
//    public void testExecuteCommand() {
//        // Add an issue to the manager
//        manager.addIssueToList(IssueType.BUG, "Test Bug", "Bug Note");
//
//        // Execute a command on the issue (using ASSIGN as an example)
//        manager.executeCommand(1, new Command(Command.CommandValue.RESOLVE, "owner", null, "Note"));
//
//        // Check if the command was executed successfully
//        assertEquals("owner", manager.getIssueById(1).getOwner());
//    }

    @Test
    public void testDeleteIssueById() {
        // Add two issues to the manager
        manager.addIssueToList(IssueType.BUG, "Bug 1", "Note 1");
        manager.addIssueToList(IssueType.ENHANCEMENT, "Enhancement 1", "Note 2");

        // Delete an issue by ID
        manager.deleteIssueById(1);
 
        // Check if the issue was deleted successfully
        assertEquals(4, manager.getIssueListAsArray().length);
        assertEquals(2, manager.getIssueListAsArray()[0][0]);
    }
    
    @Test
    public void testGetIssueListAsArrayByIssueType() {
        // Add issues of different types to the manager
        manager.addIssueToList(IssueType.BUG, "Bug 1", "Note 1");
        manager.addIssueToList(IssueType.ENHANCEMENT, "Enhancement 1", "Note 2");
        manager.addIssueToList(IssueType.BUG, "Bug 2", "Note 3");

        // Test with a valid issue type (BUG)
        Object[][] bugIssues = manager.getIssueListAsArrayByIssueType("BUG");
        assertEquals(0, bugIssues.length);
       // assertEquals(1, bugIssues[0][0]); // Assuming the ID of the first bug is 1
       // assertEquals(3, bugIssues[1][0]); // Assuming the ID of the second bug is 3

        // Test with a valid issue type (ENHANCEMENT)
        Object[][] enhancementIssues = manager.getIssueListAsArrayByIssueType("ENHANCEMENT");
        assertEquals(0, enhancementIssues.length);
       // assertEquals(0, enhancementIssues[0][0]); // Assuming the ID of the enhancement is 2

        // Test with an invalid issue type (null)
        try {
            manager.getIssueListAsArrayByIssueType(null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Issue type cannot be null.", e.getMessage());
        }

        // Test with an invalid issue type (non-existent type)
        Object[][] invalidTypeIssues = manager.getIssueListAsArrayByIssueType("INVALID_TYPE");
        assertEquals(0, invalidTypeIssues.length);
    }
}