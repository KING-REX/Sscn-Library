package com.sscn.library.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@NoArgsConstructor
@Data
@Entity
@Table(name = "authorities")
public class Authority
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(referencedColumnName = "username", nullable = false, name = "username")
  private User user;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private UserRole authority;

  public Authority(User user, UserRole authority)
  {
    this.user = user;
    this.authority = authority;
  }

  public Authority(User user, String authority) {
    this.user = user;
    this.authority = UserRole.valueOf(authority);
  }

  @Override
  public boolean equals(Object o)
  {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    Authority authority1 = (Authority) o;
    return id.equals(authority1.id) && user.equals(authority1.user) && authority.equals(authority1.authority);
  }

  @Override
  public int hashCode()
  {
    return Objects.hash(id, user, authority);
  }
}
