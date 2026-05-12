package br.com.alura.reservasdesalas.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

import br.com.alura.reservasdesalas.enums.ReservaStatus;

@Entity
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //usuario que fez a reserva
    // Lazy - só carrega quando for necessário
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id" , nullable = false)
    private Usuario usuario;

    //sala que foi reservada
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "sala_id" , nullable = false)
    private Sala sala;
    
    //incio da reserva
    @Column(nullable = false)
    private LocalDateTime inicio;
    
    //fim da reserva
    @Column(nullable = false)
    private LocalDateTime fim;

    //estado atual da reserva
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservaStatus status;
    
    protected Reserva() {
    }

    //construtor
    public Reserva(
        Long id,
        Sala sala,
        LocalDateTime inicio,
        LocalDateTime fim,
        ReservaStatus status
    ){
        validarSala(sala);
        validarPeriodo(inicio, fim);
        
        this.id = id;
        this.sala = sala;
        this.inicio = inicio;
        this.fim = fim;
        this.status = status;
        
    }
    //metodo privado que valida a sala, se a sala não estiver ativa, lançar uma exceção
    private void validarSala(Sala sala){
        if (!sala.estaAtiva()){
            throw new IllegalArgumentException("Sala não está ativa");
        }
    }
    //metodo privado que valida o período da reserva, se o inicio for posterior ao fim, lançar uma exceção
    private void validarPeriodo(LocalDateTime inicio, LocalDateTime fim){
        
        if (inicio == null || fim == null){
            throw new IllegalArgumentException("Datas não podem ser nulas");
        }
        if (!inicio.isBefore(fim)){
            throw new IllegalArgumentException("Inicio deve ser anterior a fim");
        }
    }

    public boolean conflitaCom(Reserva outraReserva){
        //reservas canceladas não conflitam com outras reservas
        if (status == ReservaStatus.CANCELADA || outraReserva.status == ReservaStatus.CANCELADA){
            return false;
        }
        //conflito só importa para a mesma sala
        if (!sala.getId().equals(outraReserva.getSala().getId())){
            return false;
        }

        //verificar se há conflito de tempo, se houver conflito de tempo, retornar true
        //se não houver conflito de tempo, retornar false
        return this.inicio.isBefore(outraReserva.getFim()) && outraReserva.getInicio().isBefore(this.fim);
    }

    //metodo publico que cancela a reserva, se a reserva não estiver ativa, lançar uma exceção
    //se a reserva estiver ativa, alterar o status para cancelada
    public void cancelar (){
        if (status != ReservaStatus.ATIVA){
            throw new IllegalArgumentException("Reserva já foi cancelada");
        }
        this.status = ReservaStatus.CANCELADA;
    }

    public boolean estaAtiva(){
        return status == ReservaStatus.ATIVA;
    }

    public ReservaStatus getStatus(){
        return status;
    }
    public Long getId() {
        return id;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Sala getSala(){
        return sala;
    }
    public LocalDateTime getInicio(){
        return inicio;
    }
    public LocalDateTime getFim(){
        return fim;
    }


}
