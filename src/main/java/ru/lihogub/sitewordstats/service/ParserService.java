package ru.lihogub.sitewordstats.service;

import ru.lihogub.sitewordstats.model.SiteModel;

public interface ParserService {
    SiteModel parse(String url);
}
