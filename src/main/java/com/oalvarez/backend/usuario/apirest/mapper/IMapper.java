package com.oalvarez.backend.usuario.apirest.mapper;

@FunctionalInterface
public interface IMapper <Input, Output>{

    Output map(Input request);
}
