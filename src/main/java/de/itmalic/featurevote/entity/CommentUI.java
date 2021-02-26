package de.itmalic.featurevote.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
public class CommentUI {


    @Id
    private Long id;

    private int counter;
    private String text;
    private String userFirstname;
    private String userLastname;
    private Long userId;
    private Long elementId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

}
