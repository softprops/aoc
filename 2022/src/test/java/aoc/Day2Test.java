/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/** https://adventofcode.com/2022/day/2 */
class Day2Test {
  enum Outcome {
    LOST(0),
    DRAW(3),
    WIN(6);
    public final int score;

    Outcome(int score) {
      this.score = score;
    }

    static Outcome fromString(String str) {
      return switch (str) {
        case "X" -> Outcome.LOST;
        case "Y" -> Outcome.DRAW;
        case "Z" -> Outcome.WIN;
        default -> throw new IllegalArgumentException();
      };
    }

    Move strategicMove(Move other) {
      return switch (this) {
        case LOST -> switch (other) {
          case ROCK -> Move.SCISSORS;
          case PAPER -> Move.ROCK;
          case SCISSORS -> Move.PAPER;
        };
        case DRAW -> other;
        case WIN -> switch (other) {
          case ROCK -> Move.PAPER;
          case PAPER -> Move.SCISSORS;
          case SCISSORS -> Move.ROCK;
        };
      };
    }
  }

  enum Move {
    ROCK(1),
    PAPER(2),
    SCISSORS(3);
    public final int score;

    Move(int score) {
      this.score = score;
    }

    static Move fromString(String str) {
      return switch (str) {
        case "A", "X" -> ROCK;
        case "B", "Y" -> PAPER;
        case "C", "Z" -> SCISSORS;
        default -> throw new IllegalArgumentException();
      };
    }

    Outcome outcome(Move other) {
      if (this == other) {
        return Outcome.DRAW;
      }
      return switch (this) {
        case ROCK -> other == Move.SCISSORS ? Outcome.WIN : Outcome.LOST;
        case PAPER -> other == Move.ROCK ? Outcome.WIN : Outcome.LOST;
        case SCISSORS -> other == Move.PAPER ? Outcome.WIN : Outcome.LOST;
      };
    }
  }

  static Stream<Arguments> args() throws Exception {
    return Stream.of(
        Arguments.of("""
            A Y
            B X
            C Z""".lines(), 15, true),
        Arguments.of(
            new String(Day1Test.class.getResourceAsStream("/input2.txt").readAllBytes()).lines(),
            12_772,
            true),
        Arguments.of("""
            A Y
            B X
            C Z""".lines(), 12, false),
        Arguments.of(
            new String(Day1Test.class.getResourceAsStream("/input2.txt").readAllBytes()).lines(),
            11_618,
            false));
  }

  int solve(Stream<String> lines, boolean partOne) {
    return lines.reduce(
        0,
        (total, line) -> {
          var round = line.split(" ");
          var theirMove = Move.fromString(round[0]);
          var yourMove =
              partOne
                  ? Move.fromString(round[1])
                  : Outcome.fromString(round[1]).strategicMove(theirMove);
          return total + yourMove.outcome(theirMove).score + yourMove.score;
        },
        Integer::sum);
  }

  @ParameterizedTest
  @MethodSource("args")
  void test(Stream<String> lines, int expected, boolean partOne) throws Exception {
    int actual = solve(lines, partOne);
    assertEquals(expected, actual);
  }
}
