package at.kriton.aoc;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class AOCUtils {
	public static final Path PUZZLE_INPUTS = Paths.get(AOCUtils.class.getClassLoader().getResource("input").getPath());
	
	public static long chineseRemainder(Map<Integer, Integer> modAndRemainder) {

		long prod = modAndRemainder.keySet().stream().map(Integer::longValue).reduce(1L, (i, j) -> i * j);

		long sm = 0;
		for (Map.Entry<Integer, Integer> entry : modAndRemainder.entrySet()) {
			long mod = entry.getKey();
			long remainder = entry.getValue();
			long p = prod / mod;
			sm += remainder * mulInv(p, mod) * p;
		}
		return sm % prod;
	}

	/**
	 * multiplicative inverse mod b
	 * mulInv(a,b) * a == 1 mod b
	 */
	public static long mulInv(long a, long b) {
		long b0 = b;
		long x0 = 0;
		long x1 = 1;

		if (b == 1)
			return 1;

		while (a > 1) {
			long q = a / b;
			long amb = a % b;
			a = b;
			b = amb;
			long xqx = x1 - q * x0;
			x1 = x0;
			x0 = xqx;
		}

		if (x1 < 0) {
			x1 += b0;
		}

		return x1;
	}
}
