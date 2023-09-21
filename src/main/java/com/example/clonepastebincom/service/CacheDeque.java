package com.example.clonepastebincom.service;

import com.example.clonepastebincom.model.Pasta;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;

@Service
public class CacheDeque {

    private CacheDeque() {
    }

    private static final Deque<Pasta> arrayDeque = new ArrayDeque<>();

    public static Deque<Pasta> getArrayDeque() {
        return arrayDeque;
    }
}
