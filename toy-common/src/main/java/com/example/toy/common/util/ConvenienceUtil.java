package com.example.toy.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ConvenienceUtil {
    public static List<Long> stringToLongList(String string) {
        string = string.replace("[", "").replace("]", "").replaceAll(" ", "");
        if(string.isEmpty()) return new ArrayList<>();

        return Arrays.stream(string.split(","))
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }
}
