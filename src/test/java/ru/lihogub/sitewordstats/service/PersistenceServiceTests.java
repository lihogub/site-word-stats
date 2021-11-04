package ru.lihogub.sitewordstats.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.lihogub.sitewordstats.model.SiteModel;
import ru.lihogub.sitewordstats.model.WordOccurrenceModel;
import ru.lihogub.sitewordstats.service.persistence.PersistenceServiceImpl;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
// To test service that uses db. Recreates db on each test.
// Solution from https://stackoverflow.com/a/58903016/16415543
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class PersistenceServiceTests {
    @Autowired
    private PersistenceServiceImpl persistenceService;

    @Test
    @Transactional
    public void testSaveAndGetSite() {
        SiteModel sourceSiteModel = new SiteModel();
        sourceSiteModel.setWords(List.of(
                new WordOccurrenceModel(null, "a", 1L),
                new WordOccurrenceModel(null, "b", 2L),
                new WordOccurrenceModel(null, "c", 3L)
        ));

        persistenceService.saveSite(sourceSiteModel);
        SiteModel savedSiteModel = Assertions.assertDoesNotThrow(() -> persistenceService.findSiteById(sourceSiteModel.getId()).get());
        Assertions.assertNotNull(sourceSiteModel.getId());
        Assertions.assertEquals(sourceSiteModel.getId(), savedSiteModel.getId());
        Assertions.assertNotNull(savedSiteModel.getWords());
        Assertions.assertEquals(sourceSiteModel.getWords().size(), savedSiteModel.getWords().size());
        Assertions.assertEquals((new HashSet<>(sourceSiteModel.getWords())), new HashSet<>(savedSiteModel.getWords()));
    }

    @Test
    @Transactional
    public void testSaveAndDeleteSite() {
        SiteModel sourceSiteModel = new SiteModel();
        sourceSiteModel.setWords(List.of(
                new WordOccurrenceModel(null, "a", 1L),
                new WordOccurrenceModel(null, "b", 2L),
                new WordOccurrenceModel(null, "c", 3L)
        ));

        persistenceService.saveSite(sourceSiteModel);

        Assertions.assertTrue(persistenceService.deleteSiteById(sourceSiteModel.getId()));
    }

    @Test
    @Transactional
    public void testDeleteNotExistingSite() {
        Assertions.assertFalse(persistenceService.deleteSiteById(0L));
    }

    @Test
    @Transactional
    public void testSaveMultipleSiteAndGetList() {
        SiteModel sourceSiteModel0 = new SiteModel();
        sourceSiteModel0.setWords(List.of(
                new WordOccurrenceModel(null, "a", 1L),
                new WordOccurrenceModel(null, "b", 2L),
                new WordOccurrenceModel(null, "c", 3L)
        ));

        persistenceService.saveSite(sourceSiteModel0);

        SiteModel sourceSiteModel1 = new SiteModel();
        sourceSiteModel1.setWords(List.of(
                new WordOccurrenceModel(null, "a", 1L),
                new WordOccurrenceModel(null, "b", 2L),
                new WordOccurrenceModel(null, "c", 3L)
        ));

        persistenceService.saveSite(sourceSiteModel1);

        Assertions.assertEquals(2, persistenceService.getSavedSiteList().size());
    }
}
