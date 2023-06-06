package com.oalvarez.backend.usuario.apirest.services;

import com.oalvarez.backend.usuario.apirest.dao.IUserDao;
import com.oalvarez.backend.usuario.apirest.dto.UserDTO;
import com.oalvarez.backend.usuario.apirest.entity.User;
import com.oalvarez.backend.usuario.apirest.mapper.UserRequestMapper;
import com.oalvarez.backend.usuario.apirest.mapper.UserResponseMapper;
import com.oalvarez.backend.usuario.apirest.mapper.UsersResponseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService, UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private IUserDao userDao;

    @Autowired
    private UserRequestMapper requestUserMapper;

    @Autowired
    private UserResponseMapper responseUserMapper;

    @Autowired
    private UsersResponseMapper responseUsersMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userDao.findByEmail(email);

        if(user == null){
            log.error("Error en el login: no existe el usuario '"+email+"' en el sistema!");
            throw new UsernameNotFoundException("Error en el login: no existe el usuario '"+email+"' en el sistema!");
        }
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map( role -> new SimpleGrantedAuthority(role.getNombre()))
                .peek(authority -> log.info("Role: " + authority.getAuthority()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(email, user.getPassword(), user.getEnabled(), true, true, true, authorities);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> findAll() {
        return responseUsersMapper.map((List<User>) userDao.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        User user = userDao.findById(id).orElse(null);
        return (user != null) ? responseUserMapper.map(user) : null;
    }

    @Override
    @Transactional
    public UserDTO create(UserDTO userDTO) {
        return responseUserMapper.map(userDao.save(requestUserMapper.map(userDTO)));
    }

    @Override
    @Transactional
    public UserDTO update(UserDTO userDTO) {
        User user;
        LocalDate today = LocalDate.now();
        user = requestUserMapper.map(userDTO);
        user.setModified(today);
        return responseUserMapper.map(userDao.save(user));
    }

    @Override
    public UserDTO findByUuid(String uuid) {
        User user = userDao.findByUuid(uuid);
        return (user != null) ? responseUserMapper.map(user) : null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userDao.existsByEmail(email);
    }

}
