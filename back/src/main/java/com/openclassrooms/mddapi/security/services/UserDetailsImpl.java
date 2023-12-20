package com.openclassrooms.mddapi.security.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {
  
  private static final long serialVersionUID = 1L;

  private Long id;

  private String theUserName;

  private String username;

  @JsonIgnore
  private String password;  
  
  /** 
   * @return Collection<? extends GrantedAuthority>
   */
  public Collection<? extends GrantedAuthority> getAuthorities() {        
      return new HashSet<GrantedAuthority>();
  }
  
  /** 
   * @return boolean
   */
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }
  
  /** 
   * @return boolean
   */
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }
  
  /** 
   * @return boolean
   */
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }
  
  /** 
   * @return boolean
   */
  @Override
  public boolean isEnabled() {
    return true;
  }
  
  /** 
   * @param o
   * @return boolean
   */
  @Override
  public boolean equals(Object o) {
    if (this == o)
      return true;
    if (o == null || getClass() != o.getClass())
      return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  } 
}
