package cn.tao.resteasy_netty;

import org.jboss.resteasy.spi.ResteasyDeployment;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.ws.rs.ext.Provider;
import java.util.Collection;

@Component
public class NettyService  {
    private String rootResourcePath = "/resteasy";

    private int port = 1234;

    private int ioWorkerCount = Runtime.getRuntime().availableProcessors() * 4;

    private int executorThreadCount = 16;

    private int maxRequestSize = 10 * 1024 * 1024;

    private ConfigurableNettyJaxrsServer nettyServer;

    private ResteasyDeployment deployment = new ResteasyDeployment();

    public void start() {
    ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        nettyServer = new ConfigurableNettyJaxrsServer();
        Collection<Object> controllers = applicationContext.getBeansWithAnnotation(Controller.class).values();
        Collection<Object> providers = applicationContext.getBeansWithAnnotation(Provider.class).values();

        this.addProvider(providers);
        this.addResources(controllers);
        nettyServer.initBootstrap().setOption("reuseAddress", true);
        nettyServer.setDeployment(deployment);
        nettyServer.setPort(port);
        nettyServer.setRootResourcePath(rootResourcePath);
        nettyServer.setIoWorkerCount(ioWorkerCount);
        nettyServer.setExecutorThreadCount(executorThreadCount);
        nettyServer.setMaxRequestSize(maxRequestSize);
        nettyServer.setSecurityDomain(null);
        nettyServer.start();
        System.out.println("Netty rest server started on port(s): " + port);
    }

    public void addProvider(Collection<Object> providers) {
        if (providers != null && !providers.isEmpty()) {
            deployment.getProviders().addAll(providers);
        }
    }

    public void addResources(Collection<Object> instances) {
        if (instances != null && !instances.isEmpty()) {
            deployment.getResources().addAll(instances);
        }
    }

    public static void main(String[] args) {
        NettyService nettyService = new NettyService();
        nettyService.start();
    }
}
