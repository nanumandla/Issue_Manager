package edu.ncsu.csc216.issue_manager.model.manager;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.io.IssueReader;
import edu.ncsu.csc216.issue_manager.model.io.IssueWriter;
import edu.ncsu.csc216.issue_manager.model.issue.Issue;
import edu.ncsu.csc216.issue_manager.model.issue.Issue.IssueType;

/**
 * The IssueManager class manages the overall functionality of the issue management system.
 * It handles user interactions and activities such as managing issues, saving and loading issues from files,
 * creating a new issue list, and retrieving issues in various formats.
 * 
 * @author Nirvan Reddy Anumandla
 */
public class IssueManager {
    /** The single instance of the IssueManager class */
    private static final IssueManager instance = new IssueManager();
    /** The current list of issues */
    private IssueList issueList;

    /**
     * Constructs an instance of the IssueManager class.
     */
    private IssueManager() {
        createNewIssueList();
    }

    /**
     * Gets an instance of the IssueManager class.
     *
     * @return An instance of the IssueManager class.
     */
    public static IssueManager getInstance() {
        return instance;
    }

    /**
     * Saves the current list of issues to a file.
     *
     * @param fileName The name of the file to save issues to.
     */
    public void saveIssuesToFile(String fileName) {
        IssueWriter.writeIssuesToFile(fileName, issueList.getIssues());
    }
    
    /**
     * Loads the current list of issues from a file.
     *
     * @param fileName The name of the file to load the issues from.
     */
    public void loadIssuesFromFile(String fileName) {
            ArrayList<Issue> issuesFromFile = IssueReader.readIssuesFromFile(fileName);
            issueList = new IssueList();
            issueList.addIssues(issuesFromFile);
    }

    /**
     * Creates a new issue list.
     */
    public void createNewIssueList() {
        issueList = new IssueList();
    }

    /**
     * Gets the list of issues as a two-dimensional array.
     *
     * @return The list of issues as a two-dimensional array.
     */
    public Object[][] getIssueListAsArray() {
        List<Issue> issues = issueList.getIssues();
        Object[][] issueArray = new Object[issues.size()][4];

        for (int i = 0; i < issues.size(); i++) {
            Issue issue = issues.get(i);
            issueArray[i][0] = issue.getIssueId();
            issueArray[i][1] = issue.getStateName();
            issueArray[i][2] = issue.getIssueType();
            issueArray[i][3] = issue.getSummary();
        }

        return issueArray;
    }

    /**
     * Gets the list of issues filtered by issue type as a two-dimensional array.
     * @param issueType The issue type to filter by.
     * @return The list of issues filtered by issue type as a two-dimensional array.
     */
    public Object[][] getIssueListAsArrayByIssueType(String issueType) {
        if (issueType == null) {
            throw new IllegalArgumentException("Issue type cannot be null.");
        }

        List<Issue> issueByType = issueList.getIssuesByType(issueType);
        Object[][] issueArray = new Object[issueByType.size()][4];

        for (int i = 0; i < issueByType.size(); i++) {
            Issue issue = issueByType.get(i);
            issueArray[i][0] = issue.getIssueId();
            issueArray[i][1] = issue.getStateName().toString();
            issueArray[i][2] = issue.getIssueType().toString();
            issueArray[i][3] = issue.getSummary();
        }

        return issueArray;
    }

    /**
     * Gets an issue by its ID.
     * @param id The ID of the issue
     * @return The issue with the specified ID.
     */
    public Issue getIssueById(int id) {
        return issueList.getIssueById(id);
    }

    /**
     * Executes a command on a specific issue.
     *
     * @param id      The ID of the issue on which the command is to be executed.
     * @param command The command to be executed.
     */
    public void executeCommand(int id, Command command) {
        issueList.executeCommand(id, command);
    }

    /**
     * Deletes an issue by its ID.
     *
     * @param id The ID of the issue to be deleted.
     */
    public void deleteIssueById(int id) {
        issueList.deleteIssueById(id);
    }

    /**
     * Adds a new issue to the list.
     *
     * @param issuetype    The type of the issue.
     * @param summary The summary of the issue.
     * @param note    The note associated with the issue.
     */
    public void addIssueToList(IssueType issuetype, String summary, String note) {
        issueList.addIssue(issuetype, summary, note);
    }
}