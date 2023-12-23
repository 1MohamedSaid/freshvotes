package com.freshvotes.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="users")
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class, property="@id")

public class User{
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  private String username;
  private String password;
  private String name;
  @ManyToOne
  private Vote vote;
  @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="user")
  private List<Authority> authorities = new ArrayList<>();
  @OneToMany(cascade=CascadeType.PERSIST, fetch=FetchType.LAZY, mappedBy="user")
  private List<Product> products = new ArrayList<>();
  @OneToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY,mappedBy = "user")
  private List<Feature> features = new ArrayList<>();

}
