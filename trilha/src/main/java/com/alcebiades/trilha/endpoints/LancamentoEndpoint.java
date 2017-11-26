package com.alcebiades.trilha.endpoints;

import com.alcebiades.trilha.model.Lancamento;
import com.alcebiades.trilha.model.response.LancamentoResponse;
import com.alcebiades.trilha.service.LancamentoService;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("/api")
public class LancamentoEndpoint {

    @Inject
    private LancamentoService contaService;

    @POST
    @Path("/consultarLancamento")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response consultarLancamento(final Lancamento filtro) throws Exception {

        final List<Lancamento> ret = contaService.consultarLancamento(null);

        return Response.ok(new LancamentoResponse(ret)).build();
    }

//    @GET
//    @Produces("text/plain")
//    public String get() throws NamingException, SQLException {
//        Context ctx = new InitialContext();
//        DataSource ds = (DataSource) ctx.lookup("jboss/datasources/TrilhaDS");
//        try (Connection conn = ds.getConnection()) {
//            return "Howdy using driver: " + conn.getMetaData().getDriverName();
//        }
//    }
}
