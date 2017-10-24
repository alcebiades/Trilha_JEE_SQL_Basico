package com.alcebiades.trilha.endpoints;

import com.alcebiades.trilha.model.Lancamento;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Alcebiades
 */
//@ApplicationScoped
@Path("/api")
public interface LancamentoEndpoint {

    @Path("/consultarLancamento")
    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    Response consultarLancamento(final Lancamento filtro) throws Exception;
}
