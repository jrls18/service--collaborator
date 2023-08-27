package br.com.group.developer.corporation.service.collaborator.api.rest.security.model;

import br.com.group.developer.corporation.service.collaborator.domain.model.Collaborator;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Getter
public class UserPrinciple implements UserDetails {

    private static final long serialVersionUID = 4864365905542688052L;

    private Long id;

    private String name;

    private String username;

    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrinciple(Long id, String name, String username, String password,
                         Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrinciple build(Collaborator collaborator) {
        GrantedAuthority authorities = new SimpleGrantedAuthority(collaborator.getTypeCollaborator().getName());

        List<GrantedAuthority> authoritiesList = new ArrayList<>();
        authoritiesList.add(authorities);

        return new UserPrinciple(collaborator.getId(), collaborator.getName(), collaborator.getContact().getEmail(), collaborator.getPassword(),
                authoritiesList);
    }


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        UserPrinciple user = (UserPrinciple) o;
        return Objects.equals(id, user.id);
    }
}
