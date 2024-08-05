package com.itgroup.jdbc;

import com.itgroup.bean.Music;
import com.itgroup.dao.MusicDao;

import java.util.Scanner;

public class DeleteMain { // 노래 삭제
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("삭제할 노래 번호 : ");
        int pnum = scanner.nextInt();

        MusicDao dao = new MusicDao();
        int cnt = -1;
        cnt = dao.deleteData(pnum);

        if (cnt == -1) {
            System.out.println("노래 삭제가 안되었습니다.");
        } else {
            System.out.println("노래 삭제가 되었습니다.");
        }
    }
}
