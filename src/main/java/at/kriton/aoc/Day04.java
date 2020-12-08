package at.kriton.aoc;

import static java.nio.file.Files.readString;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Day04 {
	static final Pattern PASSPORT_GROUPS = Pattern.compile("\\s*([^\\s]*?):([^\\s]*)\\s*");
	static final Set<String> REQUIRED_FIELDS = Set.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid");

	public static void main(String[] args) throws IOException {
		List<Map<String, String>> passports = Stream
				.of(readString(Paths.get(args[0])).replaceAll("(?<!\\n)\\n(?!\\n)", " ").split("\\n{2,}"))
				.map(Day04::passportEntries).collect(toList());
		long validPassportsPart1Count = passports.stream().filter(Day04::isValidPassportPart1).count();
		long validPassportsPart2Count = passports.stream().filter(Day04::isValidPassportPart2).count();

		System.out.format("part01: %d%n", validPassportsPart1Count);
		System.out.format("part02: %d%n", validPassportsPart2Count);
	}

	static Map<String, String> passportEntries(String str) {
		Matcher matcher = PASSPORT_GROUPS.matcher(str);

		Map<String, String> passportEntries = new HashMap<>();
		while (matcher.find()) {
			passportEntries.put(matcher.group(1), matcher.group(2));
		}
		return passportEntries;
	}

	static boolean isValidPassportPart1(Map<String, String> passportEntries) {
		return passportEntries.keySet().containsAll(REQUIRED_FIELDS);
	}

	static boolean isValidPassportPart2(Map<String, String> passportEntries) {
		return passportEntries.keySet().containsAll(REQUIRED_FIELDS)
				&& byrValid(passportEntries.get("byr"))
				&& iyrValid(passportEntries.get("iyr"))
				&& eyrValid(passportEntries.get("eyr"))
				&& hgtValid(passportEntries.get("hgt"))
				&& hclValid(passportEntries.get("hcl"))
				&& eclValid(passportEntries.get("ecl"))
				&& pidValid(passportEntries.get("pid"));
	}

	static boolean byrValid(String byr) {
		if (!byr.matches("\\d{4}")) {
			return false;
		}
		int year = Integer.parseInt(byr);
		return 1920 <= year && year <= 2002;
	}

	static boolean iyrValid(String iyr) {
		if (!iyr.matches("\\d{4}")) {
			return false;
		}
		int year = Integer.parseInt(iyr);
		return 2010 <= year && year <= 2020;
	}

	static boolean eyrValid(String eyr) {
		if (!eyr.matches("\\d{4}")) {
			return false;
		}
		int year = Integer.parseInt(eyr);
		return 2020 <= year && year <= 2030;
	}

	static boolean hgtValid(String hgt) {
		Pattern hgtPattern = Pattern.compile("^(\\d+)(cm|in)$");
		Matcher matcher = hgtPattern.matcher(hgt);
		if (!matcher.find()) {
			return false;
		}
		int height = Integer.parseInt(matcher.group(1));
		return (Objects.equals(matcher.group(2), "cm") && 150 <= height && height <= 193)
				|| (Objects.equals(matcher.group(2), "in") && 59 <= height && height <= 76);
	}

	static boolean hclValid(String hcl) {
		return hcl.matches("#[0-9a-f]{6}");
	}

	static boolean eclValid(String ecl) {
		return Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(ecl);
	}

	static boolean pidValid(String pid) {
		return pid.matches("\\d{9}");
	}
}
