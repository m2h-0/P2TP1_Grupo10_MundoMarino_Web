package com.grupo10.mundomarino.mapper;

import com.grupo10.mundomarino.dto.UsuarioDto;
import com.grupo10.mundomarino.entity.Usuario;

public class UsuarioMapper {
    public static UsuarioDto toDto(Usuario u) {
        if (u == null) return null;
        return new UsuarioDto(u.getId(), u.getUsername(), u.getRol());
    }
}
