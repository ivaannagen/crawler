package uk.co.monzo.crawler.repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Abstract CRUD - manages resources and can be used to concurrently persist entities.
 * Can be replaced by in:mem database as appose to local cache.
 *
 * Need to set eviction policy to avoid stale data.
 *
 * @param <K> The type of key we want to use for retrieving values.
 * @param <V> The type that we want to retrieve/persist.
 */
public abstract class AbstractCRUD<K, V> {

    private final ConcurrentHashMap<K, V> cache = new ConcurrentHashMap<>();


    public void create(K key, V entityToCreate) {
        if (entityToCreate != null) {
            cache.putIfAbsent(key, entityToCreate);
        } else {
            System.out.println("Cannot persist null entity.");
        }
    }


    public Optional<V> get(K id) {
        return Optional.ofNullable(cache.get(id));
    }

    public ConcurrentHashMap<K, V> getCache() {
        return cache;
    }

}