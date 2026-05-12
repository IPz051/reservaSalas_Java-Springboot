package br.com.alura.reservasdesalas.dto.Sala;

import br.com.alura.reservasdesalas.entities.Sala;

public class SalaMapper {
    public static Sala toEntity(
            SalaRequestDTO dto) {

        return new Sala(
                dto.getNome(),
                dto.getCapacidade(),
                dto.isAtiva());
    }

    public static SalaResponseDTO toDTO(
            Sala sala) {

        return new SalaResponseDTO(
                sala.getId(),
                sala.getNome(),
                sala.getCapacidade(),
                sala.estaAtiva());
    }
}
