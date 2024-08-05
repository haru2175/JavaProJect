package com.itgroup.jdbc;

import com.itgroup.bean.Music;
import com.itgroup.dao.MusicDao;

import java.util.Scanner;

public class SelectOnlyOne { //노래 제목만 입력되면 나오는것
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("노래 제목 입력 : ");

        String pnum = scanner.nextLine();

        MusicDao dao = new MusicDao();
        Music bean = dao.selectByPK(Integer.parseInt(pnum));

        if (bean == null) {
            System.out.println("('" + pnum + "')"+"이란 노래는 존재하지 않습니다.");
        } else {
            ShowData.printBean(bean);
        }
    }
}
