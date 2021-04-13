package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author sergey
 *         created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {

    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<WeakReference<HwListener<K, V>>> listeners = new ArrayList<>();

    private static final Logger log = LoggerFactory.getLogger(MyCache.class);

//Надо реализовать эти методы

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        notifyListeners(key, value, "put");
    }

    @Override
    public void remove(K key) {
        V removed = cache.remove(key);
        notifyListeners(key, removed, "remove");
    }

    @Override
    public V get(K key) {
        V value = cache.get(key);
        notifyListeners(key, value, "get");
        return value;
    }

    private void notifyListeners(K key, V value, String action) {
        for (WeakReference<HwListener<K, V>> reference : listeners) {
            HwListener<K, V> listener = reference.get();
            if (listener == null) {
                listeners.remove(reference);
                continue;
            }

            try {
                listener.notify(key, value, action);
            } catch (Exception e) {
                log.warn("notify failed", e);
            }
        }
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(new WeakReference<>(listener));
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(new WeakReference<>(listener));
    }
}
