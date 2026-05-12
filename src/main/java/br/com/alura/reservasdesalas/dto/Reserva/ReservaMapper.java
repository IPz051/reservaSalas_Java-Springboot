package br.com.alura.reservasdesalas.dto.Reserva;

import br.com.alura.reservasdesalas.entities.Reserva;

public class ReservaMapper {

    public static ReservaResponseDTO toDTO(
            Reserva reserva
    ) {

        return new ReservaResponseDTO(
                reserva.getId(),
                reserva.getSala().getNome(),
                reserva.getUsuario().getNome(),
                reserva.getInicio(),
                reserva.getFim(),
                reserva.getStatus()
        );
    }
}
