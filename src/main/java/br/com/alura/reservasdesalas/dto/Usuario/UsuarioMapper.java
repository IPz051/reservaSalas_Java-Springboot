package br.com.alura.reservasdesalas.dto.Usuario;

import br.com.alura.reservasdesalas.entities.Usuario;

public class UsuarioMapper {
    public static Usuario toEntity(
            UsuarioRequestDTO dto) {

        return new Usuario(
                dto.getNome(),
                dto.getEmail());
    }

    public static UsuarioResponseDTO toDTO(
            Usuario usuario) {

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail());
    }
}
