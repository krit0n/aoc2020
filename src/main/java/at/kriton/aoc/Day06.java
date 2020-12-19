package at.kriton.aoc;

import static java.nio.file.Files.readString;
import static java.util.Collections.emptySet;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Day06 {
	static final Path INPUT = AOCUtils.PUZZLE_INPUTS.resolve("input06.txt");

	public static void main(String[] args) throws IOException {
		List<String> lines = Stream
				.of(readString(INPUT).replaceAll("(?<!\\n)\\n(?!\\n)", " ").split("\\n{2,}"))
				.collect(toList());
		long sumPart1 = lines.stream().mapToLong(line -> line.chars().filter(c -> ' ' != c).distinct().count()).sum();

		long sumPart2 = lines.stream().map(l -> Stream.of(l.split(" ")).map(c -> c.chars().boxed().collect(toList())))
				.map(Day06::intersect).mapToInt(Set::size).sum();

		System.out.format("part01: %d%n", sumPart1);
		System.out.format("part02: %d", sumPart2);
	}

	static <T> Set<T> intersect(Stream<? extends Collection<T>> stream) {
		return intersect(stream.collect(toList()));
	}

	static <T> Set<T> intersect(Collection<? extends Collection<T>> coll) {
		if (coll.isEmpty()) {
			return emptySet();
		}
		return coll.stream().skip(1).collect(() -> new HashSet<>(coll.iterator().next()), Set::retainAll,
				Set::retainAll);
	}

}
