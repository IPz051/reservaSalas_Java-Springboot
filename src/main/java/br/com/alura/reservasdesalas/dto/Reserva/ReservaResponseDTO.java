package br.com.alura.reservasdesalas.dto.Reserva;

import java.time.LocalDateTime;

import br.com.alura.reservasdesalas.enums.ReservaStatus;

public class ReservaResponseDTO {
    private Long id;
    private String sala;
    private String usuario;
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private ReservaStatus status;

    public ReservaResponseDTO(
        Long id,
            String sala,
            String usuario,
            LocalDateTime inicio,
            LocalDateTime fim,
            ReservaStatus status
    ){
        this.id = id;
        this.sala = sala;
        this.usuario = usuario;
        this.inicio = inicio;
        this.fim = fim;
        this.status = status;
    }

    public Long getId() {
        return id;
    }
    public String getSala() {
        return sala;
    }
    public String getUsuario() {
        return usuario;
    }

}
