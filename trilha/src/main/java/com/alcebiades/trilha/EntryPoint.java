package com.alcebiades.trilha;

//import com.alcebiades.trilha.service.LancamentoService;
//import java.util.List;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.spec.JavaArchive;
//import org.wildfly.swarm.spi.api.ArtifactLookup;
//import org.wildfly.swarm.spi.api.JARArchive;

/**
 *
 * @author Alcebiades
 */
public class EntryPoint {

//    public static void main(final String[] args) throws Exception {
//        
//        
//        final Container container = new Container();
//        container.start();
//        final JARArchive ejbArchive = ShrinkWrap.create(JARArchive.class);
//        ejbArchive.addClass(LancamentoService.class);
//        /*
//            Merge in the dependencies that the warm build plugin included in the Swarm UberJar
//            into an EJB UberJar. This gives us a self contained EJB JAR file that Swarm can then
//            deploy and run.
//         */
//        final List<JavaArchive> artifacts = ArtifactLookup.get().allArtifacts(new String[]{"org.wildfly.swarm"});
//        for (final JavaArchive javaArchive : artifacts) {
//            ejbArchive.merge(javaArchive);
//        }
//        // Deploy your app
//        container.deploy(ejbArchive);
//    }
}
