package com.alcebiades.trilha.model.response;

import com.alcebiades.trilha.model.TipoLancamento;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author alcebiades
 */
@XmlRootElement
public class TipoLancamentoResponse {

    private List<TipoLancamento> listTipoLancamento;

    public TipoLancamentoResponse() {
        super();
    }

    public TipoLancamentoResponse(List<TipoLancamento> listTipoLancamento) {
        this.listTipoLancamento = listTipoLancamento;
    }

    public List<TipoLancamento> getListTipoLancamento() {
        if (this.listTipoLancamento == null) {
            this.listTipoLancamento = new ArrayList<>();
        }
        return listTipoLancamento;
    }

    public void setListTipoLancamento(List<TipoLancamento> listTipoLancamento) {
        if (this.listTipoLancamento == null) {
            this.listTipoLancamento = new ArrayList<>();
        }
        this.listTipoLancamento = listTipoLancamento;
    }
}
