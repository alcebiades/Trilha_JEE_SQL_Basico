package com.alcebiades.trilha.service;

import com.alcebiades.trilha.model.Lancamento;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Alcebiades
 */
@Local
public interface LancamentoService {

    List<Lancamento> obterListLancemanto(Lancamento filtro) throws Exception;
}
