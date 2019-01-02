package com.dylan.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

import static java.lang.System.out;

/**
 * @Auther: langdylan
 * @Date: 2018/12/28 17:06
 * @Description:
 */
public class GrpcServer {

    private Server server;

    public static void main(String[] args) throws IOException, InterruptedException {
        GrpcServer server = new GrpcServer();
        server.start();
        server.awaitTermination(); //should set service await, or else service will terminate immediately
    }


    private void start() throws IOException {
        // based on tcp
        server = ServerBuilder.forPort(8899).addService(new StudentServiceImpl()).build().start();
        out.println("server start...");

        //add jvm callback hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            out.println("close jvm");
            GrpcServer.this.stop();
        }));

        out.println("callback hook done...");
    }

    private void stop() {
        if (null != this.server) {
            this.server.shutdown();
        }

    }

    private void awaitTermination() throws InterruptedException {
        if (null != this.server) {
            this.server.awaitTermination();
        }
    }
}
