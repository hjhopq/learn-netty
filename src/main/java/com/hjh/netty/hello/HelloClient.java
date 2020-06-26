package com.hjh.netty.hello;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 *
 * Netty hello world demo Client端
 * 往Server端发送 hello Server消息。
 * 接收回发的消息并打印
 *
 * 连接 127.0.0.1:6666
 *
 * @author hjh
 * @date 2020/6/25 22:54
 */
public class HelloClient {

    public static void main(String[] args) throws Exception {

        //创建一个事件循环组
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            //创建客户端启动对象
            Bootstrap bootstrap = new Bootstrap();

            //设置相关参数
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ClientHandler());
                        }
                    });

            System.out.println("Client is ready...");

            //连接Server
            ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 6666);

            //关闭通道监听
            channelFuture.channel().closeFuture().sync();

        } finally {

            group.shutdownGracefully();
        }
    }
}
