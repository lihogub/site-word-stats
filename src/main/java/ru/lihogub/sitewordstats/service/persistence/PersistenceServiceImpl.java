package ru.lihogub.sitewordstats.service.persistence;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lihogub.sitewordstats.model.SiteModel;
import ru.lihogub.sitewordstats.repository.SiteRepository;
import ru.lihogub.sitewordstats.repository.WordOccurrenceRepository;
import ru.lihogub.sitewordstats.service.PersistenceService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersistenceServiceImpl implements PersistenceService {
    private final SiteRepository siteRepository;
    private final WordOccurrenceRepository wordOccurrenceRepository;

    /**
     * Returns all sites from database.
     * Return empty list if database is empty.
     */
    @Override
    public List<SiteModel> getSavedSiteList() {
        return siteRepository.findAll();
    }

    /**
     * Deletes site from database.
     * Returns true if successfully deleted, otherwise returns false.
     */
    @Override
    public boolean deleteSiteById(Long id) {
        Optional<SiteModel> optionalSite = siteRepository.findById(id);
        optionalSite.ifPresent(siteRepository::delete);
        return optionalSite.isPresent();
    }

    /*
    *  Returns optional site from database.
    */
    @Override
    public Optional<SiteModel> findSiteById(Long id) {
        return siteRepository.findById(id);
    }

    /**
    * Saves site to database.
    */
    @Override
    public SiteModel saveSite(SiteModel siteModel) {
        // Save child objects before parent.
        wordOccurrenceRepository.saveAll(siteModel.getWords());
        return siteRepository.save(siteModel);
    }
}
