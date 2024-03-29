package com.bookstore.repository;

import com.bookstore.model.ClientRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRoleRepository extends JpaRepository<ClientRole, Long> {

    ClientRole findByRole(String role);
}
