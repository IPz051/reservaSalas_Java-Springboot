package br.com.alura.reservasdesalas.dto.Sala;

public class SalaRequestDTO {
private String nome;

    private int capacidade;

    private boolean ativa;

    public String getNome() {
        return nome;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public boolean isAtiva() {
        return ativa;
    }
}
