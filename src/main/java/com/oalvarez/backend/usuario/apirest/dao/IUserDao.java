package com.oalvarez.backend.usuario.apirest.dao;

import com.oalvarez.backend.usuario.apirest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserDao extends JpaRepository<User, Long> {

    public User findByEmail(String email);
    public User findByUuid(String uuid);

    public boolean existsByEmail(String email);

}
