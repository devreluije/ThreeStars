package fr.reluije.threeStars.web;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.reluije.threeStars.dto.PlayerProfileDTO;
import fr.reluije.threeStars.exceptions.RequestError;
import fr.reluije.threeStars.exceptions.ServerError;
import fr.reluije.threeStars.utils.EnumUtils;
import org.bukkit.configuration.ConfigurationSection;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class WebAccess {

    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().disableJdkUnsafe().create();

    private boolean loaded;
    private ExecutorService executor;
    private HttpClient client;
    private String url;

    public void load(ConfigurationSection section) {
        int threadPoolSize = section.getInt("thread-pool.size", 4);
        String threadPoolNameFormat = section.getString("thread-pool.name-format", "WebAccess Pool - %d");

        String redirectPolicy = section.getString("client.redirect-policy", "NEVER").toUpperCase();
        String version = section.getString("client.version", "HTTP_1_1").toUpperCase();

        url = section.getString("url");
        if (url == null) throw new IllegalArgumentException("URL is null");

        if (!url.endsWith("/")) url += "/";

        executor = Executors.newFixedThreadPool(threadPoolSize,
                new ThreadFactoryBuilder().setNameFormat(threadPoolNameFormat).build());
        client = HttpClient.newBuilder()
                .followRedirects(EnumUtils.get(Redirect.class, redirectPolicy, Redirect.NEVER))
                .version(EnumUtils.get(Version.class, version, Version.HTTP_1_1))
                .executor(executor).build();
        loaded = true;
    }

    public CompletableFuture<PlayerProfileDTO> createProfile(PlayerProfileDTO playerProfileDTO) {
        String profileJson = GSON.toJson(playerProfileDTO);

        return execute("players",
                builder -> builder.POST(HttpRequest.BodyPublishers.ofString(profileJson, StandardCharsets.UTF_8)));
    }

    public CompletableFuture<PlayerProfileDTO> getProfileByUniqueId(UUID uniqueId) {
        return execute("players/" + uniqueId, HttpRequest.Builder::GET);
    }

    public void close() {
        if (loaded) {
            executor.shutdownNow();

            executor = null;
            client = null;
            url = null;
            loaded = false;
        }
    }

    private CompletableFuture<PlayerProfileDTO> execute(String endpoint, Consumer<HttpRequest.Builder> builderConsumer) {
        if (!loaded) return CompletableFuture.failedFuture(new IllegalStateException("Web access are not loaded"));
        CompletableFuture<PlayerProfileDTO> future = new CompletableFuture<>();
        HttpRequest.Builder builder = HttpRequest.newBuilder().uri(URI.create(url + endpoint));

        builderConsumer.accept(builder);
        client.sendAsync(builder.build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8))
                .whenComplete((response, error) -> {
                    if (error != null) future.completeExceptionally(error);
                    else {
                        error = checkError(response.statusCode());

                        if (error == null) future.complete(GSON.fromJson(response.body(), PlayerProfileDTO.class));
                        else future.completeExceptionally(error);
                    }
                });
        return future;
    }

    private Throwable checkError(int statusCode) {
        int type = statusCode / 100;

        return switch (type) {
            case 4 -> new RequestError();
            case 5 -> new ServerError();
            default -> null;
        };
    }
}
