package br.com.alura.reservasdesalas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.reservasdesalas.entities.Sala;

public interface SalaRepository extends JpaRepository<Sala, Long> {

}
