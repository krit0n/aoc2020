package at.kriton.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day13 {
	static final Path INPUT = AOCUtils.PUZZLE_INPUTS.resolve("input13.txt");

	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(INPUT);

		int departTime = Integer.parseInt(lines.get(0));
		Map<Integer, Integer> busIdToOffset = busIdToOffset(lines.get(1).split(","));

		long minWaitTime = Integer.MAX_VALUE;
		long minWaitTimeId = 0;
		for (long id : busIdToOffset.keySet()) {
			long waitTime = (-departTime % id) + id;
			if (waitTime < minWaitTime) {
				minWaitTime = waitTime;
				minWaitTimeId = id;
			}
		}
		System.out.format("part01: waitTime * id = %d*%d=%d%n", minWaitTime, minWaitTimeId,
				minWaitTime * minWaitTimeId);
		System.out.format("part02: %d%n", part2(busIdToOffset));

	}

	private static Map<Integer, Integer> busIdToOffset(String[] busIds) {
		Map<Integer, Integer> offsets = new HashMap<>();
		for (int offset = 0; offset < busIds.length; offset++) {
			if (!"x".equals(busIds[offset])) {
				offsets.put(Integer.parseInt(busIds[offset]), offset);
			}
		}
		return offsets;
	}

	private static long part2(Map<Integer, Integer> busIdToOffset) {
		Map<Integer, Integer> waitTimes = new HashMap<>();
		busIdToOffset.forEach((id, offset) -> waitTimes.put(id, Math.floorMod(id - offset, id)));
		return AOCUtils.chineseRemainder(waitTimes);
	}
}
