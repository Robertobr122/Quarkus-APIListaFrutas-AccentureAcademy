package acc.br;

import java.util.List;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import acc.br.model.Fruta;

@Path("/frutas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FrutasResource {

    @GET
    public List<Fruta> list(@QueryParam("id") Long id) {
        if (id != null) {
            return Fruta.findByIdOptional(id)
                        .map(fruta -> List.of((Fruta) fruta))
                        .orElseThrow(() -> new NotFoundException("Fruta não encontrada"));
        }
        return Fruta.listAll().stream().map(fruta -> (Fruta) fruta).toList();
    }

    @POST
    @Transactional
    public Response novaFruta(@Valid Fruta fruta) {
        fruta.persist();
        return Response.status(Response.Status.CREATED).entity(fruta).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response atualizaFruta(@PathParam("id") Long id, @Valid Fruta frutaAtualizada) {
        Fruta frutaExistente = Fruta.findById(id);
        if (frutaExistente == null) {
            throw new NotFoundException("Fruta não encontrada");
        }

        frutaExistente.nome = frutaAtualizada.nome;
        frutaExistente.qtd = frutaAtualizada.qtd;
        frutaExistente.persist();

        return Response.ok(frutaExistente).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response removeFruta(@PathParam("id") Long id) {
        boolean deletado = Fruta.deleteById(id);
        if (!deletado) {
            throw new NotFoundException("Fruta não encontrada");
        }
        return Response.noContent().build();
    }
}

