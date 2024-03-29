package com.bookstore.security;

import com.bookstore.model.Client;
import com.bookstore.model.ClientRole;
import com.bookstore.repository.ClientRepository;
import com.bookstore.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private ClientService clientService;

    public CustomUserDetailsService(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) {
        Optional<Client> client = clientService.findClientForLogin(userName);
        if (client.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                    client.get().getUserName(),
                    client.get().getPassword(),
                    convertAuthorities(client.get().getClientRoles()));
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }

    private Set<GrantedAuthority> convertAuthorities(Set<ClientRole> clientRoles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (ClientRole cr : clientRoles) {
            authorities.add(new SimpleGrantedAuthority(cr.getRole()));
        }
        return authorities;
    }
}
