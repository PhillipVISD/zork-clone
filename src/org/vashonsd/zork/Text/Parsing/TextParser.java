package org.vashonsd.zork.Text.Parsing;

import java.util.Scanner;

/**
* Takes input from user from the command line.
*/
public class TextParser {
	Scanner reader;

	public TextParser() {
		this.reader = new Scanner(System.in);
	}

	public String read(String prompt) {
		System.out.print(prompt);
		return reader.nextLine();
	}
}
