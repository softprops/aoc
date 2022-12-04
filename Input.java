import static java.net.http.HttpResponse.BodyHandlers;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;

class Input {
  public static void main(String[] args) throws Exception {
    var http = HttpClient.newHttpClient();
    var year = 2022;
    var responses =
        IntStream.rangeClosed(1, 31)
            .mapToObj(
                date ->
                    http.sendAsync(
                        HttpRequest.newBuilder()
                            .uri(
                                URI.create(
                                    "https://adventofcode.com/%s/day/%d/input"
                                        .formatted(year, date)))
                            .build(),
                        BodyHandlers.ofString()))
            .toList();
    var combined =
        CompletableFuture.allOf(responses.toArray(CompletableFuture[]::new))
            .thenApply(v -> responses.stream().map(CompletableFuture::join).toList())
            .get();
    System.out.println(combined);
  }
}
