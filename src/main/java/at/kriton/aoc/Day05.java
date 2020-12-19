package at.kriton.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day05 {
	static final Path INPUT = AOCUtils.PUZZLE_INPUTS.resolve("input05.txt");

	static final Pattern SEAT_PATTERN = Pattern.compile("([BF]{7})([RL]{3})");

	public static void main(String[] args) throws IOException {
		try (Stream<String> lines = Files.lines(INPUT)) {
			List<Integer> seatIds = lines.map(Day05::seatId).sorted().collect(Collectors.toList());
			Integer highestSeatId = seatIds.get(seatIds.size() - 1);

			System.out.format("part01: %d%n", highestSeatId);
			System.out.format("part02: %d", findeLuecke(seatIds));
		}
	}

	static int seatId(String str) {
		return Integer.parseInt(str.replace('B', '1').replace('F', '0').replace('R', '1').replace('L', '0'), 2);
	}
	
	static int findeLuecke(List<Integer> seatIds) {
		for (int i = 1; i < seatIds.size(); i++) {
			int last = seatIds.get(i - 1);
			int current = seatIds.get(i);
			
			if (last + 1 != current) {
				return last + 1;
			}
		}
		return -1;
	}

}
