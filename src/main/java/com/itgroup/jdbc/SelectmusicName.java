package com.itgroup.jdbc;

import com.itgroup.bean.Music;
import com.itgroup.dao.MusicDao;

import java.util.List;
import java.util.Scanner;

public class SelectmusicName { //특정한 곡 앨범 개수 찾기
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("all,musicName 중 1개 입력 : ");

        String musicName = scanner.next();

        MusicDao dao = new MusicDao();
        List<Music> allMusic = dao.selectByMusicName(musicName);
        System.out.println("앨범 개수 : " + allMusic.size());
        System.out.println();

        for (Music bean : allMusic) {
            ShowData.printBean(bean);
        }
    }
}
