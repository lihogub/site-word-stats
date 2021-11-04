package ru.lihogub.sitewordstats.service;

import ru.lihogub.sitewordstats.model.SiteModel;

import java.util.List;
import java.util.Optional;

public interface PersistenceService {
    List<SiteModel> getSavedSiteList();
    boolean deleteSiteById(Long id);
    Optional<SiteModel> findSiteById(Long id);
    SiteModel saveSite(SiteModel siteModel);
}
