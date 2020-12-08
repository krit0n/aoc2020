package at.kriton.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day03 {

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(args[0]));

		long result = 1;
		for (int i = 1; i < args.length; i += 2) {
			int slopeDown = Integer.parseInt(args[i]);
			int slopeRight = Integer.parseInt(args[i + 1]);
			result *= encounteredTrees(slopeDown, slopeRight, lines);
		}

		System.out.format("result: %d%n", result);
	}

	static int encounteredTrees(int slopeDown, int slopeRight, List<String> lines) {
		int periodLength = lines.get(0).length();
		int currentPosition = 0;
		int encounteredTrees = 0;
		for (int i = 0; i < lines.size(); i += slopeDown) {
			if (lines.get(i).charAt(currentPosition) == '#') {
				encounteredTrees++;
			}
			currentPosition = (currentPosition + slopeRight) % periodLength;
		}
		return encounteredTrees;
	}

}
