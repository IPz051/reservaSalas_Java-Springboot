package br.com.alura.reservasdesalas.controller;

import br.com.alura.reservasdesalas.dto.Usuario.UsuarioMapper;
import br.com.alura.reservasdesalas.dto.Usuario.UsuarioRequestDTO;
import br.com.alura.reservasdesalas.dto.Usuario.UsuarioResponseDTO;
import br.com.alura.reservasdesalas.entities.Usuario;
import br.com.alura.reservasdesalas.services.UsuarioService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(
            UsuarioService usuarioService
    ) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public UsuarioResponseDTO criar(
            @RequestBody UsuarioRequestDTO dto
    ) {

        Usuario usuario =
                UsuarioMapper.toEntity(dto);

        Usuario salvo =
                usuarioService.criar(usuario);

        return UsuarioMapper.toDTO(salvo);
    }
}
