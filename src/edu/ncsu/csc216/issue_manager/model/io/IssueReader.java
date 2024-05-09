package edu.ncsu.csc216.issue_manager.model.io;

import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.FileInputStream;

import edu.ncsu.csc216.issue_manager.model.issue.Issue;

/**
 * The IssueReader class provides methods for reading and processing issues from
 * a file. It includes a method to read issues from a specified file and another
 * method to process a string representation of an issue and create an Issue
 * object with the extracted information.
 *
 * The file containing issues should be formatted with each issue represented as
 * a separate block of text, using '*' as a delimiter between issues.
 *
 * @author Nirvan Reddy Anumandla
 */
public class IssueReader {

	/**
	 * Reads issues from a file and returns them as a Arraylist of Issue. The method
	 * processes each line as an issue, and adds them to an ArrayList of Issue
	 * objects. The file should be formatted with each issue represented as a
	 * separate issues, using '*' as a delimiter between issues.
	 * 
	 * @param fileName The name of the file to read issues from.
	 * @return A Arraylist of issues read from the file.
	 * @throws FileNotFoundException    If the specified file is not found.
	 * @throws IllegalArgumentException If there is an issue with the file format or
	 *                                  if an error occurs during the reading or
	 *                                  processing of the file.
	 */
	public static ArrayList<Issue> readIssuesFromFile(String fileName) {
		ArrayList<Issue> issues = new ArrayList<>();
		String issueString = "";
		try {
			Scanner fileReader = new Scanner(new FileInputStream(fileName));
			fileReader.useDelimiter("\\r?\\n?[*]");
			while (fileReader.hasNext()) {
				issueString = fileReader.next();
				try {
					Issue issue = processIssue(issueString);
					issues.add(issue);
				} catch (Exception e) {
				}
			}
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to load file.");
		}
		return issues;
	}

	/**
	 * Processes a string representing an issue and returns an Issue object.
	 *
	 * This method takes a string representation of an issue, parses it, and creates
	 * an Issue object with the extracted information. The issue information is
	 * expected to be in a comma-separated format with the following fields:
	 *
	 * - Issue ID (int) - State (String) - Issue Type (String) - Summary (String) -
	 * Owner (String) - Confirmed (boolean) - Resolution (String) - Notes
	 * (ArrayList<String)
	 *
	 * @param fileName The string representation of the issue to be processed.
	 * @return An Issue object containing the parsed information.
	 * @throws IllegalArgumentException If there is an issue with the format of the
	 *                                  input string or if an error occurs during
	 *                                  the processing of the issue.
	 */
	private static Issue processIssue(String issueToken) {
		try (Scanner scan = new Scanner(issueToken)) {
			
			scan.useDelimiter(",");
			int issueId = Integer.parseInt(scan.next().trim());
			String state = scan.next().trim();
			String issueType = scan.next().trim();
			String summary = scan.next().trim();
			String owner = scan.next().trim();
			boolean confirmed = Boolean.parseBoolean(scan.next().trim());
			String temp[] = scan.next().split("\n");
			String resolution = temp[0];
			ArrayList<String> notes = new ArrayList<>();
			for (int i = 1; i < temp.length; i++) {
				notes.add(temp[i].trim()); 
			}
			
			Issue newIssue = new Issue(issueId, state, issueType, summary, owner, confirmed, resolution, notes);
			return newIssue;
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException("Unable to process issue.");
		}
	}
		
}
