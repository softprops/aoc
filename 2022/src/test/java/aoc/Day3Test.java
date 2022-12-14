/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package aoc;

import static java.lang.Math.min;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.IntStream.rangeClosed;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/** https://adventofcode.com/2022/day/3 */
class Day3Test {
  static int[] items = IntStream.concat(rangeClosed('a', 'z'), rangeClosed('A', 'Z')).toArray();
  static Map<Integer, Item> catalog =
      rangeClosed(1, 52).boxed().collect(toMap(p -> items[p - 1], Item::new));

  record Item(int priority) {}

  record Rucksack(List<Item> compartmentA, List<Item> compartmentB) {

    public static Rucksack fromString(String str) {
      return new Rucksack(
          str.substring(0, str.length() / 2).chars().boxed().map(catalog::get).collect(toList()),
          str.substring(str.length() / 2, str.length())
              .chars()
              .boxed()
              .map(catalog::get)
              .collect(toList()));
    }

    public List<Item> items() {
      return Stream.concat(compartmentA.stream(), compartmentB.stream()).collect(toList());
    }

    public Optional<Integer> duplicatePriority() {
      var intersect = new HashSet<Item>();
      intersect.addAll(compartmentA);
      intersect.retainAll(compartmentB);
      // System.out.println("intersect " + intersect);
      return intersect.stream().findFirst().map(Item::priority);
    }
  }

  static Stream<Arguments> args() throws Exception {
    return Stream.of(
        Arguments.of(
            """
            vJrwpWtwJgWrhcsFMMfFFhFp
            jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
            PmmdzqPrVvPwwTWBwg
            wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
            ttgJtRGJQctTZtZT
            CrZsJsPPZsGzwwsLwLmpwMDw"""
                .lines(),
            157,
            true),
        Arguments.of(
            new String(Day1Test.class.getResourceAsStream("/input3.txt").readAllBytes()).lines(),
            8_039,
            true),
        Arguments.of(
            """
              vJrwpWtwJgWrhcsFMMfFFhFp
              jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
              PmmdzqPrVvPwwTWBwg
              wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
              ttgJtRGJQctTZtZT
              CrZsJsPPZsGzwwsLwLmpwMDw"""
                .lines(),
            70,
            false),
        Arguments.of(
            new String(Day1Test.class.getResourceAsStream("/input3.txt").readAllBytes()).lines(),
            2_510,
            false));
  }

  <T> Stream<List<T>> grouped(Stream<T> stream, int size) {
    var xs = stream.collect(toList());
    return IntStream.range(0, (xs.size() + size - 1) / size)
        .mapToObj(i -> xs.subList(i * size, min(size * (i + 1), xs.size())));
  }

  int solve(Stream<String> lines, boolean partOne) {
    if (partOne) {
      return lines.reduce(
          0,
          (total, line) -> total + Rucksack.fromString(line).duplicatePriority().orElse(0),
          Integer::sum);
    }
    return grouped(lines, 3)
        .reduce(
            0,
            (total, group) -> {
              var cadidates =
                  group.stream()
                      .skip(1)
                      .map(Rucksack::fromString)
                      .reduce(
                          new HashSet<Item>(Rucksack.fromString(group.get(0)).items()),
                          (same, rucksack) -> {
                            same.retainAll(rucksack.items());
                            return same;
                          },
                          (prev, next) -> next);
              return total + cadidates.stream().findFirst().map(Item::priority).orElse(0);
            },
            Integer::sum);
  }

  @ParameterizedTest
  @MethodSource("args")
  void test(Stream<String> lines, int expected, boolean partOne) throws Exception {
    assertEquals(expected, solve(lines, partOne));
  }
}
