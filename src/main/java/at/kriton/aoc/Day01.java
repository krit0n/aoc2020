package at.kriton.aoc;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

public class Day01 {
	static final int SUM = 2020;
	static final Path INPUT = AOCUtils.PUZZLE_INPUTS.resolve("input01.txt");

	public static void main(String[] args) throws IOException {
		try (Stream<String> lines = Files.lines(INPUT)) {
			List<Integer> numbers = lines.map(Integer::valueOf).collect(toList());

			System.out.format("part01: %d%n", part01(SUM, numbers));
			System.out.format("part02: %d", part02(SUM, numbers));
		}
	}

	static int part01(int sum, Collection<Integer> numbers) {
		for (int s1 : numbers) {
			for (int s2 : numbers) {
				if (s1 + s2 == sum) {
					return s1 * s2;
				}
			}
		}
		return -1;
	}

	static int part02(int sum, Collection<Integer> numbers) {
		for (int s1 : numbers) {
			for (int s2 : numbers) {
				for (int s3 : numbers) {
					if (s1 + s2 + s3 == sum) {
						return s1 * s2 * s3;
					}
				}
			}
		}
		return -1;
	}
}
