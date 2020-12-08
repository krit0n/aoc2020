package at.kriton.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day02 {
	static final Pattern POLICY_PASSWORD_PATTERN = Pattern.compile("(\\d+)-(\\d+) ([a-z]): ([a-z]+)");

	static class PasswordPolicy1 {
		final char character;
		final int minOccurence;
		final int maxOccurence;

		PasswordPolicy1(char character, int minOccurence, int maxOccurence) {
			this.character = character;
			this.minOccurence = minOccurence;
			this.maxOccurence = maxOccurence;
		}

		boolean validate(String password) {
			long occurence = password.chars().filter(c -> c == character).count();
			return minOccurence <= occurence && occurence <= maxOccurence;
		}
	}

	static class PasswordPolicy2 {
		final char character;
		final int pos1;
		final int pos2;

		PasswordPolicy2(char character, int pos1, int pos2) {
			this.character = character;
			this.pos1 = pos1;
			this.pos2 = pos2;
		}

		boolean validate(String password) {
			return password.charAt(pos1 - 1) == character ^ password.charAt(pos2 - 1) == character;
		}
	}

	static boolean isValidPasswordPolicy1(String policyAndPassword) {
		Matcher matcher = POLICY_PASSWORD_PATTERN.matcher(policyAndPassword);
		if (!matcher.matches()) {
			return false;
		}
		int minOccurence = Integer.parseInt(matcher.group(1));
		int maxOccurence = Integer.parseInt(matcher.group(2));
		char character = matcher.group(3).charAt(0);
		String password = matcher.group(4);
		return new PasswordPolicy1(character, minOccurence, maxOccurence).validate(password);
	}

	static boolean isValidPasswordPolicy2(String policyAndPassword) {
		Matcher matcher = POLICY_PASSWORD_PATTERN.matcher(policyAndPassword);
		if (!matcher.matches()) {
			return false;
		}
		int minOccurence = Integer.parseInt(matcher.group(1));
		int maxOccurence = Integer.parseInt(matcher.group(2));
		char character = matcher.group(3).charAt(0);
		String password = matcher.group(4);
		return new PasswordPolicy2(character, minOccurence, maxOccurence).validate(password);
	}

	public static void main(String[] args) throws IOException {
		Path path = Paths.get(args[0]);

		List<String> lines = Files.readAllLines(path);
		long validPasswordPolicy1 = lines.stream().filter(Day02::isValidPasswordPolicy1).count();
		long validPasswordPolicy2 = lines.stream().filter(Day02::isValidPasswordPolicy2).count();

		System.out.format("part01: %d%n", validPasswordPolicy1);
		System.out.format("part02: %d", validPasswordPolicy2);
	}
}
