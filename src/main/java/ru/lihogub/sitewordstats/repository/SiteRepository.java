package ru.lihogub.sitewordstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.lihogub.sitewordstats.model.SiteModel;

@Repository
public interface SiteRepository extends JpaRepository<SiteModel, Long> {
}
