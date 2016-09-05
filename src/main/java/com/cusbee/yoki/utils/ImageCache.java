package com.cusbee.yoki.utils;

import org.apache.commons.collections.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageCache {
    private static Map<Long, List<String>> cache = new HashMap<>();

    public static List<String> getLinks(Long id) {
        if(cache.containsKey(id)) {
            return cache.get(id);
        } else {
            return null;
        }
    }

    public static boolean cached(Long id) {
        return cache.containsKey(id);
    }

    public static void removeFromCache(Long id) {
        if(cache.containsKey(id)) {
            cache.remove(id);
        }
    }

    public static void putToCache(Long id, List<String> links) {
        if(CollectionUtils.isNotEmpty(links)) {
            cache.put(id, links);
        }
    }
}
