package com.sscn.library.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String username;

  @Column(nullable = false)
  private String password;

  @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  private List<Authority> authorities;

  public User()
  {
    this.authorities = new ArrayList<>();
  }

  public User(String username, String password, String... authorities)
  {
    this.username = username;
    this.password = password;
    setAuthorities(authorities);
  }

  public User(Long id, String username, String password, String... authorities)
  {
    this.id = id;
    this.username = username;
    this.password = password;
    setAuthorities(authorities);
  }

  public Collection<? extends GrantedAuthority> getAuthorities()
  {
    return this.authorities.stream()
        .map(authority -> new SimpleGrantedAuthority(authority.getAuthority()))
        .collect(Collectors.toList());
  }

  public void setAuthorities(List<Authority> authorities)
  {
    this.authorities = authorities;
  }

  public void setAuthorities(String... authorities)
  {
    this.authorities = new ArrayList<>();

    Arrays.stream(authorities)
        .forEach(auth -> this.authorities.add(new Authority(this, auth)));
  }

  @Override
  public boolean isAccountNonExpired()
  {
    return true;
  }

  @Override
  public boolean isAccountNonLocked()
  {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired()
  {
    return true;
  }

  @Override
  public boolean isEnabled()
  {
    return true;
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    User user = (User) o;
    return id.equals(user.id) && username.equals(user.username) && password.equals(user.password) && authorities.equals(user.authorities);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(id, username, password, authorities);
  }
}
