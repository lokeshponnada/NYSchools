package com.example.nyschools.global;


import androidx.collection.LruCache;

import com.example.nyschools.model.Score;

/* LRU Cache for storing School Scores */
public class ScoreCache {

    /* The value of this is a tradeoff b/w app memory used  and network calls  */
    public static int MAX_CACHE_SIZE = 100;

    private static ScoreCache instance;
    private final LruCache<String, Score> lru;

    private ScoreCache() {
        lru = new LruCache<>(MAX_CACHE_SIZE);
    }

    public synchronized static ScoreCache getInstance() {
        if (instance == null) {
            instance = new ScoreCache();
        }
        return instance;
    }

    public Score getItem(String schoolId){
        return lru.get(schoolId);
    }

    public void putItem(String schoolId,Score score){
        lru.put(schoolId,score);
    }

}
