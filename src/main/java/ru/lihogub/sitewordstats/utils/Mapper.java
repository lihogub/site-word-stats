package ru.lihogub.sitewordstats.utils;

import ru.lihogub.sitewordstats.dto.SiteDto;
import ru.lihogub.sitewordstats.dto.WordOccurrenceDto;
import ru.lihogub.sitewordstats.model.SiteModel;
import ru.lihogub.sitewordstats.model.WordOccurrenceModel;

import java.util.stream.Collectors;

public class Mapper {
    /**
     * Converts Site to SiteDto.
     */
    public static SiteDto toSiteDto(SiteModel siteModel) {
        return new SiteDto(
                siteModel.getId(),
                siteModel.getUrl(),
                siteModel
                        .getWords()
                        .stream()
                        .map(Mapper::toWordOccurrenceDto)
                        .collect(Collectors.toList())
        );
    }

    /**
     * Converts WordOccurrence to WordOccurrenceDto.
     */
    public static WordOccurrenceDto toWordOccurrenceDto(WordOccurrenceModel wordOccurrenceModel) {
        return new WordOccurrenceDto(wordOccurrenceModel.getWord(), wordOccurrenceModel.getCount().intValue());
    }
}
