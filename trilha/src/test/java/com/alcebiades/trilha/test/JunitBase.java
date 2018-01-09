package com.alcebiades.trilha.test;

import com.alcebiades.trilha.Conexao;
import com.alcebiades.trilha.endpoints.LancamentoEndpoint;
import com.alcebiades.trilha.model.Lancamento;
import com.alcebiades.trilha.model.request.LancamentoFiltro;
import com.alcebiades.trilha.model.response.LancamentoResponse;
import com.alcebiades.trilha.service.LancamentoService;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import java.io.File;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.wildfly.swarm.undertow.WARArchive;

/**
 *
 * @author alcebiades
 */
public abstract class JunitBase {

    public static final String PATH_API = "http://localhost:8080/trilhajee/api";

    public static Archive createDeployment() {

        File[] files = Maven.resolver()
                .loadPomFromFile("pom.xml")
                .importCompileAndRuntimeDependencies()
                .importTestDependencies()
                .resolve().withTransitivity().asFile();

        return ShrinkWrap.create(WARArchive.class, "test.war")
                .addAsLibrary(files[0])
                .addPackage(Conexao.class.getPackage())
                .addPackage(LancamentoEndpoint.class.getPackage())
                .addPackage(Lancamento.class.getPackage())
                .addPackage(LancamentoResponse.class.getPackage())
                .addPackage(LancamentoFiltro.class.getPackage())
                .addPackage(LancamentoService.class.getPackage())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsWebResource("project-defaults.yml");
//                        .addPackage(RepositoryManager.class.getPackage()).addAsResource("META-INF/persistence.xml")
    }

    public String POST(Object objeto, String url) throws Exception {

        String json = given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(objeto).
                expect().
                when().
                post(url).asString();

        System.out.println("=====================================================================");
        System.out.println(json);
        System.out.println("=====================================================================");

        return json;
    }

    public String GET(String url) throws Exception {

        String json = given().
                accept(ContentType.JSON).
                when().
                get(url).asString();

        System.out.println("=====================================================================");
        System.out.println(json);
        System.out.println("=====================================================================");

        return json;
    }
}
