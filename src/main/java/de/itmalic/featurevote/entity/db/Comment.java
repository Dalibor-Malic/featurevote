package de.itmalic.featurevote.entity.db;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Comment {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String text;
    private Long userId;
    private Long elementId;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

}
