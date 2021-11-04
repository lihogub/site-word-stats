package ru.lihogub.sitewordstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lihogub.sitewordstats.model.WordOccurrenceModel;

@Repository
public interface WordOccurrenceRepository extends JpaRepository<WordOccurrenceModel, Long> {
}
