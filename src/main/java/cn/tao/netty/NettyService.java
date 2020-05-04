package cn.tao.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.string.StringDecoder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.nio.charset.Charset;

public class NettyService {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        new NettyService().bind(8888,applicationContext);
    }

    private void bind(int port, ApplicationContext applicationContext){
        /**
         * interface EventLoopGroup extends EventExecutorGroup extends ScheduledExecutorService extends ExecutorService
         * 配置服务端的 NIO 线程池,用于网络事件处理，实质上他们就是 Reactor 线程组
         * bossGroup 用于服务端接受客户端连接，workerGroup 用于进行 SocketChannel 网络读写*/

        //创建bootstrap实例
        ServerBootstrap bootstrap = new ServerBootstrap();

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        bootstrap.group(bossGroup,workGroup) //设置并绑定Reactor线程池
                .channel(NioServerSocketChannel.class)//设置并绑定服务墙
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childHandler(new ChannelInitializer<SocketChannel>(){//tcp链路创立时创建ChannelPipeline 与handle
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new HttpResponseEncoder());
                        socketChannel.pipeline().addLast(new HttpRequestDecoder());
                        socketChannel.pipeline().addLast(new MessageServerHandler(applicationContext));

                    }
                });


        /**服务器启动辅助类配置完成后，调用 bind 方法绑定监听端口，调用 sync 方法同步等待绑定操作完成*/
        try {
            ChannelFuture f = bootstrap.bind(port).sync();
            if (f.isSuccess()) {
                System.out.println("服务端启动成功");
                System.out.println(Thread.currentThread().getName() + ",服务器开始监听端口，等待客户端连接.........");
            } else {
                System.out.println("服务端启动失败");
                f.cause().printStackTrace();
                bossGroup.shutdownGracefully(); //关闭线程组
                workGroup.shutdownGracefully();
            }

            /**下面会进行阻塞，等待服务器连接关闭之后 main 方法退出，程序结束
             *
             * */
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
