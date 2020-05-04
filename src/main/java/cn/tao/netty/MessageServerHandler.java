package cn.tao.netty;

import cn.tao.netty.Service.MyAdapter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import org.apache.cxf.helpers.HttpHeaderHelper;
import org.springframework.context.ApplicationContext;

import static org.apache.cxf.helpers.HttpHeaderHelper.CONTENT_TYPE;
public class MessageServerHandler extends ChannelInboundHandlerAdapter {
    private ApplicationContext applicationContext;
    public MessageServerHandler(){

    }
    public MessageServerHandler(ApplicationContext applicationContext){
        this.applicationContext = applicationContext;
    }
    /**
     * 收到客户端消息，自动触发
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        /**
         * 将 msg 转为 Netty 的 ByteBuf 对象，类似 JDK 中的 java.nio.ByteBuffer，不过 ButeBuf 功能更强，更灵活
         */
        HttpRequest buf = null;
        if (msg instanceof HttpRequest) {
             buf = (HttpRequest) msg;
        }
        String uri = buf.uri();
            MyAdapter bean = applicationContext.getBean(MyAdapter.class);
        //对参数进行解析找到需要解析的接口
        String adapter = bean.adapter(uri, applicationContext);
        adapter = adapter == null ? "" : adapter;
        System.out.println(Thread.currentThread().getName() + ",The server receive  order : " + buf);

        /**回复消息
         * copiedBuffer：创建一个新的缓冲区，内容为里面的参数
         * 通过 ChannelHandlerContext 的 write 方法将消息异步发送给客户端
         * */
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1,HttpResponseStatus.OK
                ,Unpooled.wrappedBuffer(adapter.getBytes()));
        response.headers().set(CONTENT_TYPE, "application/json;charset=utf-8");
        response.headers().set(HttpHeaderHelper.CONTENT_LENGTH,
        response.content().readableBytes());
        response.headers().set(HttpHeaderHelper.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        ctx.write(response);
        ctx.flush();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        /**flush：将消息发送队列中的消息写入到 SocketChannel 中发送给对方，为了频繁的唤醒 Selector 进行消息发送
         * Netty 的 write 方法并不直接将消息写如 SocketChannel 中，调用 write 只是把待发送的消息放到发送缓存数组中，再通过调用 flush
         * 方法，将发送缓冲区的消息全部写入到 SocketChannel 中
         * */
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        /**当发生异常时，关闭 ChannelHandlerContext，释放和它相关联的句柄等资源 */
        ctx.close();
    }

    //客户端去和服务端连接成功时触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("客户端与服务端连接成功！");
    }

}
