package edu.ncsu.csc216.issue_manager.model.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import edu.ncsu.csc216.issue_manager.model.issue.Issue;

/**
 * tests IssueWriter class
 * 
 * @author Nirvan Reddy Anumandla
 */
public class IssueWriterTest {
	/**
	 * Tests writeActivityRecords()
	 */
	/**
	 * Tests writeIssuesToFile()
	 */
	@Test
	public void testWriteIssuesToFile() {
	    // Create a list of issues for testing
	    ArrayList<Issue> issues = new ArrayList<>();
	    ArrayList<String> notes1 = new ArrayList<>();
	    notes1.add("[New] Note 1");
	    issues.add(new Issue(1, "New", "Enhancement", "Issue 1", "", false, "FIXED", notes1));

	    ArrayList<String> notes2 = new ArrayList<>();
	    notes2.add("[New] Note 1");
	    issues.add(new Issue(2, "Working", "Bug", "Issue 2", "Owner 2", true, "FIXED", notes2));


	    try {
	        // Write issues to the actual file
	        IssueWriter.writeIssuesToFile("test-files/actual_issues.txt", issues);
	    } catch (Exception e) {
	        fail("Unable to save file.");
	    }
	}

}

	
