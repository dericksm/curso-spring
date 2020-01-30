package br.com.springmc.repositories;

import br.com.springmc.domain.Categoria;
import br.com.springmc.domain.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {


}
