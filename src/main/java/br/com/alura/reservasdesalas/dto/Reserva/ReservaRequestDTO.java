package br.com.alura.reservasdesalas.dto.Reserva;

import java.time.LocalDateTime;

public class ReservaRequestDTO {

    private Long salaId;
    private Long usuarioId;
    private String nome;
    private LocalDateTime inicio;
    private LocalDateTime fim;

    public Long getSalaId() {
        return salaId;
    }
    public Long getUsuarioId() {
        return usuarioId;
    }
    public String getNome() {
        return nome;
    }
    public LocalDateTime getInicio() {
        return inicio;
    }
    public LocalDateTime getFim() {
        return fim;
    }
}
