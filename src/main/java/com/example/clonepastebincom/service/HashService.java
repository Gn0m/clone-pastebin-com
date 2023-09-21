package com.example.clonepastebincom.service;

import com.example.clonepastebincom.model.Pasta;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class HashService {

    private HashService() {
    }

    public static void goodFastHash(Pasta pasta) {
        String convertStr = paramToString(pasta);
        String hash = Hashing.goodFastHash(30)
                .hashString(convertStr, StandardCharsets.UTF_8)
                .toString();
        pasta.setHash(hash);
    }

    private static String paramToString(Pasta pasta) {
        if (pasta.getExpiration() != null) {
            return String.valueOf(pasta.getAccess())
                    .concat(String.valueOf(pasta.getExpiration()))
                    .concat(pasta.getText());
        } else {
            return String.valueOf(pasta.getAccess())
                    .concat(pasta.getText());
        }
    }
}
