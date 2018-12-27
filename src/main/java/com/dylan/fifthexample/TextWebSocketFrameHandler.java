package com.dylan.fifthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

import static java.lang.System.out;

/**
 * @Auther: langdylan
 * @Date: 2018/12/25 20:24
 * @Description:
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        out.println("received message : " + msg.text());

        ctx.channel().writeAndFlush(new TextWebSocketFrame("server time: " + LocalDateTime.now()));
    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        out.println("handler added: " + ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        out.println("handler removed: " + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        out.println("exception....");
        ctx.close();
    }
}
