package at.kriton.aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;

public class Day17 {
	static final Path INPUT = AOCUtils.PUZZLE_INPUTS.resolve("input17.txt");

	interface Coordinate<T extends Coordinate<T>> {
		List<T> neighbors();
	}

	static class Coordinate3 implements Coordinate<Coordinate3> {

		final int X;
		final int Y;
		final int Z;

		Coordinate3(int x, int y, int z) {
			this.X = x;
			this.Y = y;
			this.Z = z;
		}

		@Override
		public List<Coordinate3> neighbors() {
			List<Coordinate3> neighbors = new ArrayList<>(26);
			int[] neighborhoodX = new int[] { X - 1, X, X + 1 };
			int[] neighborhoodY = new int[] { Y - 1, Y, Y + 1 };
			int[] neighborhoodZ = new int[] { Z - 1, Z, Z + 1 };
			for (int x : neighborhoodX) {
				for (int y : neighborhoodY) {
					for (int z : neighborhoodZ) {
						if (x == X && y == Y && z == Z) {
							continue;
						}
						neighbors.add(new Coordinate3(x, y, z));
					}
				}
			}
			return neighbors;
		}

		@Override
		public int hashCode() {
			return 3 * X + 5 * Y + 7 * Z;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof Coordinate3)) {
				return false;
			}
			Coordinate3 other = (Coordinate3) obj;
			return X == other.X && Y == other.Y && Z == other.Z;
		}

		@Override
		public String toString() {
			return String.format("Coordinate3 [X=%s, Y=%s, Z=%s]", X, Y, Z);
		}
	}

	static class Coordinate4 implements Coordinate<Coordinate4> {
		final int W;
		final int X;
		final int Y;
		final int Z;

		Coordinate4(int w, int x, int y, int z) {
			this.W = w;
			this.X = x;
			this.Y = y;
			this.Z = z;
		}

		@Override
		public List<Coordinate4> neighbors() {
			List<Coordinate4> neighbors = new ArrayList<>(26);
			int[] neighborhoodW = new int[] { W - 1, W, W + 1 };
			int[] neighborhoodX = new int[] { X - 1, X, X + 1 };
			int[] neighborhoodY = new int[] { Y - 1, Y, Y + 1 };
			int[] neighborhoodZ = new int[] { Z - 1, Z, Z + 1 };
			for (int w : neighborhoodW) {
				for (int x : neighborhoodX) {
					for (int y : neighborhoodY) {
						for (int z : neighborhoodZ) {
							if (w == W && x == X && y == Y && z == Z) {
								continue;
							}
							neighbors.add(new Coordinate4(w, x, y, z));
						}
					}
				}
			}
			return neighbors;
		}

		@Override
		public int hashCode() {
			return 3 * W + 5 * X + 7 * Y + 11 * Z;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (!(obj instanceof Coordinate4)) {
				return false;
			}
			Coordinate4 other = (Coordinate4) obj;
			return W == other.W && X == other.X && Y == other.Y && Z == other.Z;
		}

		@Override
		public String toString() {
			return String.format("Coordinate4 [W=%s, X=%s, Y=%s, Z=%s]", W, X, Y, Z);
		}
	}

	public static void main(String[] args) throws IOException {
		Set<Coordinate3> activeCells3 = readActiveCells(Files.readAllLines(INPUT), (x, y) -> new Coordinate3(x, y, 0));

		for (int i = 0; i < 6; i++) {
			activeCells3 = nextCycle(activeCells3);
		}

		Set<Coordinate4> activeCells4 = readActiveCells(Files.readAllLines(INPUT),
				(x, y) -> new Coordinate4(0, x, y, 0));
		for (int i = 0; i < 6; i++) {
			activeCells4 = nextCycle(activeCells4);
		}

		System.out.format("part01: %d%n", activeCells3.size());
		System.out.format("part02: %d%n", activeCells4.size());
	}

	static <T extends Coordinate<T>> Set<T> readActiveCells(List<String> lines,
			BiFunction<Integer, Integer, T> toCoord) {
		Set<T> activeCells = new HashSet<>();

		for (int y = 0; y < lines.size(); y++) {
			char[] line = lines.get(y).toCharArray();
			for (int x = 0; x < line.length; x++) {
				if (line[x] == '#') {
					activeCells.add(toCoord.apply(x, y));
				}
			}
		}

		return activeCells;
	}

	static <T extends Coordinate<T>> Set<T> nextCycle(Collection<T> activeCells) {
		Set<T> next = new HashSet<>();

		Map<T, Integer> neighborCount = new HashMap<>();
		for (T activeCell : activeCells) {
			for (T neighbor : activeCell.neighbors()) {
				neighborCount.merge(neighbor, 1, Integer::sum);
			}
		}

		// If a cube is active and exactly 2 or 3 of its neighbors are also active, the
		// cube remains active. Otherwise, the cube becomes inactive.
		for (T activeCell : activeCells) {
			if (List.of(2, 3).contains(neighborCount.getOrDefault(activeCell, 0))) {
				next.add(activeCell);
			}
		}

		// If a cube is inactive but exactly 3 of its neighbors are active, the cube
		// becomes active. Otherwise, the cube remains inactive.
		neighborCount.forEach((cube, count) -> {
			if (count == 3) {
				// A cube with 3 neighboring active cells, gets an active cell either way.
				// Hence we do not need to check if the cube is inactive
				next.add(cube);
			}
		});

		return next;
	}
}
