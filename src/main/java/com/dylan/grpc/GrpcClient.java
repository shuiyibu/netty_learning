package com.dylan.grpc;

import com.dylan.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.time.LocalDateTime;
import java.util.Iterator;

import static java.lang.System.out;

/**
 * @Auther: langdylan @Date: 2018-12-29 10:26 @Description:
 */
public class GrpcClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8899).usePlaintext().build();

        StudentServiceGrpc.StudentServiceBlockingStub blockingStub = StudentServiceGrpc.newBlockingStub(channel);
        StudentServiceGrpc.StudentServiceStub studentServiceStub = StudentServiceGrpc.newStub(channel); // for stream request; asyn call

        out.println("========================request: single string; response: single stream==================================");
        simpleRPC(blockingStub);

        out.println("========================request: single string; response:stream==================================");
//        serversideStreamingRPC(blockingStub);

        out.println("========================request: stream; response: single string==================================");
//        clientsideStreamingRPC(studentServiceStub);

        out.println("========================request: stream; response: stream==================================");
//        bidirectionalStreamingRPC(studentServiceStub);
    }

    public static void simpleRPC(StudentServiceGrpc.StudentServiceBlockingStub blockingStub) {
        for (int i = 0; i < 10; i++) {
            MyResponse myResponse = blockingStub.getRealnameByUsername(MyRequest.newBuilder().setUsername("zhangsan").build());
            out.println(myResponse.getRealname());
        }
    }

    public static void serversideStreamingRPC(StudentServiceGrpc.StudentServiceBlockingStub blockingStub) {
        Iterator<StudentResponse> iterator = blockingStub.getStudentsByAge(StudentRequest.newBuilder().setAge(20).build());
        while (iterator.hasNext()) {
            StudentResponse studentResponse = iterator.next();
            out.println(studentResponse.getName() + ", " + studentResponse.getAge() + ", " + studentResponse.getCity());
        }
    }

    public static void clientsideStreamingRPC(StudentServiceGrpc.StudentServiceStub studentServiceStub) {
        StreamObserver<StudentResponseList> studentResponseListStreamObserver = new StreamObserver<StudentResponseList>() {
            @Override
            public void onNext(StudentResponseList value) {
                value.getStudentResponseList().forEach(studentResponse -> {
                    out.println(studentResponse.getName());
                    out.println(studentResponse.getAge());
                    out.println(studentResponse.getCity());
                    out.println("*******************");
                });
            }

            @Override
            public void onError(Throwable t) {
                out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                out.println("completed....");
            }
        };

        StreamObserver<StudentRequest> studentRequestStreamObserver =
                studentServiceStub.getStudentWrapperByAge(studentResponseListStreamObserver); // asyn call

        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(20).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(30).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(40).build());
        studentRequestStreamObserver.onNext(StudentRequest.newBuilder().setAge(50).build());

        studentRequestStreamObserver.onCompleted();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void bidirectionalStreamingRPC(StudentServiceGrpc.StudentServiceStub studentServiceStub) {
        StreamObserver<StreamRequest> requestStreamObserver = studentServiceStub.biTalk(new StreamObserver<StreamResponse>() {
            @Override
            public void onNext(StreamResponse value) {
                out.println(value.getResponseInfo());
            }

            @Override
            public void onError(Throwable t) {
                out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                out.println("completed...");
            }
        });

        for (int i = 0; i < 10; i++) {
            requestStreamObserver.onNext(StreamRequest.newBuilder().setRequestInfo(LocalDateTime.now().toString()).build());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
