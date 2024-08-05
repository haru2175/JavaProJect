package com.itgroup.jdbc;

import com.itgroup.bean.Music;

public class ShowData {//데이터 출력 보여주기
    public static void printBean(Music bean) {
        String musicName = bean.getMusicName();
        String artist = bean.getArtist();
        String album = bean.getAlbum();
        String ballad = bean.getBallad();
        double stime = bean.getStime();

        System.out.println("노래 제목 : " +musicName);
        System.out.println("가수 : " + artist);
        System.out.println("앨범 발매 : "+ album);
        System.out.println("장르 : " + ballad);
        System.out.println("재생 시간 : " + stime);
        System.out.println("=======================");
    }
}
