package com.freshvotes.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean voteType;
    @ManyToOne
    private User user;
    @ManyToOne
    private Comment comment;
    @ManyToOne
    private Feature feature;
}
