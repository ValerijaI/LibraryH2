package com.library.databaseH2;

public class Answer {

    public static void main(String[] args) {
        for (int i = 1; i <=10; i++) {
            for (int j = 1; j <= 10; j++) {
                String toPrint = "";
                if (i * j < 10) {
                    toPrint = "   " + String.valueOf(i * j);
                } else if (i * j < 100) {
                    toPrint = "  " + String.valueOf(i * j);
                } else {
                    toPrint = " " + String.valueOf(i * j);
                }
                System.out.print(toPrint);
            }
            System.out.println();
        }
    }
}
