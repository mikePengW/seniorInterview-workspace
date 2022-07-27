package com.chalon.nio.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author wei.peng
 */
public class NewIOClient {
    public static void main(String[] args) throws Exception {

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 7001));
        String filename = "xigua_setup_1.0.4.exe";

        // 得到一个文件channel
        FileChannel fileChannel = new FileInputStream(filename).getChannel();

        // 准备发送
        long startTime = System.currentTimeMillis();

        // 在linux下一个transferTo 方法就可以完成传输
        // 在windows下 一次调用 transferTo 只能发送8m，就需要分段传输文件，而且
        // 要注意传输时的位置 =》 课后思考，大文件如何拷贝，计算总的大小，除以8m后每次拷贝过去 TODO
        // transferTo 底层使用到零拷贝
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);

        System.out.println("发送的总字节数 = " + transferCount + ", 耗时：" + (System.currentTimeMillis() - startTime));

        // 关闭
        fileChannel.close();
    }

}
