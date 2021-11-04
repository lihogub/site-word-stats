package ru.lihogub.sitewordstats.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class WordOccurrenceModel {
    @Id
    @GeneratedValue
    private Long id;
    @Column(columnDefinition = "varchar(1024)")
    private String word;
    private Long count;
}
