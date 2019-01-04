package com.dylan.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import static java.lang.System.out;

/**
 * @Auther: langdylan
 * @Date: 2019-01-04 10:55
 * @Description:
 */
public class NioTest4 {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("src/main/resources/input.txt");
        FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/output.txt");

        FileChannel fileInputChannel = fileInputStream.getChannel();
        FileChannel fileOutputChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(13);
        while (true) {
            byteBuffer.clear();

            int read = fileInputChannel.read(byteBuffer);
            out.println("read: " + read);
            if (-1 == read)
                break;

            byteBuffer.flip();
            fileOutputChannel.write(byteBuffer);
        }

        fileInputStream.close();
        fileOutputStream.close();
    }
}
