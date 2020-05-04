package cn.tao.netty.rpc;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RpcEncoder extends MessageToByteEncoder {
    //目标对象类型进行编码
    private Class<?> target;

    public RpcEncoder(Class target) {
        this.target = target;
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if (target.isInstance(o)) {
            byte[] data = JSON.toJSONBytes(o); //使用fastJson将对象转换为byte
            byteBuf.writeInt(data.length); //先将消息长度写入，也就是消息头
            byteBuf.writeBytes(data); //消息体中包含我们要发送的数据
        }

    }
}
