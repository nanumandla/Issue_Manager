package edu.ncsu.csc216.issue_manager.model.io;
import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import edu.ncsu.csc216.issue_manager.model.issue.Issue;

public class IssueReaderTest {

    @Test
    void testReadIssuesFromFile() {
            ArrayList<Issue> issues = IssueReader.readIssuesFromFile("test-files/issue1.txt");
            assertEquals(5, issues.size());

            // Check the details of the first issue
            Issue issue1 = issues.get(0);
            assertEquals(1, issue1.getIssueId());
            assertEquals("New", issue1.getStateName());
            assertEquals("Enhancement", issue1.getIssueType());
            assertEquals("Issue description", issue1.getSummary());
            assertEquals("", issue1.getOwner());
            assertFalse(issue1.isConfirmed());
            assertNull(issue1.getResolution());
            ArrayList<String> notes1 = new ArrayList<>();
            notes1.add("[New] Note 1");
            assertEquals(notes1, issue1.getNotes());
            
         // Check the details of the second issue
            Issue issue2 = issues.get(1);
            assertEquals(3, issue2.getIssueId());
            assertEquals("Confirmed", issue2.getStateName());
            assertEquals("Bug", issue2.getIssueType());
            assertEquals("Issue description", issue2.getSummary());
            assertEquals("", issue2.getOwner());
            assertTrue(issue2.isConfirmed());
            assertNull(issue2.getResolution());
            ArrayList<String> notes2 = new ArrayList<>();
            notes1.add("[New] Note 1");
            notes1.add("[Confirmed] Note 2");
            assertEquals(notes2, issue2.getNotes());

            // Check the details of the third issue
            Issue issue3 = issues.get(2);
            assertEquals(7, issue3.getIssueId());
            assertEquals("Working", issue3.getStateName());
            assertEquals("Bug", issue3.getIssueType());
            assertEquals("Issue description", issue3.getSummary());
            assertEquals("owner", issue3.getOwner());
            assertTrue(issue3.isConfirmed());
            assertNull(issue3.getResolution());
            ArrayList<String> notes3 = new ArrayList<>();
            notes3.add("[New] Note 1");
            notes3.add("[Confirmed] Note 2");
            notes3.add("[Working] Note 3");
            assertEquals(notes3, issue3.getNotes());

            // Check the details of the fourth issue
            Issue issue4 = issues.get(3);
            assertEquals(14, issue4.getIssueId());
            assertEquals("Verifying", issue4.getStateName());
            assertEquals("Enhancement", issue4.getIssueType());
            assertEquals("Issue description", issue4.getSummary());
            assertEquals("owner", issue4.getOwner());
            assertFalse(issue4.isConfirmed());
            assertEquals("Fixed", issue4.getResolution());
            ArrayList<String> notes4 = new ArrayList<>();
            notes4.add("[New] Note 1");
            notes4.add("[Working] Note 2");
            notes4.add("[Verifying] Note 3");
            assertEquals(notes4, issue4.getNotes());

            // Check the details of the fifth issue
            Issue issue5 = issues.get(4);
            assertEquals(15, issue5.getIssueId());
            assertEquals("Closed", issue5.getStateName());
            assertEquals("Enhancement", issue5.getIssueType());
            assertEquals("Issue description", issue5.getSummary());
            assertEquals("owner", issue5.getOwner());
            assertFalse(issue5.isConfirmed());
            assertEquals("WontFix", issue5.getResolution());
            ArrayList<String> notes5 = new ArrayList<>();
            notes5.add("[New] Note 1");
            notes5.add("[Working] Note 2");
            notes5.add("[Verifying] Note 3");
            notes5.add("[Working] Note 4");
            notes5.add("[Closed] Note 6");
            assertEquals(notes5, issue5.getNotes());
    }

//    @Test
//    void testReadIssuesFromFileInvalidIssueData() {
//        assertThrows(IllegalArgumentException.class, () -> {
//            IssueReader.readIssuesFromFile("test-files/issue1.txt");
//        });
//    }
}
