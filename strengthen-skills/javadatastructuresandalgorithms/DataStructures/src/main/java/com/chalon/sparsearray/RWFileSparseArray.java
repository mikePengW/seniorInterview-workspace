package com.chalon.sparsearray;

import org.junit.jupiter.api.Test;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 读写文件的方式
 *
 * @author wei.peng
 */
public class RWFileSparseArray {

    @Test
    public void rwFileSparseArray() throws Exception {
        // 创建一个原始的二维数组 11 * 11
        // 0: 表示没有棋子，1表示黑子，2表示蓝子
        int chessArr1[][] = new int[11][11];
        chessArr1[1][2] = 1;
        chessArr1[2][3] = 2;
        chessArr1[4][5] = 2;
        // 输出原始的二维数组
        for (int[] row : chessArr1) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

        // 将二维数组 转 稀疏数组的思路
        // 1. 先遍历二维数组 得到非0数据的个数
        int sum = 0;
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArr1[i][j] != 0) {
                    sum++;
                }
            }
        }
        // 2. 创建对应的稀疏数组
        int sparseArr1[][] = new int[sum + 1][3];
        // 给稀疏数组赋值
        sparseArr1[0][0] = 11;
        sparseArr1[0][1] = 11;
        sparseArr1[0][2] = sum;

        // 遍历二维数组，将非0的值存放到 sparseArr 中
        int count = 0; // count 用于记录是第几个非0数据
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (chessArr1[i][j] != 0) {
                    count++;
                    sparseArr1[count][0] = i;
                    sparseArr1[count][1] = j;
                    sparseArr1[count][2] = chessArr1[i][j];
                }
            }
        }
        System.out.println();

        // 输出稀疏数组的形式
        System.out.println("得到稀疏数组为~~~~~~");
        for (int i = 0; i < sparseArr1.length; i++) {
            System.out.printf("%d\t%d\t%d\t\n", sparseArr1[i][0], sparseArr1[i][1], sparseArr1[i][2]);
        }
        System.out.println();

        // 存入文件
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < sparseArr1.length; i++) {
            sb.append(String.format("%d\t%d\t%d\t\n", sparseArr1[i][0], sparseArr1[i][1], sparseArr1[i][2]));
        }
        System.out.println("存入文件：\n" + sb);
        ByteBuffer buf1 = ByteBuffer.allocate(1024);
        buf1.put(sb.toString().getBytes());
        buf1.flip();
        RandomAccessFile raf1 = new RandomAccessFile("3.txt", "rw");
        FileChannel channel1 = raf1.getChannel();
        channel1.write(buf1);

        // 读取文件
        RandomAccessFile raf2 = new RandomAccessFile("3.txt", "rw");
        FileChannel channel2 = raf2.getChannel();
        ByteBuffer buf2 = ByteBuffer.allocate(1024);
        channel2.read(buf2);
        buf2.flip();
        String inputStr = new String(buf2.array(), 0, buf2.limit());
        System.out.println("读取文件：\n" + inputStr);
        String[] rows = inputStr.split("\n");
        int sparseArr2[][] = new int[rows.length][3];
        for (int i = 0; i < rows.length; i++) {
            String[] data = rows[i].split("\t");
            sparseArr2[i][0] = Integer.valueOf(data[0]);
            sparseArr2[i][1] = Integer.valueOf(data[1]);
            sparseArr2[i][2] = Integer.valueOf(data[2]);
        }

        // 将稀疏数组 --》 恢复成 原始的二维数组
        /**
         * 1. 先读取稀疏数组的第一行，根据第一行的数据，创建原始的二维数组，比如上面的  chessArr2 = int [11][11]
         * 2. 在读取稀疏数组后几行的数据，并赋给 原始的二维数组 即可.
         */

        // 1. 先读取稀疏数组德第一行，根据第一行的数据，创建原始的二维数组
        int chessArr2[][] = new int[sparseArr2[0][0]][sparseArr2[0][1]];

        // 2. 在读取稀疏数组后几行的数据，并赋给 原始的二维数组 即可.
        for (int i = 1; i < sparseArr2.length; i++) {
            chessArr2[sparseArr2[i][0]][sparseArr2[i][1]] = sparseArr2[i][2];
        }

        // 输出恢复后的二维数组
        System.out.println("恢复后的二维数组");
        for (int[] row : chessArr2) {
            for (int data : row) {
                System.out.printf("%d\t", data);
            }
            System.out.println();
        }

    }

}
