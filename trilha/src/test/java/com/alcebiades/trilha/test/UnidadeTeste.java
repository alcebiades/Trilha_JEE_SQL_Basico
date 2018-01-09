package com.alcebiades.trilha.test;

import com.alcebiades.trilha.model.Lancamento;
import com.alcebiades.trilha.model.TipoLancamento;
import com.alcebiades.trilha.model.request.LancamentoFiltro;
import com.alcebiades.trilha.model.response.LancamentoResponse;
import com.alcebiades.trilha.model.response.TipoLancamentoResponse;
import com.alcebiades.trilha.service.LancamentoService;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import javax.inject.Inject;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author alcebiades
 */
@RunWith(Arquillian.class)
public class UnidadeTeste extends JunitBase {

    //<editor-fold defaultstate="collapsed" desc=" METODOS PARA INICIALIZAR O CONTAINER E O DATASOUCE ">
//    @CreateSwarm
//    public static Swarm newContainer() throws Exception {
//        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ JunitBase.createContainer ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        return JunitBase.createContainer();
//    }
    @Deployment()
    public static Archive createDeployment() {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ JunitBase.createDeployment ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        return JunitBase.createDeployment();
    }
    //</editor-fold>

    @Inject
    private LancamentoService service;

    @Test
    public void testObterTipoLancamento() throws Exception {
        final TipoLancamentoResponse tipoLancamento = service.consultarTipoLancamento();
        Assert.assertEquals("A quantidade de tipo lancamento esta diferente da esperada", 2, tipoLancamento.getListTipoLancamento().size());
        Assert.assertEquals("O id do tipo lancamento esta diferente do esperado", "1", tipoLancamento.getListTipoLancamento().get(0).getId().toString());
        Assert.assertEquals("A descricao do tipo lancamento esta diferente da esperada", "Entrada", tipoLancamento.getListTipoLancamento().get(0).getDescricao());
        Assert.assertEquals("O id do tipo lancamento esta diferente do esperado", "2", tipoLancamento.getListTipoLancamento().get(1).getId().toString());
        Assert.assertEquals("A descricao do tipo lancamento esta diferente da esperada", "Saída", tipoLancamento.getListTipoLancamento().get(1).getDescricao());
    }

    @Test
    public void testInserirTipoLancamento() throws Exception {
        final TipoLancamento tipoLancamento = new TipoLancamento();
        tipoLancamento.setId(null);
        tipoLancamento.setDescricao("Tipo Lancamento Teste");

        final TipoLancamento tipoLancamentoRet = service.salvarTipoLancamento(tipoLancamento);
        Assert.assertNotNull("O id do tipo lancamento esta diferente do esperado", tipoLancamentoRet.getId());
        Assert.assertEquals("A descricao do tipo lancamento esta diferente da esperada", "Tipo Lancamento Teste", tipoLancamento.getDescricao());
    }

    @Test
    public void testAtualizarTipoLancamento() throws Exception {
        final TipoLancamento tipoLancamento = new TipoLancamento();
        tipoLancamento.setId(1l);
        tipoLancamento.setDescricao("Tipo Lancamento Teste");

        final TipoLancamento tipoLancamentoRet = service.salvarTipoLancamento(tipoLancamento);

        Assert.assertEquals("O id do tipo lancamento esta diferente do esperado", "1", tipoLancamentoRet.getId().toString());
        Assert.assertEquals("A descricao do tipo lancamento esta diferente da esperada", "Tipo Lancamento Teste", tipoLancamentoRet.getDescricao());
    }

    @Test
    public void testSalvarLancamentoIncluirAtualizar() throws Exception {

        Lancamento lancamento = new Lancamento();

        Date dataAtual = new Date();
        lancamento.setData(dataAtual);
        lancamento.setDescricao("Recebimento de salário");
        lancamento.setValor(1000d);
        TipoLancamento tipoLancamento = new TipoLancamento();
        tipoLancamento.setId(1l);
        lancamento.setTipoLancamento(tipoLancamento);

        // Cria um lancamento
        lancamento = service.salvarLancamento(lancamento);

        LancamentoFiltro filtro = new LancamentoFiltro();
        filtro.setId(lancamento.getId());
        LancamentoResponse consultarLancamento = service.consultarLancamento(filtro);
        lancamento = consultarLancamento.getListLancamento().get(0);

        Assert.assertEquals("A descricao do lancamento esta diferente da esperada", "Recebimento de salário", lancamento.getDescricao());
        Assert.assertEquals("O valor do lancamento esta diferente do esperado", new Double(1000d), lancamento.getValor());
        Assert.assertEquals("A data do lancamento esta diferente da esperada", dataAtual, lancamento.getData());
        Assert.assertEquals("O tipo do lancamento esta diferente do esperado", tipoLancamento.getId(), lancamento.getTipoLancamento().getId());

        // Atualiza o lancamento criado anteriormente
        dataAtual = new Date();
        lancamento.setData(dataAtual);
        lancamento.setDescricao("Pagamento de conta");
        lancamento.setValor(100.37d);
        tipoLancamento = new TipoLancamento();
        tipoLancamento.setId(2l);
        lancamento.setTipoLancamento(tipoLancamento);

        lancamento = service.salvarLancamento(lancamento);

        filtro = new LancamentoFiltro();
        filtro.setId(lancamento.getId());
        consultarLancamento = service.consultarLancamento(filtro);
        lancamento = consultarLancamento.getListLancamento().get(0);

        Assert.assertEquals("A descricao do lancamento esta diferente da esperada", "Pagamento de conta", lancamento.getDescricao());
        Assert.assertEquals("O valor do lancamento esta diferente do esperado", new Double(100.37d), lancamento.getValor());
        Assert.assertEquals("A data do lancamento esta diferente da esperada", dataAtual, lancamento.getData());
        Assert.assertEquals("O tipo do lancamento esta diferente do esperado", tipoLancamento.getId(), lancamento.getTipoLancamento().getId());

        service.excluirLancamento(lancamento.getId());
        consultarLancamento = service.consultarLancamento(filtro);

        Assert.assertEquals("A quantidade de lancamentos esta diferente do esperado", 0, consultarLancamento.getListLancamento().size());
    }

    @Test
    public void testFiltrosLancamento() throws Exception {

        TipoLancamento tipoLancamento1 = new TipoLancamento();
        tipoLancamento1.setId(1l);

        TipoLancamento tipoLancamento2 = new TipoLancamento();
        tipoLancamento2.setId(2l);

        // Cria lancamento 1
        final Date dataAtual = new Date();

        Lancamento lancamento = new Lancamento();

        lancamento.setData(dataAtual);
        lancamento.setDescricao("teste 1");
        lancamento.setValor(10d);
        lancamento.setTipoLancamento(tipoLancamento1);

        service.salvarLancamento(lancamento);

        // Cria lancamento 2
        final Date data10dias = somarData10Dias(dataAtual);

        lancamento = new Lancamento();
        lancamento.setData(data10dias);
        lancamento.setDescricao("teste 2");
        lancamento.setValor(20d);
        lancamento.setTipoLancamento(tipoLancamento2);

        service.salvarLancamento(lancamento);

        // Cria lancamento 3
        final Date data20dias = somarData10Dias(data10dias);

        lancamento = new Lancamento();
        lancamento.setData(data20dias);
        lancamento.setDescricao("teste 3");
        lancamento.setValor(30d);
        lancamento.setTipoLancamento(tipoLancamento1);

        service.salvarLancamento(lancamento);

        // Cria lancamento 4
        final Date data30dias = somarData10Dias(data20dias);

        lancamento = new Lancamento();
        lancamento.setData(data30dias);
        lancamento.setDescricao("teste 4");
        lancamento.setValor(40d);
        lancamento.setTipoLancamento(tipoLancamento2);

        service.salvarLancamento(lancamento);

        // Cria lancamento 5
        final Date data40dias = somarData10Dias(data30dias);

        lancamento = new Lancamento();
        lancamento.setData(data40dias);
        lancamento.setDescricao("teste 5");
        lancamento.setValor(50d);
        lancamento.setTipoLancamento(tipoLancamento1);

        service.salvarLancamento(lancamento);

        LancamentoResponse consultarLancamento = service.consultarLancamento(null);
        Assert.assertEquals("A quantidade de lancamentos esta diferente do esperado", 5, consultarLancamento.getListLancamento().size());

        // Filtro por ID
        LancamentoFiltro filtro = new LancamentoFiltro();
        filtro.setId(4l);
        consultarLancamento = service.consultarLancamento(filtro);
        lancamento = consultarLancamento.getListLancamento().get(0);
        Assert.assertEquals("A data do lancamento esta diferente da esperada", data30dias, lancamento.getData());

        // Filtro por Data
        filtro = new LancamentoFiltro();
        filtro.setDataInicial(data10dias);
        filtro.setDataFinal(data30dias);
        consultarLancamento = service.consultarLancamento(filtro);
        Assert.assertEquals("A quantidade de lancamentos esta diferente do esperado", 3, consultarLancamento.getListLancamento().size());

        // Filtro por Valor
        filtro = new LancamentoFiltro();
        filtro.setValorInicial(27.50);
        filtro.setValorFinal(30.01);
        consultarLancamento = service.consultarLancamento(filtro);
        Assert.assertEquals("A quantidade de lancamentos esta diferente do esperado", 1, consultarLancamento.getListLancamento().size());

        // Filtro por Tipo Lancamento 2
        filtro = new LancamentoFiltro();
        filtro.setTipoLancamento(tipoLancamento2);
        consultarLancamento = service.consultarLancamento(filtro);
        Assert.assertEquals("A quantidade de lancamentos esta diferente do esperado", 2, consultarLancamento.getListLancamento().size());

        // Filtro por Tipo Lancamento 1
        filtro = new LancamentoFiltro();
        filtro.setTipoLancamento(tipoLancamento1);
        consultarLancamento = service.consultarLancamento(filtro);
        Assert.assertEquals("A quantidade de lancamentos esta diferente do esperado", 3, consultarLancamento.getListLancamento().size());
    }

    public Date somarData10Dias(final Date data) {
        Calendar calendar = new GregorianCalendar(new Locale("pt", "BR"));
        calendar.setTime(data);
        calendar.add(Calendar.DAY_OF_WEEK, 10);

        return calendar.getTime();
    }
}
