package br.com.springmc.services;

import br.com.springmc.domain.Categoria;
import br.com.springmc.domain.Produto;
import br.com.springmc.domain.QCategoria;
import br.com.springmc.domain.QProduto;
import br.com.springmc.repositories.CategoriaRepository;
import br.com.springmc.services.exception.ObjectNotFoundException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.support.QueryBase;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria findById(Integer id){
        Optional<Categoria> categoria = categoriaRepository.findById(id);
        return categoria.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
    }

    public List<Categoria> teste(){
        JPAQuery query = new JPAQuery(entityManager);


        Produto produto = new Produto(1, "Computador", 2000.00);
        Categoria categoria = new Categoria("Informática");

        BooleanExpression booleanExpression = QProduto.produto.categorias.any().nome.eq("Informática");


//        booleanBuilder.and(QProduto.produto.eq("Computador"));

        QProduto qProduto = QProduto.produto;
        QCategoria qCategoria = QCategoria.categoria;
//        List<Produto> produtos = query.from(qProduto).where(qProduto.categorias.contains(QCategoria.categoria.nome.eq("Informática"))).list(qProduto);


        BooleanBuilder builder = new BooleanBuilder();
        builder.and(qProduto.nome.eq("Computador"));
        builder.and(qCategoria.nome.eq("Informática"));

        query.from(qProduto)
                .innerJoin(qCategoria.categoria, qCategoria)
                .where(qCategoria.nome.in("Informática", "USA"));


        QCategoria.categoria.produtos.isNotEmpty();
        return (List<Categoria>) categoriaRepository.findAll(builder);
    }
}
