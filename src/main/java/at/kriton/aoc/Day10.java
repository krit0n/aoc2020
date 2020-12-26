package at.kriton.aoc;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class Day10 {
	static final Path INPUT = AOCUtils.PUZZLE_INPUTS.resolve("input10.txt");

	public static void main(String[] args) throws IOException {
		List<Integer> jolts;
		try (Stream<String> lines = Files.lines(INPUT)) {
			jolts = lines.map(Integer::valueOf).sorted().collect(toList());
		}
		int oneJoltDifferences = 0;
		int threeJoltDifferences = 0;

		int lastJolt = 0;
		for (int jolt : jolts) {
			int difference = jolt - lastJolt;
			if (difference == 1) {
				oneJoltDifferences++;
			} else if (difference == 3) {
				threeJoltDifferences++;
			}
			lastJolt = jolt;
		}
		threeJoltDifferences++;
		System.out.format("part01: oneJoltDiff*threeJoltDiff: %d*%d=%d%n", oneJoltDifferences, threeJoltDifferences,
				oneJoltDifferences * threeJoltDifferences);

		List<Integer> jPart2 = new ArrayList<>(jolts.size() + 1);
		jPart2.add(0);
		jPart2.addAll(jolts);
		System.out.format("part02: %d%n", arrangements(jPart2));
	}

	static Map<List<Integer>, Long> arrangementCache = new HashMap<>();

	static long arrangements(List<Integer> rest) {
		if (rest.size() == 1) {
			return 1;
		}
		Long result = arrangementCache.get(rest);
		if (result != null) {
			return result;
		} else {
			int start = rest.get(0);
			long sum = 0;
			for (int i = 1; i < rest.size() && rest.get(i) - start <= 3; i++) {
				sum += arrangements(rest.subList(i, rest.size()));
			}
			arrangementCache.put(rest, sum);
			return sum;
		}
	}

}
