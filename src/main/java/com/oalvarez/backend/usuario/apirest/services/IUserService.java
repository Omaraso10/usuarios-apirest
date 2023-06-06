package com.oalvarez.backend.usuario.apirest.services;

import com.oalvarez.backend.usuario.apirest.dto.UserDTO;
import com.oalvarez.backend.usuario.apirest.entity.User;

import java.util.List;

public interface IUserService {

    public List<UserDTO> findAll();

    public UserDTO findById(Long id);

    public UserDTO create(UserDTO user);

    public UserDTO update(UserDTO user);

    public UserDTO findByUuid(String uuid);

    public boolean existsByEmail(String email);

}
