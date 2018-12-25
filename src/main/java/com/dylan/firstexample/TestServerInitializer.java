package com.dylan.firstexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Auther: langdylan
 * @Date: 2018/12/25 09:37
 * @Description:
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("httpServerCodec", new HttpServerCodec());//不要使用单例，应该使用多实例
        pipeline.addLast("testHttpServerHandler", new TestHttpServerHandler());

    }
}
