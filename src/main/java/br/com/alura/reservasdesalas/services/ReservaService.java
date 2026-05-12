package br.com.alura.reservasdesalas.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.alura.reservasdesalas.dto.Reserva.ReservaRequestDTO;
import br.com.alura.reservasdesalas.entities.Reserva;
import br.com.alura.reservasdesalas.enums.ReservaStatus;
import br.com.alura.reservasdesalas.repository.ReservaRepository;
import br.com.alura.reservasdesalas.repository.SalaRepository;
import br.com.alura.reservasdesalas.repository.UsuarioRepository;
import jakarta.transaction.Transactional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final SalaRepository salaRepository;
    private final UsuarioRepository usuarioRepository;

    public ReservaService(
        ReservaRepository reservaRepository,
        SalaRepository salaRepository,
        UsuarioRepository usuarioRepository
    ) {
        this.reservaRepository = reservaRepository;
        this.salaRepository = salaRepository;
        this.usuarioRepository = usuarioRepository;
    }
    //
    @Transactional
    public Reserva criarReserva(Reserva reservaNova){
        Boolean possuiConflito = !reservaRepository.buscarConflitos(
            reservaNova.getSala().getId(),
            reservaNova.getInicio(),
            reservaNova.getFim(),
            ReservaStatus.ATIVA
        ).isEmpty();
        if (possuiConflito){
            throw new IllegalArgumentException("Sala já está reservada neste período");
        }
        return reservaRepository.save(reservaNova);
    }

    @Transactional
    public Reserva criarReserva(ReservaRequestDTO dto) {
        var sala = salaRepository.findById(dto.getSalaId())
            .orElseThrow(() -> new IllegalArgumentException("Sala não encontrada"));
        var usuario = usuarioRepository.findById(dto.getUsuarioId())
            .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Reserva reservaNova = new Reserva(
            null,
            sala,
            dto.getInicio(),
            dto.getFim(),
            ReservaStatus.ATIVA
        );
        reservaNova.setUsuario(usuario);

        return criarReserva(reservaNova);
    }

    //metodo privado que valida o conflito de horário
    //se houver conflito, lançar uma exceção
    public void validarConflito(Reserva reservaNova){
        List<Reserva> reservas = reservaRepository.buscarConflitos(
            reservaNova.getSala().getId(),
            reservaNova.getInicio(),
            reservaNova.getFim(),
            ReservaStatus.ATIVA
        );
        Boolean temConflito = !reservas.isEmpty();
        if (temConflito){
            throw new IllegalArgumentException("Sala já está reservada neste período");
        }
    }
    //metodo que busca uma reserva por id
    //se a reserva não for encontrada, lançar uma exceção
    public Reserva buscarPorId(Long id){
        return reservaRepository.findById(id).
                orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada"));
    }

    //metodo que cancela uma reserva
    //se a reserva não for encontrada, lançar uma exceção
    public void cancelarReserva(Long id){
        Reserva reserva = buscarPorId(id);
        reserva.cancelar();
        reservaRepository.save(reserva);
    }
}
