package br.edu.ifsul.estoque.domain.repository;

import br.edu.ifsul.estoque.domain.model.Produto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {
}