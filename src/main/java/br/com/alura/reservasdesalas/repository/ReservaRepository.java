package br.com.alura.reservasdesalas.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



import br.com.alura.reservasdesalas.entities.Reserva;
import br.com.alura.reservasdesalas.enums.ReservaStatus;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    //Criar metodo publico que retorna uma lista de reservas que conflitem com a reserva a ser feita
    //se houver conflito , retornar uma lista não vazia
    //se não houver conflito , retornar uma lista vazia
     @Query("""
        SELECT r
        FROM Reserva r
        WHERE r.sala.id = :salaId
        AND r.status = :status
        AND r.inicio < :fim
        AND :inicio < r.fim
    """)
    List<Reserva> buscarConflitos(
            @Param("salaId") Long salaId,
            @Param("inicio") LocalDateTime inicio,
            @Param("fim") LocalDateTime fim,
            @Param("status") ReservaStatus status
    );
}
