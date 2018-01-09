package com.alcebiades.trilha.endpoints;

import com.alcebiades.trilha.exceptions.ApplicationException;
import com.alcebiades.trilha.exceptions.Mensagem;
import com.alcebiades.trilha.model.Lancamento;
import com.alcebiades.trilha.model.TipoLancamento;
import com.alcebiades.trilha.model.request.LancamentoFiltro;
import com.alcebiades.trilha.model.response.LancamentoResponse;
import com.alcebiades.trilha.model.response.TipoLancamentoResponse;
import com.alcebiades.trilha.service.LancamentoService;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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

    @GET
    @Path("/consultarTipoLancamento")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response consultarLancamento() throws Exception {
        try {
            final TipoLancamentoResponse consultarTipoLancamento = contaService.consultarTipoLancamento();
            return Response.ok(consultarTipoLancamento).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e)).build();
        }
    }

    @POST
    @Path("/salvarTipoLancamento")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response consultarLancamento(final TipoLancamento tipoLancamento) throws Exception {
        try {
            final TipoLancamento salvarTipoLancamento = contaService.salvarTipoLancamento(tipoLancamento);
            return Response.ok(salvarTipoLancamento).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e)).build();
        }
    }

    @POST
    @Path("/salvarLancamento")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response consultarLancamento(final Lancamento lancamento) throws Exception {
        try {
            final Lancamento salvarLancamento = contaService.salvarLancamento(lancamento);
            return Response.ok(salvarLancamento).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e)).build();
        }
    }

    @POST
    @Path("/consultarLancamento")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response consultarLancamento(final LancamentoFiltro filtro) {
        try {
            final LancamentoResponse consultarLancamento = contaService.consultarLancamento(filtro);
            return Response.ok(consultarLancamento).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e)).build();
        }
    }

    @POST
    @Path("/consultarLancamento")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response consultarLancamento(final Long idLancamento) {
        try {
            final boolean excluirLancamento = contaService.excluirLancamento(idLancamento);
            if (excluirLancamento) {
                return Response.ok(new Mensagem("Lançamento excluido com sucesso!")).build();
            } else {
                return Response.ok(new Mensagem("Falha ao excluir o lançamento " + idLancamento + "!")).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ApplicationException(e)).build();
        }
    }
}
