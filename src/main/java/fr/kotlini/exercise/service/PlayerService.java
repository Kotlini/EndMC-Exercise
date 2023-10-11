package fr.kotlini.exercise.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.google.inject.Inject;
import fr.kotlini.exercise.model.PlayerInfos;
import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Getter
public class PlayerService
{
    private final DatabaseService databaseService;

    private final Cache<UUID, PlayerInfos> playerCache;

    @Inject
    public PlayerService(DatabaseService databaseService) {
        this.databaseService = databaseService;
        this.playerCache = Caffeine.newBuilder().expireAfterAccess(5, TimeUnit.MINUTES).build();
    }

    public CompletableFuture<PlayerInfos> getOrCreate(UUID uuid, String name) {
        return CompletableFuture.supplyAsync(() -> databaseService.getOrCreate(uuid, name));
    }

    public PlayerInfos getCachePlayer(UUID uuid) {
        return playerCache.getIfPresent(uuid);
    }

    public boolean hasCachePlayer(UUID uuid) {
        return playerCache.asMap().containsKey(uuid);
    }
}