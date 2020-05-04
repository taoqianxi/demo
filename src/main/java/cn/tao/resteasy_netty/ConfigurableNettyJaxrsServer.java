package cn.tao.resteasy_netty;

import org.jboss.netty.bootstrap.Bootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.resteasy.core.SynchronousDispatcher;
import org.jboss.resteasy.plugins.server.netty.HttpServerPipelineFactory;
import org.jboss.resteasy.plugins.server.netty.HttpsServerPipelineFactory;
import org.jboss.resteasy.plugins.server.netty.NettyJaxrsServer;
import org.jboss.resteasy.plugins.server.netty.RequestDispatcher;

import javax.net.ssl.SSLContext;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;

public class ConfigurableNettyJaxrsServer extends NettyJaxrsServer {

    private final int ioWorkerCount = Runtime.getRuntime().availableProcessors() * 2;
    private final int executorThreadCount = 16;
    private SSLContext sslContext;
    private final int maxRequestSize = 10485760;
    private final boolean isKeepAlive = true;
    static final ChannelGroup allChannels = new DefaultChannelGroup("NettyJaxrsServer");
    private List<ChannelHandler> channelHandlers = Collections.emptyList();

    public ConfigurableNettyJaxrsServer() {
    }

    public Bootstrap initBootstrap() {
        this.bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(), Executors.newCachedThreadPool(), this.ioWorkerCount));
        return this.bootstrap;
    }

    public void setBootstrap(ServerBootstrap bootstrap) {
        this.bootstrap = bootstrap;
    }

    public void start() {
        this.deployment.start();
        RequestDispatcher dispatcher = new RequestDispatcher((SynchronousDispatcher)this.deployment.getDispatcher(), this.deployment.getProviderFactory(), this.domain);
        if (this.bootstrap == null) {
            this.initBootstrap();
        }

        Object factory;
        if (this.sslContext == null) {
            factory = new HttpServerPipelineFactory(dispatcher, this.root, 16, 10485760, true, this.channelHandlers);
        } else {
            factory = new HttpsServerPipelineFactory(dispatcher, this.root, 16, 10485760, true, this.channelHandlers, this.sslContext);
        }

        this.bootstrap.setPipelineFactory((ChannelPipelineFactory)factory);
        this.channel = this.bootstrap.bind(new InetSocketAddress(this.configuredPort));
        allChannels.add(this.channel);
    }
}
