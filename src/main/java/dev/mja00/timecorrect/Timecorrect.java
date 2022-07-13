package dev.mja00.timecorrect;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public final class Timecorrect extends JavaPlugin {

    static final HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create("http://worldtimeapi.org/api/ip")).build();
    static final HttpClient client = HttpClient.newBuilder().build();
    static final Gson gson = new Gson();

    @Override
    public void onEnable() {
        getLogger().info("TimeCorrect is checking the time...");
        // We'll need to make a GET request to http://worldtimeapi.org/api/ip and parse the json
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            String responseBody = null;
            CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
            try {
                responseBody = response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | java.util.concurrent.TimeoutException e) {
                getLogger().warning("TimeCorrect failed to check the time.");
            }
            if (responseBody == null || responseBody.isEmpty()) {
                getLogger().warning("TimeCorrect failed to check the time.");
            } else {
                TimeInfo timeInfo = gson.fromJson(responseBody, TimeInfo.class);
                Long timestamp = timeInfo.getUnixtime();
                Long currentTime = Instant.now().getEpochSecond();
                // Get the difference between the two times
                long difference = currentTime - timestamp;
                getLogger().info("TimeCorrect has detected a time difference of " + difference + " seconds.");
                // Check if the difference is over 60 seconds in either direction
                if (difference > 60 || difference < -60) {
                    // Time to display a big scary warning!
                    getLogger().warning("****************************");
                    getLogger().warning("Your server's time has drifted by " + difference + " seconds!");
                    getLogger().warning("This can cause issues with chat signatures and various other time dependent plugins.");
                    getLogger().warning("Please re-sync your server's time with the internet.");
                    getLogger().warning("This can usually be done with 'ntpdate' or 'timedatectl'.");
                    getLogger().warning("If you use a server host, reach out to their support team.");
                    getLogger().warning("****************************");
                }
            }
        });
    }

    @Override
    public void onDisable() {
        getLogger().info("TimeCorrect has been disabled.");
    }
}
