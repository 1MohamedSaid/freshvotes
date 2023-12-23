package com.freshvotes.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import com.freshvotes.domain.User;

@Entity
@Data
public class Authority implements GrantedAuthority {
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long id;
  private String authority;
  @ManyToOne()
  private User user;

  @Override
  public String getAuthority() {
    return this.authority;
  }
}
