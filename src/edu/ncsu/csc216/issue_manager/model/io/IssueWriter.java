package edu.ncsu.csc216.issue_manager.model.io;

import java.io.PrintStream;
import java.util.List;
import java.io.File;

import java.io.IOException;

import edu.ncsu.csc216.issue_manager.model.issue.Issue;

/**
 * The IssueWriter class writes issues to a file.
 * 
 * @author Nirvan Reddy Anumandla
 */
public class IssueWriter {

	/**
	 * Writes a list of issues to a file.
	 *
	 * @param fileName The name of the file to write issues.
	 * @param manager  The list of issues to be written to the file.
	 * @throws IllegalArgumentException If there are any error while writting to a file.
	 */
	public static void writeIssuesToFile(String fileName, List<Issue> manager) {
		try (PrintStream fileWriter = new PrintStream(new File(fileName))) {

			for (int i = 0; i < manager.size(); i++) {
				fileWriter.println(manager.get(i).toString());
			}
			fileWriter.close();
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to save file");
		}
	}
}