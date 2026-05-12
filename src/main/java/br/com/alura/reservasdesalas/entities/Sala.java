package br.com.alura.reservasdesalas.entities;
import jakarta.persistence.*;

@Entity
@Table(name = "salas" , uniqueConstraints = @UniqueConstraint(columnNames = "nome"))
public class Sala {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //nome da sala
    @Column(nullable = false)
    private String nome;

    //capacidade da sala , deve ser maior que zero
    @Column(nullable = false)
    private int capacidade;

    //se a sala está ativa e pode receber reservas
    @Column(nullable = false)
    private boolean ativa;

    protected Sala() {
    }

    //Criar metodo publico recebendo os parametros e inicializando os atributos
    //se a capacidade for menor que igual a zero , lançar uma exceção
    public Sala(String nome , int capacidade , boolean ativa){
        this(null, nome, capacidade, ativa);
    }

    public Sala (Long id , String nome , int capacidade , boolean ativa){
        this.id = id;
        this.nome = nome;
        this.capacidade = capacidade;
        this.ativa = ativa;
        validarCapacidade(capacidade);
    }

    private void validarCapacidade (int capacidade){
        if (capacidade <= 0){
            throw new IllegalArgumentException("Capacidade deve ser maior que zero");
        }
    }
    //Criar metodo publico que retorna se a sala está ativa
    //se a sala está ativa , retornar true
    //se a sala não está ativa , retornar false
    public boolean estaAtiva(){
        return ativa;
    }

    //Criar metodo publico que retorna o id da sala
    public Long getId(){
        return id;
    }
    //Criar metodo publico que retorna o nome da sala
    public String getNome(){
        return nome;
    }
    //Criar metodo publico que retorna a capacidade da sala
    public int getCapacidade(){
        return capacidade;
    }


}
