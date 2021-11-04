package ru.lihogub.sitewordstats.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SiteListDto {
    private final List<SiteDto> siteList;
}
