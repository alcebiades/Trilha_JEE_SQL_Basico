package com.alcebiades.trilha.service;

import com.alcebiades.trilha.model.Lancamento;
import com.alcebiades.trilha.model.TipoLancamento;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.transaction.Transactional;

/**
 *
 * @author Alcebiades
 */
@Stateless
public class LancamentoServiceImpl implements LancamentoService {

    @Override
    public List<Lancamento> obterListLancemanto(Lancamento filtro) throws Exception {
       
        final List<Lancamento> ret = new ArrayList<>();

        Lancamento lancamento = new Lancamento();
        lancamento.setId(1l);
        lancamento.setNome("Compra do mês");
        lancamento.setValor(342d);
        lancamento.setData(new Date());
        
        TipoLancamento tipoLancamento=new TipoLancamento();
        tipoLancamento.setId(1l);
        tipoLancamento.setDescricao("Alimentação");
        
        lancamento.setTipoLancamento(tipoLancamento);
        
        ret.add(lancamento);

        return ret;
    }
}
