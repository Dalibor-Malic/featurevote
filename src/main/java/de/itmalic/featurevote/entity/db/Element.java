package de.itmalic.featurevote.entity.db;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Element {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String title;
    private String description;
    private String category;
    private Long createdByUserId;
    private Long dashboardId;
    private boolean online;
}
