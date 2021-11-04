package ru.lihogub.sitewordstats.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SiteModel {
    @Id
    @GeneratedValue
    private Long id;
    private String url;
    @OneToMany
    @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private List<WordOccurrenceModel> words;
}
