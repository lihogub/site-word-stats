package ru.lihogub.sitewordstats.dto;

import lombok.Data;

import java.util.List;

@Data
public class SiteDto {
    private final Long id;
    private final String siteUrl;
    private final List<WordOccurrenceDto> words;
}
