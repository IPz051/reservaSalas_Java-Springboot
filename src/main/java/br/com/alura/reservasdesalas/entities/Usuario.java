package br.com.alura.reservasdesalas.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    protected Usuario() {
    }

    public Usuario(String nome, String email) {
        this(null, nome, email);
    }

    public Usuario(Long id, String nome, String email) {
        this.id = id;
        this.nome = nome;
        this.email = email;

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email deve ser informado");
        }
    }
    //Criar metodo publico que atualiza o usuario, utilizando os parametros de entrada
    public void atualizar(String nome, String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email deve ser informado");
        }
        this.nome = nome;
        this.email = email;
    }
    //Criar metodo publico que retorna o id do usuario
    public Long getId(){
        return id;
    }
    //Criar metodo publico que retorna o nome do usuario
    public String getNome(){
        return nome;
    }
    //Criar metodo publico que retorna o email do usuario
    public String getEmail(){
        return email;
    }
}
