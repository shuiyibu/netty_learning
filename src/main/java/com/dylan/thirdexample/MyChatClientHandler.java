package com.dylan.thirdexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static java.lang.System.out;

/**
 * @Auther: langdylan
 * @Date: 2018/12/25 15:44
 * @Description:
 */
public class MyChatClientHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        out.println(msg);
    }
}
