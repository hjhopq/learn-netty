package com.hjh.netty.hello;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 *
 * Netty hello world demo Server端
 * 接收client端发过来的消息   hello server
 * 回发给client端  hello client
 *
 * 监听端口 6666
 * @author hjh
 * @date 2020/6/25 19:17
 */
public class HelloServer {

    public static void main(String[] args) throws Exception{


        //创建BossGroup 和 WorkGroup  分别是处理连接和读写
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();


        try {
            //创建服务端的启动对象，对于基础参数的配置
            ServerBootstrap bootstrap = new ServerBootstrap();

            //设置参数
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ServerHandler());
                        }
                    });

            System.out.println("Server is ready...");

            //绑定端口
            ChannelFuture cf = bootstrap.bind(6666).sync();

            //对关闭通道进行监听
            cf.channel().closeFuture().sync();

        } finally {

            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }


}
