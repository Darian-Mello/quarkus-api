package br.edu.ifsul.estoque.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProdutoRequest {

    @NotBlank(message = "O campo 'nome' não pode ser vazio.")
    @Size(min = 3, max = 255, message = "O campo 'nome' deve ter entre 3 e 255 caracteres.")
    private String nome;

    private String descricao;

    @NotNull(message = "O campo 'valor' não pode ser nulo.")
    private Double valor;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
