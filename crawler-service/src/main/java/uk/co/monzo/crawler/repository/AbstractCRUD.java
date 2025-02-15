package uk.co.monzo.crawler.repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Abstract CRUD - manages resources and can be used to concurrently persist entities.
 * Can be replaced by in:mem database as appose to local cache.
 *
 * @param <K> The type of key we want to use for retrieving values.
 * @param <V> The type that we want to retrieve/persist.
 */
public abstract class AbstractCRUD<K, V> {

    private final ConcurrentHashMap<K, V> cache = new ConcurrentHashMap<>();


    protected V create(K key, V entityToCreate) {
        if (entityToCreate != null) {
            cache.putIfAbsent(key, entityToCreate);
        } else {
            System.out.println("Cannot persist null entity.");
        }

        return entityToCreate;
    }


    public Optional<V> get(K id) {
        return Optional.of(cache.get(id));
    }

    protected void update(K key, V entityToUpdate) {
        cache.computeIfPresent(key, (k, v) -> entityToUpdate);
    }

    protected void delete(K key) {
        cache.remove(key);
    }

    public ConcurrentHashMap<K, V> getCache() {
        return cache;
    }

}
