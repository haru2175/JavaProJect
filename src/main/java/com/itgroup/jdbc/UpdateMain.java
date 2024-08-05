package com.itgroup.jdbc;

import com.itgroup.bean.Music;
import com.itgroup.dao.MusicDao;

import java.util.Scanner;

public class UpdateMain {
    public static void main(String[] args) {
        MusicDao dao = new MusicDao();
        Music bean = new Music();

        Scanner scanner = new Scanner(System.in);

        System.out.print("노래 제목 : ");
        String musicName = scanner.next();

        bean.setMusicName(musicName);
        bean.setArtist("박효신");
        bean.setAlbum("1988.08.08");
        bean.setBallad("발라드");
        bean.setStime(5.00);

        int cnt = -1;
        cnt = dao.updateData(bean);

        if (cnt == -1) {
            System.out.println("다른 노래 정보 수정이 안되었습니다.");
        } else {
            System.out.println("다른 노래 정보 수정이 되었습니다.");
        }
    }
}
