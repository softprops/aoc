/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package aoc;

import static java.lang.Integer.parseInt;
import static java.util.stream.Stream.concat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/** https://adventofcode.com/2022/day/1 */
class Day1Test {
  record Elf(int calories) {
    Elf() {
      this(0);
    }

    public Elf add(int calories) {
      return new Elf(this.calories() + calories);
    }
  }

  static Stream<Arguments> args() throws Exception {
    return Stream.of(
        Arguments.of(
            """
            1000
            2000
            3000

            4000

            5000
            6000

            7000
            8000
            9000

            10000
            """
                .replace("\n$", "")
                .lines(),
            24_000),
        Arguments.of(
            new String(Day1Test.class.getResourceAsStream("/input1.txt").readAllBytes()).lines(),
            71_780));
  }

  int solve(Stream<String> lines) {
    return lines
        .reduce(
            List.of(new Elf()),
            (elves, line) ->
                switch (line) {
                  case "" -> concat(Stream.of(new Elf()), elves.stream()).toList();
                  case String s -> concat(
                          Stream.of(elves.get(0).add(parseInt(s))), elves.stream().skip(1))
                      .toList();
                },
            (prev, next) -> concat(prev.stream(), next.stream()).toList())
        .stream()
        .map(Elf::calories)
        .max(Comparator.naturalOrder())
        .orElse(0);
  }

  @ParameterizedTest
  @MethodSource("args")
  void test(Stream<String> lines, int expected) throws Exception {
    assertEquals(expected, solve(lines));
  }
}