package br.com.alura.reservasdesalas.controller;

import br.com.alura.reservasdesalas.repository.ReservaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.alura.reservasdesalas.dto.Reserva.ReservaMapper;
import br.com.alura.reservasdesalas.dto.Reserva.ReservaRequestDTO;
import br.com.alura.reservasdesalas.dto.Reserva.ReservaResponseDTO;
import br.com.alura.reservasdesalas.entities.Reserva;
import br.com.alura.reservasdesalas.services.ReservaService;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/reservas")
public class ReservaController {

    private final ReservaRepository reservaRepository;
    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService, ReservaRepository reservaRepository) {
        this.reservaService = reservaService;
        this.reservaRepository = reservaRepository;
    }

    @PostMapping
    public ReservaResponseDTO criarReserva(
            @RequestBody ReservaRequestDTO dto
    ) {
        Reserva reserva =
                reservaService.criarReserva(dto);

        return ReservaMapper.toDTO(reserva);
    }

    @PatchMapping("/{id}/cancelar")
    public void cancelarReserva(
            @PathVariable Long id) {
        reservaService.cancelarReserva(id);
    }

    @GetMapping
    public Page<Reserva> listar(
            Pageable pageable) {
        return reservaRepository.findAll(pageable);
    }
}
