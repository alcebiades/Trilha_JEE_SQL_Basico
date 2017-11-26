package com.alcebiades.trilha.service;

import com.alcebiades.trilha.Conexao;
import com.alcebiades.trilha.model.Lancamento;
import com.alcebiades.trilha.model.TipoLancamento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author alcebiades
 */
@Stateless
public class LancamentoService {

    @Inject
    private Conexao con;

    public LancamentoService() {
    }

    public LancamentoService(Conexao con) {
        this.con = con;
    }

    public List<Lancamento> consultarLancamento(final Lancamento filtro) throws Exception {

        try (Connection conn = con.getConnection()) {

            final List<Lancamento> ret = new ArrayList<>();

            Lancamento lancamento = new Lancamento();
            lancamento.setId(1l);
            lancamento.setDescricao("Howdy using driver: " + conn.getMetaData().getDriverName());
            lancamento.setValor(342d);
            lancamento.setData(new Date());

            TipoLancamento tipoLancamento = new TipoLancamento();
            tipoLancamento.setId(1l);
            tipoLancamento.setDescricao("Howdy using driver: ");

            lancamento.setTipoLancamento(tipoLancamento);

            ret.add(lancamento);

            return ret;
        }
    }

    public Lancamento salvarLancamento(final Lancamento lancamento) throws Exception {

        try {

            if (lancamento.getId() == null || lancamento.getId() < 0) {
                inserirLancamento(lancamento);
            } else {
                atualizarLancamento(lancamento);
            }

        } catch (SQLException e) {
            throw e;
        }

        return lancamento;
    }

    private Lancamento inserirLancamento(final Lancamento lancamento) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = con.getConnection().prepareStatement("INSERT INTO lancamento (nome, data, valor, idTipoLancamento) VALUES (?,?,?,?)");
            ps.setString(1, lancamento.getDescricao());
            ps.setDate(2, (java.sql.Date) lancamento.getData());
            ps.setDouble(3, lancamento.getValor());
            ps.setLong(4, lancamento.getTipoLancamento().getId());

            ps.executeUpdate();

            rs = con.getConnection().createStatement().executeQuery("SELECT currval('regra_id_seq') as id");

            if (rs.next()) {
                lancamento.setId(rs.getLong("id"));
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            con.close(null, rs, ps);
        }

        return lancamento;
    }

    private Lancamento atualizarLancamento(final Lancamento lancamento) throws Exception {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            ps = con.getConnection().prepareStatement("INSERT INTO lancamento (nome, data, valor, idTipoLancamento) VALUES (?,?,?,?)");
            ps.setString(1, lancamento.getDescricao());
            ps.setDate(2, (java.sql.Date) lancamento.getData());
            ps.setDouble(3, lancamento.getValor());
            ps.setLong(4, lancamento.getTipoLancamento().getId());

            ps.executeUpdate();

            rs = con.getConnection().createStatement().executeQuery("SELECT currval('regra_id_seq') as id");

            if (rs.next()) {
                lancamento.setId(rs.getLong("id"));
            }

        } catch (SQLException e) {
            throw e;
        } finally {
            con.close(null, rs, ps);
        }

        return lancamento;
    }
}
