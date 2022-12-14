/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/** https://adventofcode.com/2022/day/6 */
class Day6Test {

  static Stream<Arguments> args() throws Exception {
    return Stream.of(
        Arguments.of("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 7, true),
        Arguments.of("bvwbjplbgvbhsrlpgdmjqwftvncz", 5, true),
        Arguments.of("nppdvjthqldpwncqszvftbrmjlhg", 6, true),
        Arguments.of("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 10, true),
        Arguments.of("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 11, true),
        Arguments.of(
            new String(Day1Test.class.getResourceAsStream("/input6.txt").readAllBytes()),
            1_480,
            true),
        Arguments.of("mjqjpqmgbljsphdztnvjfqwrcgsmlb", 19, false),
        Arguments.of("bvwbjplbgvbhsrlpgdmjqwftvncz", 23, false),
        Arguments.of("nppdvjthqldpwncqszvftbrmjlhg", 23, false),
        Arguments.of("nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg", 29, false),
        Arguments.of("zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw", 26, false),
        Arguments.of(
            new String(Day1Test.class.getResourceAsStream("/input6.txt").readAllBytes()),
            2_746,
            false));
  }

  record Buffer(int uniqueCount, int index, List<Integer> data) {
    Buffer(int uniqueCount) {
      this(uniqueCount, 0, new ArrayList<Integer>(uniqueCount));
    }

    Buffer append(Integer elem) {
      data.add(elem);
      return new Buffer(
          uniqueCount,
          index + 1,
          data.size() > uniqueCount ? data.subList(1, uniqueCount + 1) : data);
    }

    boolean packetMarker() {
      return new HashSet<>(data).size() == uniqueCount;
    }
  }

  int solve(String line, boolean partOne) {
    // find the index of first occurance of 4 unique chars
    var iter = line.chars().boxed().iterator();
    return Stream.iterate(new Buffer(partOne ? 4 : 14), buf -> buf.append(iter.next()))
        .filter(Buffer::packetMarker)
        .findFirst()
        .map(Buffer::index)
        .orElse(0);
  }

  @ParameterizedTest
  @MethodSource("args")
  void test(String lines, int expected, boolean partOne) throws Exception {
    assertEquals(expected, solve(lines, partOne));
  }
}
