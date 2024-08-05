package com.itgroup.jdbc;

import com.itgroup.bean.Music;
import com.itgroup.dao.MusicDao;

import java.util.Scanner;

public class InsertMain { // 노래 생성
    public static void main(String[] args) {
        // 오늘은 노래를 생성 해볼까?
        // 박효신 노래로!
        // 대장님 최신곡으로 부탁드립니다. 푸쉬하려고 시도.
        MusicDao dao = new MusicDao();
        Music bean = new Music();

        Scanner scanner = new Scanner(System.in);
        System.out.print("노래 제목 : ");
        String name = scanner.next();

        bean.setMusicName(name);
        bean.setArtist("박효신");
        bean.setAlbum("2024.07.15");
        bean.setBallad("발라드");
        bean.setStime(3.00);

        int cnt = -1;
        cnt = dao.inertData(bean);

        if (cnt == -1) {
            System.out.println("노래 등록이 안됩니다.");
        } else {
            System.out.println("노래 등록이 되었습니다.");
        }
    }
}
