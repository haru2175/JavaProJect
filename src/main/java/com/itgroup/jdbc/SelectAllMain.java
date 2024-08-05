package com.itgroup.jdbc;

import com.itgroup.dao.MusicDao;
import com.itgroup.bean.Music;

import java.util.List;

public class SelectAllMain { //JDBC
    public static void main(String[] args) {
        MusicDao dao = new MusicDao();

        List<Music> allData = dao.selectAll();
        System.out.println("항목 개수 : " + allData.size());

        for (Music bean : allData) {
            ShowData.printBean(bean);
        }
    }
}
