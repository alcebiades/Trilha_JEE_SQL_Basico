package com.alcebiades.trilha;

import static com.arjuna.ats.internal.jdbc.recovery.JDBCXARecovery.PASSWORD;
import static com.sun.xml.internal.ws.model.RuntimeModeler.PORT;
import java.io.File;
import static javax.ws.rs.Priorities.USER;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.wildfly.swarm.Swarm;
import org.wildfly.swarm.config.datasources.DataSource;
import org.wildfly.swarm.config.datasources.DataSourceConsumer;
import org.wildfly.swarm.datasources.DatasourceArchive;
import org.wildfly.swarm.undertow.WARArchive;

/**
 *
 * @author Alcebiades
 */
public class EntryPoint {

    public static void main(String args[]) throws Exception {

        Swarm container = new Swarm(args);
        container.start();

        container.deploy(Swarm.artifact("org.hsqldb.jdbc.JDBCDriver", "hsqldb"));

        // Cria o dataSource
        DatasourceArchive dsArchive = ShrinkWrap.create(DatasourceArchive.class);

        dsArchive.dataSource("dataSource", (DataSourceConsumer) (DataSource ds) -> {

            ds.connectionUrl("jdbc:hsqldb:file:trilhajeedb");
            ds.driverName("hsqldb");
            ds.userName("SA");
            ds.password("");
        });

        // Faz deploy no dataSource
        container.deploy(dsArchive);

        WARArchive deployment = ShrinkWrap.createFromZipFile(WARArchive.class, new File("target/executiveserver.war"));

        ClassLoader classLoader = EntryPoint.class.getClassLoader();
        // Configura o CDI
        deployment.addAsWebInfResource(classLoader.getResource("beans.xml"), "beans.xml");
        deployment.addPackages(true, Package.getPackage("com.alcebiades.trilha"));
        deployment.addAllDependencies();

        container.deploy(deployment);
    }
}
