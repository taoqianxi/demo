package cn.tao.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.UnsupportedEncodingException;
import java.util.Date;

public class MessageClientHandler extends ChannelInboundHandlerAdapter {
    /**
     * 当客户端和服务端 TCP 链路建立成功之后，Netty 的 NIO 线程会调用 channelActive 方法
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String reqMsg = "我是客户端 " + Thread.currentThread().getName();
        byte[] reqMsgByte = reqMsg.getBytes("UTF-8");
        ByteBuf reqByteBuf = Unpooled.buffer(reqMsgByte.length);
        /**
         * writeBytes：将指定的源数组的数据传输到缓冲区
         * 调用 ChannelHandlerContext 的 writeAndFlush 方法将消息发送给服务器
         */
        reqByteBuf.writeBytes(reqMsgByte);
        ctx.writeAndFlush(reqByteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf buf = (ByteBuf) msg; // (1)
        try {
            byte[] reg = new byte[buf.readableBytes()];
            /**readBytes：将缓冲区字节数组复制到新建的 byte 数组中
             * 然后将字节数组转为字符串
             * */
            buf.readBytes(reg);
            String body = new String(reg, "UTF-8");
            System.out.println(Thread.currentThread().getName() + ",The server receive  order : " + body);

            ctx.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            buf.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
