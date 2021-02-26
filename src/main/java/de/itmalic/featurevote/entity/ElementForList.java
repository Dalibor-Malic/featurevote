package de.itmalic.featurevote.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
public class ElementForList {

    private Long id;

    private String title;
    private String description;
    private Long createdByUserId;
    private boolean userOwner;
    private Long dashboardId;
    private boolean online;
    private boolean voted;
    private long votes;
}
