package com.bonqa.bonqa.domain.model;

import com.bonqa.bonqa.exception.NotAuthorizedException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor
@Entity
@Table(name = "bank_user")
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "email", nullable = false, unique = true, length = 100)
  private String email;

  @Column(name = "username", nullable = false, unique = true, length = 100)
  private String username;

  @Column(name = "password", nullable = false, length = 100)
  private String password;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "portfolio_id")
  private Portfolio portfolio;

  @Column(name = "create_time", nullable = false)
  @CreationTimestamp
  private LocalDateTime createdTime;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private Role role;

  @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
  private List<Token> tokens;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  private void preventUnAuthorizedRemove() {

    String name = SecurityContextHolder.getContext().getAuthentication().getName();

    if (!name.equals(this.username)) {
      throw new NotAuthorizedException("Users can only delete themselves.");
    }

  }

  public void preventUnauthorizedUpdate() {
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    if (!name.equals(this.username)) {
      throw new NotAuthorizedException("Users can only update their own information.");
    }
  }

}


