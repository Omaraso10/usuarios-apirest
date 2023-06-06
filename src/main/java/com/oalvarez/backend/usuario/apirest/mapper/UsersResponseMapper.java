package com.oalvarez.backend.usuario.apirest.mapper;

import com.oalvarez.backend.usuario.apirest.dto.PhoneDTO;
import com.oalvarez.backend.usuario.apirest.dto.UserDTO;
import com.oalvarez.backend.usuario.apirest.entity.Phone;
import com.oalvarez.backend.usuario.apirest.entity.User;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsersResponseMapper implements IMapper<List<User>, List<UserDTO>>{

    private final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public List<UserDTO> map(List<User> users) {
        return getUsers(users);
    }

    private List<UserDTO> getUsers(List<User> users){
        return users.stream()
                .map(this::getUser)
                .collect(Collectors.toList());
    }

    private UserDTO getUser(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setUuid(user.getUuid());
        userDTO.setPassword(user.getPassword());
        userDTO.setPhones(getPhones(user.getPhones()));
        userDTO.setCreated(user.getCreated().format(FORMAT));
        userDTO.setModified(user.getModified().format(FORMAT));
        userDTO.setLastLogin(user.getLastLogin().format(FORMAT));
        userDTO.setIsactive(user.getEnabled());
        return userDTO;
    }

    private List<PhoneDTO> getPhones(List<Phone> phones){
        return phones.stream()
                .map(this::getPhone)
                .collect(Collectors.toList());
    }

    private PhoneDTO getPhone(Phone phone){
        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setNumber(String.valueOf(phone.getNumber()));
        phoneDTO.setCityCode(phone.getCityCode());
        phoneDTO.setCountryCode(phone.getCountryCode());
        return phoneDTO;
    }
}
