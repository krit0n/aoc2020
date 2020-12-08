package at.kriton.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day01 {

	public static void main(String[] args) throws IOException {
		String sum = args[0];
		String path = args[1];

		try (Stream<String> lines = Files.lines(Paths.get(path))) {
			List<Integer> numbers = lines.map(Integer::valueOf).collect(Collectors.toList());

			System.out.format("part01: %d%n", part01(Integer.valueOf(sum), numbers));
			System.out.format("part02: %d", part02(Integer.valueOf(sum), numbers));
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
