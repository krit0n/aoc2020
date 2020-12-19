package at.kriton.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day03 {
	static final Path INPUT = AOCUtils.PUZZLE_INPUTS.resolve("input03.txt");
	static final int[] SLOPE1 = new int[] { 1, 3 };
	static final int[] SLOPE2 = new int[] { 1, 1, 1, 3, 1, 5, 1, 7, 2, 1 };

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(INPUT);

		System.out.format("result01: %d%n", solve(lines, SLOPE1));
		System.out.format("result02: %d%n", solve(lines, SLOPE2));
	}

	static long solve(List<String> lines, int[] slope) throws IOException {
		long result = 1;
		for (int i = 0; i < slope.length; i += 2) {
			int slopeDown = slope[i];
			int slopeRight = slope[i + 1];
			result *= encounteredTrees(slopeDown, slopeRight, lines);
		}
		return result;
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
