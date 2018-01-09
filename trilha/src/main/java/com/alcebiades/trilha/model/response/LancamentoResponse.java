package com.alcebiades.trilha.model.response;

import com.alcebiades.trilha.model.Lancamento;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Alcebiades
 */
@XmlRootElement
public class LancamentoResponse {

    private List<Lancamento> listLancamento;

    public LancamentoResponse() {
    }

    public LancamentoResponse(List<Lancamento> listLancamento) {
        this.listLancamento = listLancamento;
    }

    public List<Lancamento> getListLancamento() {
        if (listLancamento == null) {
            listLancamento = new ArrayList<>();
        }
        return listLancamento;
    }

    public void setListLancamento(List<Lancamento> listLancamento) {
        if (listLancamento == null) {
            listLancamento = new ArrayList<>();
        }
        this.listLancamento = listLancamento;
    }
}
