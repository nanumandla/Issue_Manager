package edu.ncsu.csc216.issue_manager.model.manager;

import java.util.ArrayList;
import java.util.List;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.issue.Issue;
import edu.ncsu.csc216.issue_manager.model.issue.Issue.IssueType;

/**
 * The IssueList class represents a list of issues in the issue management
 * system. It provides methods to add, retrieve, execute commands on, and delete
 * issues.
 * 
 * @author Nirvan Reddy Anumandla
 */
public class IssueList {
	/** counter. */
	private int counter;
	/** List to store issues. */
	private List<Issue> issues;

	/**
	 * Constructs an IssueList object.
	 */
	public IssueList() {
		counter = 1;
		issues = new ArrayList<>();
	}

	/**
	 * Adds a new issue to the list.
	 *
	 * @param type    The type of the issue.
	 * @param summary The summary of the issue.
	 * @param note    The note associated with the issue.
	 * @return the added issue
	 */
	public int addIssue(IssueType issueType, String summary, String note) {
		Issue newIssue = new Issue(counter, issueType, summary, note);
		addIssue(newIssue);
		return counter++;
	}

	/**
	 * Adds multiple issues to the list.
	 *
	 * @param issueList A list of issues to be added.
	 */
	public void addIssues(List<Issue> issueList) {
		for (Issue issue : issueList) {
			addIssue(issue);
		}
	}

	/**
	 * Adds a specific issue to the list.
	 * 
	 * @param issue The issue to be added.
	 */
	private void addIssue(Issue issue) {
		if (!issues.contains(issue)) {
			issues.add(issue);
		}
	}

	/**
	 * Gets a list of all issues.
	 *
	 * @return A list of all issues.
	 */
	public List<Issue> getIssues() {
		return new ArrayList<Issue>(issues);
	}

	/**
	 * Gets a list of issues filtered by type.
	 * 
	 * @param type The type of issue
	 * @return A list of issues filtered by type.
	 */
	public List<Issue> getIssuesByType(String type) {
		List<Issue> issueByType = new ArrayList<>();
		for (Issue issue : issues) {
			if (issue.getIssueType().toString().equals(type)) {
				issueByType.add(issue);
			}
		}
		return issueByType;
	}

	/**
	 * Retrieves an issue by its ID.
	 *
	 * @param counter The ID of the issue to be retrieved.
	 * @return The issue with the specified ID.
	 */
	public Issue getIssueById(int issueId) {
		for (Issue issue : issues) {
			if (issue.getIssueId() == issueId) {
				return issue;
			}
		}
		return null;
	}

	/**
	 * Executes a command on a specific issue.
	 *
	 * @param counter The ID of the issue on which the command is to be executed.
	 * @param command The command to be executed.
	 */
	public void executeCommand(int issueId, Command command) {
		Issue issue = getIssueById(issueId);
        if (issue != null) {
            issue.update(command);
        }
	}

	/**
	 * Deletes an issue by its ID.
	 *
	 * @param counter The ID of the issue to be deleted.
	 */
	public void deleteIssueById(int issueId) {
		int index = -1;
	    for (int i = 0; i < issues.size(); i++) {
	        if (issues.get(i).getIssueId() == issueId) {
	            index = i;
	            break;
	        }
	    }
	    
	    if (index != -1) {
	        issues.remove(index);
	    }
	}
}