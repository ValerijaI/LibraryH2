package com.library.databaseH2;

public class Tree {

    public static void main(String[] args) {
        int count = 20;
//        for (int i = 1; i <= count; i++) {
//            for (int j = 1; j <= (count * 2 - 1); j++) {
//                if (j > (count - i ) && j < (count + i)) {
//                    System.out.format("*");
//                } else {
//                    System.out.format(" ");
//                }
//            }
//            System.out.format("%n");
//        }

        for (int i = count; i >= 1; i--) {
            for (int j = 1; j <= (count * 2 - 1); j++) {
                if (j > (count - i ) && j < (count + i)) {
                    System.out.format("*");
                } else {
                    System.out.format(" ");
                }
            }
            System.out.format("%n");
        }
    }
}
