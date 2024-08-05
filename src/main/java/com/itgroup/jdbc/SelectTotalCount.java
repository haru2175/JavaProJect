package com.itgroup.jdbc;

import com.itgroup.dao.MusicDao;

import java.util.Scanner;

public class SelectTotalCount {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("'allMusic' ,'장르' 입력 : ");

        String ballad = scanner.next();

        MusicDao dao = new MusicDao();
        int totalCount = dao.getTotalCount(ballad);

        if (ballad.equals("allMusic")) {
            System.out.println("노래 목록 : " + totalCount);
        } else {
            String message = "노래 장르 %s 곡 : %d\n";
            System.out.printf(message, ballad,totalCount);
        }
    }
}
