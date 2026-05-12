package br.com.alura.reservasdesalas.services;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.alura.reservasdesalas.entities.Sala;
import br.com.alura.reservasdesalas.exception.ResourceNotFoundException;
import br.com.alura.reservasdesalas.repository.SalaRepository;

@Service
public class SalaService {
    //  atributo privado do tipo SalaRepository, para acessar o banco de dados
    private final SalaRepository salaRepository;

    //  construtor que recebe um objeto do tipo SalaRepository
    public SalaService(SalaRepository salaRepository) {
        this.salaRepository = salaRepository;
    }

    //  metodo publico que retorna uma lista de salas
    // se não houver salas , retornar uma lista vazia
    public List<Sala> findAll() {
        return salaRepository.findAll();
    }

    // metodo publico que retorna uma sala por id
    // se não houver sala com esse id, retornar uma exceção
    // ResourceNotFoundException
    public Sala buscarPorId(Long id) {
        return salaRepository.findById(id)
                .orElseThrow(() -> 
                        new ResourceNotFoundException("Sala não encontrada"));
    }

    //metodo publico que cria uma nova sala
    public Sala criar(Sala sala) {
        return salaRepository.save(sala);
    }
    
    //metodo publico que deleta uma sala por id
    public void deletar (Long id){
        Sala sala = buscarPorId(id);
        salaRepository.delete(sala);
    }
}
