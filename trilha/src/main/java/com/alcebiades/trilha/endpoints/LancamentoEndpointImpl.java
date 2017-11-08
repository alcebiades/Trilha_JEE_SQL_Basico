package com.alcebiades.trilha.endpoints;

import com.alcebiades.trilha.model.Lancamento;
import com.alcebiades.trilha.model.response.LancamentoResponse;
import javax.ws.rs.core.Response;
import com.alcebiades.trilha.service.LancamentoService;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class LancamentoEndpointImpl implements LancamentoEndpoint {

    @Inject
    private LancamentoService contaService;

    @Override
    public Response consultarLancamento(final Lancamento filtro) throws Exception {
        //        return Response.ok("Hello from WildFly Swarm!").build();
        List<Lancamento> ret = contaService.obterListLancemanto(null);

//        final List<Lancamento> ret = new ArrayList<>();
//
//        Lancamento lancamento = new Lancamento();
//        lancamento.setId(filtro.getId());
//        lancamento.setNome("Compra do mês");
//        lancamento.setValor(342d);
//        lancamento.setData(new Date());
//
//        TipoLancamento tipoLancamento = new TipoLancamento();
//        tipoLancamento.setId(1l);
//        tipoLancamento.setDescricao("Alimentação");
//
//        lancamento.setTipoLancamento(tipoLancamento);
//
//        ret.add(lancamento);

        return Response.ok(new LancamentoResponse(ret)).build();
    }
}
