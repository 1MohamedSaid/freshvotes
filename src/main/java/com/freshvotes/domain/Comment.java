package com.freshvotes.domain;

import java.util.*;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id")
@Getter
@Setter
public class Comment implements Comparable<Comment> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 5000)
    private String text;
    private Integer voteCount = 0;
    @ManyToOne
    @JsonIgnore
    private User user;
    @ManyToOne
    @JsonIgnore
    private Feature feature;
    @OneToMany(mappedBy = "comment")
    @OrderBy("createdOn, id")
    private SortedSet<Comment> comments;
    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = true)
    private Comment comment;
    private Date createdOn;
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Vote> votes;

    @Override
    public String toString() {
        return "Comment [id=" + id + ", text=" + text + "]";
    }

    @Override
    public int compareTo(Comment that) {
        int val1 = this.createdOn.compareTo(that.createdOn);
        int val2 = this.id.compareTo(that.id);
        if(val1==0){
            val1=val2;
        }
        return val1;
    }
}
