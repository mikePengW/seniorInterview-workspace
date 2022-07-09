package com.chalon.sort;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wei.peng
 */
public class BubbleSort {

    public static void main(String[] args) {
//        int arr[] = {3, 9, -1, 10, 20};
//        System.out.println("排序前");
//        System.out.println(Arrays.toString(arr));
//        // 冒泡排序
//        bobbleSort(arr);
//        System.out.println("排序后");
//        System.out.println(Arrays.toString(arr));

        // 测试一下冒泡排序的速度O(n^2), 给80000个整数
        // 创建一个80000长度的随机的数组
        int[] arr = new int[80000];
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 8000000); // 生成一个(0, 8000000) 的数
        }
        Date date1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1Str = sdf.format(date1);
        System.out.println("排序前的时间是=" + date1Str);
        // 冒泡排序
        bobbleSort(arr);
        Date date2 = new Date();
        String date2Str = sdf.format(date2);
        System.out.println("排序后的时间是=" + date2Str);

        /*
        // 为了容易理解，把冒泡排序的演进过程，展示出来
        // 第一趟排序，就是将最大的数排在最后
        int temp = 0; // 临时变量
        for (int j = 0; j < arr.length - 1 - 0; j++) {
            // 如果前面的数比后面的数大，则交换
            if (arr[j] > arr[j + 1]) {
                temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
        System.out.println("第一趟排序后的数组");
        System.out.println(Arrays.toString(arr));
        // 第二趟排序，就是将第二大的数排在倒数第二位
        for (int j = 0; j < arr.length - 1 - 1; j++) {
            // 如果前面的数比后面的数大，则交换
            if (arr[j] > arr[j + 1]) {
                temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
        System.out.println("第二趟排序后的数组");
        System.out.println(Arrays.toString(arr));
        // 第三趟排序，就是将第三大的数排在倒数第三位
        for (int j = 0; j < arr.length - 1 - 2; j++) {
            // 如果前面的数比后面的数大，则交换
            if (arr[j] > arr[j + 1]) {
                temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
        System.out.println("第三趟排序后的数组");
        System.out.println(Arrays.toString(arr));
        // 第四趟排序，就是将第四大的数排在倒数第四位
        for (int j = 0; j < arr.length - 1 - 3; j++) {
            // 如果前面的数比后面的数大，则交换
            if (arr[j] > arr[j + 1]) {
                temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
        System.out.println("第四趟排序后的数组");
        System.out.println(Arrays.toString(arr));
        */

    }

    // 将前面的冒泡排序算法，封装成一个方法
    public static void bobbleSort(int[] arr) {
        // 冒泡排序 的时间复杂度 O(n^2)
        int temp = 0; // 临时变量
        boolean flag = false; // 标识变量，表示是否进行过交换
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                // 如果前面的数比后面的数大，则交换
                if (arr[j] > arr[j + 1]) {
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            // System.out.println("第" + (i + 1) + "趟排序后的数组");
            // System.out.println(Arrays.toString(arr));

            if (!flag) { // 在一趟排序中，一次交换都没有发生过
                break;
            } else {
                flag = false; // 重置flag！！！，进行下次判断
            }
        }
    }

}
