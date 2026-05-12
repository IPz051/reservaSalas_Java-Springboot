package br.com.alura.reservasdesalas.dto.Sala;

public class SalaResponseDTO {
private Long id;

    private String nome;

    private int capacidade;

    private boolean ativa;

    public SalaResponseDTO(
            Long id,
            String nome,
            int capacidade,
            boolean ativa
    ) {
        this.id = id;
        this.nome = nome;
        this.capacidade = capacidade;
        this.ativa = ativa;
    }

    public Long getId() {
        return id;
    }

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
