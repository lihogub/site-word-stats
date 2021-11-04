package ru.lihogub.sitewordstats.dto;

import lombok.Data;

@Data
public class WordOccurrenceDto {
    private final String word;
    private final int count;
}
