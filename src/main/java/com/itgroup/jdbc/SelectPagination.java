package com.itgroup.jdbc;

import com.itgroup.Utility.Paging;
import com.itgroup.bean.Music;
import com.itgroup.dao.MusicDao;

import java.util.List;
import java.util.Scanner;

public class SelectPagination {
    public static void main(String[] args) {
        // 검색 모드와 페이지 네이션 기능을 구현합니다.
        Scanner scanner = new Scanner(System.in);
        System.out.print("몇 페이지 볼꺼니? : ");
        String pageNumber = scanner.next();

        System.out.print("페이지 당 몇 건씩 볼꺼니? : ");
        String pageSize = scanner.next();

        System.out.print("all , 발라드 중 1개 입력 : ");
        String mode = scanner.next(); // 검색 모드(무엇을 검색할 것인가?)

        MusicDao dao = new MusicDao();
        int totalCount = dao.getTotalCount(mode);

        String url = "MuList.jsp";
        String keyword = "";
        Paging pageInfo = new Paging(pageNumber,pageSize,totalCount,url,mode,keyword);
        pageInfo.displayInformation();

        List<Music> musicList = dao.getPaginationData(pageInfo);

        for (Music bean : musicList) {
            ShowData.printBean(bean);

        }
    }
}
