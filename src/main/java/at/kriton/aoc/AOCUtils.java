package at.kriton.aoc;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AOCUtils {
	public static final Path PUZZLE_INPUTS = Paths.get(AOCUtils.class.getClassLoader().getResource("input").getPath());
}
