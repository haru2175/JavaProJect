package com.itgroup.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class SuperDao { // DB 접속
    //드라이버 클래스,데이터 베이스 출처,사용자 ID,사용자 비번
    private String driver;
    private String url;
    private String id;
    private String password;

    public SuperDao() {//DB 드라이버
        this.driver = "oracle.jdbc.driver.OracleDriver";
        this.url = "jdbc:oracle:thin:@localhost:1521:xe";
        this.id = "music";
        this.password = "mnet";

        try {
            Class.forName(driver); //동적 객체 생성
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    protected Connection getConnection() {//DB 접속 관련
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, id, password);
            if (conn != null) {
                System.out.println("접속 성공");
            } else {
                System.out.println("접속 실패");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
