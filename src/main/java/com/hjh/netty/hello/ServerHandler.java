package com.hjh.netty.hello;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author hjh
 * @date 2020/6/25 22:02
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 读取数据事件  可以读取到客户端发送的消息
     * @param ctx 上下文对象，含有管道pipeline、通道channel、地址等信息
     * @param msg  客户端发送的数据  默认是Object对象
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Channel channel = ctx.channel();

        //将msg转成一个ByteBuf
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("客户端发送消息为：" + buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端的地址为：" + channel.remoteAddress());
    }

    /**
     *
     * 数据读取完毕
     *
     * @param ctx 上下文
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入缓存并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello client", CharsetUtil.UTF_8));
    }

    /**
     *
     * 处理异常， 一般是关闭通道
     *
     * @param ctx 上下文
     * @param cause 异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
