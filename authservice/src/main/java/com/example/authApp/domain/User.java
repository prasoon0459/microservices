package com.example.authApp.domain;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name="users", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User implements UserDetails {
    

	private static final long serialVersionUID = -761503632186596342L;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotEmpty
    @Column(length = 32)
    private String username;

    @NotEmpty
    @Column(length = 64)
    private String password;
    
    @NotEmpty
    @Column(length = 128)
    private String name;
    
    @NotEmpty
    private Long phone;
    
    @NotEmpty
    private boolean valid=true;

    
	@ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    
    public User() {}
	public User(@NotEmpty String username, @NotEmpty String password, @NotEmpty String name, @NotEmpty Long phone, List<String> roles) {
		super();
		this.username = username;
		this.password = password;
		this.name=name;
		this.phone=phone;
		this.roles = roles;
	}


	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(toList());
    }


    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public String getName() {
		return name;
	}
	public Long getPhone() {
		return phone;
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
    public List<String> getRoles() {
  		return roles;
  	}

  	public void setRoles(List<String> roles) {
  		this.roles = roles;
  	}
  	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}


}
