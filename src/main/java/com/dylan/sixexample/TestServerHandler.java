package com.dylan.sixexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import static java.lang.System.out;

/**
 * @Auther: langdylan
 * @Date: 2018/12/27 10:05
 * @Description:
 */
public class TestServerHandler extends SimpleChannelInboundHandler<MyDataInfo.Person> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.Person msg) {
        out.println(msg.getName());
        out.println(msg.getAddress());
        out.println(msg.getAge());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

    }
}
