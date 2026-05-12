package br.com.alura.reservasdesalas.controller;

import org.springframework.web.bind.annotation.*;

import br.com.alura.reservasdesalas.dto.Sala.SalaMapper;
import br.com.alura.reservasdesalas.dto.Sala.SalaRequestDTO;
import br.com.alura.reservasdesalas.dto.Sala.SalaResponseDTO;
import br.com.alura.reservasdesalas.entities.Sala;
import br.com.alura.reservasdesalas.services.SalaService;

@RestController
@RequestMapping("/api/v1/salas")
public class SalaController {

    // Injetar a dependencia SalaService
    private final SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    // metodo publico que retorna uma lista de salas, utilizando o dto
    // e retornando o dto de resposta
    // utilizando o mapper
    @GetMapping("/{id}")
    public SalaResponseDTO buscar(
            @PathVariable Long id) {
        Sala sala = salaService.buscarPorId(id);
        return SalaMapper.toDTO(sala);
    }

    // metodo publico que retorna uma sala por id
    @GetMapping("/{id}")
    public Sala buscarPorId(@PathVariable Long id) {
        return salaService.buscarPorId(id);
    }

    // metodo publico que cria uma nova sala, utilizando o dto
    // e retornando o dto de resposta
    // utilizando o mapper
    @PostMapping
    public SalaResponseDTO criar(
            @RequestBody SalaRequestDTO dto) {
        Sala sala = SalaMapper.toEntity(dto);
        Sala salaSalva = salaService.criar(sala);
        return SalaMapper.toDTO(salaSalva);
    }

    // metodo publico que delet uma sala por id
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        salaService.deletar(id);
    }
}
