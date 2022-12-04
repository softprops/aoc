/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/** https://adventofcode.com/2022/day/4 */
class Day4Test {

  static Stream<Arguments> args() throws Exception {
    return Stream.of(
        Arguments.of(
            """
            2-4,6-8
            2-3,4-5
            5-7,7-9
            2-8,3-7
            6-6,4-6
            2-6,4-8
            """
                .replace("\n$", "")
                .lines(),
            2));
  }

  int solve(Stream<String> lines) {
    return 0;
  }

  @ParameterizedTest
  @MethodSource("args")
  void test(Stream<String> lines, int expected) throws Exception {
    assertEquals(expected, solve(lines));
  }
}
