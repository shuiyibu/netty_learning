package com.dylan.grpc;

import com.dylan.proto.*;
import io.grpc.stub.StreamObserver;

import java.util.UUID;

import static java.lang.System.out;

/**
 * @Auther: langdylan
 * @Date: 2018/12/28 16:41
 * @Description:
 */
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    @Override
    public void getRealnameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        out.println("accept client message: " + request.getUsername());
        responseObserver.onNext(MyResponse.newBuilder().setRealname("dylan").build());
        responseObserver.onCompleted();
    }

    @Override
    public void getStudentsByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        out.println("accept client message: " + request.getAge());
        responseObserver.onNext(StudentResponse.newBuilder().setName("dylan").setAge(20).setCity("washington").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("alex").setAge(30).setCity("francisco").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("bob").setAge(40).setCity("london").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("clare").setAge(50).setCity("paris").build());

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<StudentRequest> getStudentWrapperByAge(StreamObserver<StudentResponseList> responseObserver) {
        return new StreamObserver<StudentRequest>() {
            @Override
            public void onNext(StudentRequest value) {
                out.println("onNext: " + value.getAge());
            }

            @Override
            public void onError(Throwable t) {
                out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                StudentResponse studentResponse = StudentResponse.newBuilder().setName("张三").setAge(20).setCity("西安").build();
                StudentResponse studentResponse2 = StudentResponse.newBuilder().setName("李四").setAge(30).setCity("山西").build();
                StudentResponse studentResponse3 = StudentResponse.newBuilder().setName("王五").setAge(40).setCity("晋城").build();

                StudentResponseList studentResponseList = StudentResponseList.newBuilder()
                        .addStudentResponse(studentResponse)
                        .addStudentResponse(studentResponse2)
                        .addStudentResponse(studentResponse3)
                        .build();

                responseObserver.onNext(studentResponseList);
                responseObserver.onCompleted();
            }
        };
    }


    @Override
    public StreamObserver<StreamRequest> biTalk(StreamObserver<StreamResponse> responseObserver) {
        return new StreamObserver<StreamRequest>() {
            @Override
            public void onNext(StreamRequest value) {
                out.println(value.getRequestInfo());

                responseObserver.onNext(StreamResponse.newBuilder().setResponseInfo(UUID.randomUUID().toString()).build());
            }

            @Override
            public void onError(Throwable t) {
                out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }


}
