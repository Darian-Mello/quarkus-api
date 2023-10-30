package br.edu.ifsul.estoque.rest;

import br.edu.ifsul.estoque.domain.model.Produto;
import br.edu.ifsul.estoque.domain.repository.ProdutoRepository;
import br.edu.ifsul.estoque.rest.dto.ProdutoRequest;
import br.edu.ifsul.estoque.rest.dto.violation.ViolationResponse;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.Set;

@Path("/produto")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdutoResource {
    private ProdutoRepository repository;
    private Validator validator;

    @Inject
    public ProdutoResource(ProdutoRepository repository, Validator validator){
        this.repository = repository;
        this.validator = validator;
    }

    @GET
    public Response getProdutos(){
        PanacheQuery<Produto> query = repository.findAll();
        return Response.ok(query.list()).build();
    }

    @GET
    @Path("{id}")
    public Response getProdutoById(@PathParam("id") Long id) {
        Produto produto = repository.findById(id);

        if (produto == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok(produto).build();
    }

    @POST
    @Transactional
    public Response createProduto(ProdutoRequest produtoRequest){
        Set<ConstraintViolation<ProdutoRequest>> violations = validator.validate(produtoRequest);
        if(!violations.isEmpty()){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ViolationResponse.listViolations(violations))
                    .build();

        }

        Produto produto = new Produto();
        produto.setNome(produtoRequest.getNome());
        produto.setDescricao(produtoRequest.getDescricao());
        produto.setValor(produtoRequest.getValor());
        repository.persist(produto);

        return Response
                .status(Response.Status.CREATED.getStatusCode())
                .entity(produto)
                .build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateProduto(@PathParam("id") Long id, ProdutoRequest produtoRequest){
        Produto produto = repository.findById(id);

        if(produto == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        Set<ConstraintViolation<ProdutoRequest>> violations = validator.validate(produtoRequest);
        if(!violations.isEmpty()){
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ViolationResponse.listViolations(violations))
                    .build();

        }

        produto.setNome(produtoRequest.getNome());
        produto.setDescricao(produtoRequest.getDescricao());
        produto.setValor(produtoRequest.getValor());

        return Response.noContent().build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteProduto(@PathParam("id") Long id){
        Produto produto = repository.findById(id);

        if(produto == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        repository.delete(produto);

        return Response.noContent().build();
    }
}
