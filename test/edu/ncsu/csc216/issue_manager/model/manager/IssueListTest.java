package edu.ncsu.csc216.issue_manager.model.manager;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.issue.Issue;
import edu.ncsu.csc216.issue_manager.model.issue.Issue.IssueType;

public class IssueListTest {
    private IssueList issueList;

    @Before
    public void setUp() {
        issueList = new IssueList();
    }

    @Test
    public void testAddIssue() {
        int id = issueList.addIssue(IssueType.BUG, "Test Summary", "Test Note");
        assertEquals(1, id);

        List<Issue> issues = issueList.getIssues();
        assertEquals(1, issues.size());
        assertEquals(1, issues.get(0).getIssueId());
    }

    @Test
    public void testAddIssues() {
        List<Issue> issueListToAdd = new ArrayList<>();
        issueListToAdd.add(new Issue(1, IssueType.ENHANCEMENT, "Summary 1", "Note 1"));
        issueListToAdd.add(new Issue(2, IssueType.BUG, "Summary 2", "Note 2"));

        issueList.addIssues(issueListToAdd);

        List<Issue> issues = issueList.getIssues();
        assertEquals(2, issues.size());
        assertEquals(1, issues.get(0).getIssueId());
        assertEquals(2, issues.get(1).getIssueId());
    }

    @Test
    public void testGetIssuesByType() {
        issueList.addIssue(IssueType.BUG, "Bug 1", "Note 1");
        issueList.addIssue(IssueType.ENHANCEMENT, "Enhancement 1", "Note 2");
        issueList.addIssue(IssueType.BUG, "Bug 2", "Note 3");

        List<Issue> bugIssues = issueList.getIssuesByType("Bug");
        assertEquals(2, bugIssues.size());
        assertEquals(1, bugIssues.get(0).getIssueId());
        assertEquals(3, bugIssues.get(1).getIssueId());

        List<Issue> enhancementIssues = issueList.getIssuesByType("Enhancement");
        assertEquals(1, enhancementIssues.size());
        assertEquals(2, enhancementIssues.get(0).getIssueId());

        List<Issue> invalidTypeIssues = issueList.getIssuesByType("INVALID_TYPE");
        assertEquals(0, invalidTypeIssues.size());
    }

    @Test
    public void testGetIssueById() {
        issueList.addIssue(IssueType.BUG, "Bug 1", "Note 1");
        issueList.addIssue(IssueType.ENHANCEMENT, "Enhancement 1", "Note 2");

        Issue issue = issueList.getIssueById(2);
        assertNotNull(issue);
        assertEquals(2, issue.getIssueId());
        assertEquals("Enhancement", issue.getIssueType());

        Issue nonExistingIssue = issueList.getIssueById(3);
        assertNull(nonExistingIssue);
    }
}