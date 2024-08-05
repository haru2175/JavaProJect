package com.itgroup.jdbc;


import com.itgroup.Utility.Paging;


public class PagingTest {
    public static void main(String[] args) {
        String pageNumber = "1" ;
        String pageSize = "10" ;
        int totalCount = 6 ;
        String url = "abc.html";
        String mode = "ballad";
        String keyword = "야생화";
        Paging pageInfo = new Paging(pageNumber, pageSize, totalCount, url, mode, keyword);
        pageInfo.displayInformation();
    }
}
/*
실행 결과
총 레코드 건수 : 257

전체 페이지 수 : 26

보여줄 페이지 넘버 : 12

한 페이지에 보여줄 건수 : 10

현재 페이지의 시작 행 : 111

현재 페이지의 끝 행 : 120

보여줄 페이지 링크 수 : 10

페이징 처리 시작 페이지 번호 : 11

페이징 처리 끝 페이지 번호 : 20

요청 URL : abc.html

상단 우측의 현재 페이지 위치 표시 : 총 257건[12/26]

검색 모드 : bread

검색 키워드 : 크로와상
*/
