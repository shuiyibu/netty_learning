package com.dylan.fourthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

import static java.lang.System.out;

/**
 * @Auther: langdylan
 * @Date: 2018/12/25 17:23
 * @Description:
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;

            String eventType = null;
            switch (event.state()) {
                case READER_IDLE:
                    eventType = "READER_IDLE";
                    break;
                case WRITER_IDLE:
                    eventType = "WRITER_IDLE";
                    break;
                case ALL_IDLE:
                    eventType = "ALL_IDLE";
            }

            out.println(ctx.channel().remoteAddress() + "timeout event: " + eventType);

            ctx.channel().close();
        }
    }
}
