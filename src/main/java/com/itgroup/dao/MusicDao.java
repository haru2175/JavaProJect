package com.itgroup.dao;

import com.itgroup.Utility.Paging;
import com.itgroup.bean.Music;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MusicDao extends SuperDao { // DB에 접속을 할때 필요한 옵션들을 메소드로 인해 추가시킨것
    //기본 생성자,모든걸 조회하기
    public MusicDao() {
    }


    public List<Music> selectAll() { //모든걸 조회
        Connection conn = null;
        String sql = " select * from musics order by pnum asc";
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<Music> allData = new ArrayList<>();
        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Music bean = this.makebean(rs);
                allData.add(bean);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return allData;
    }

    private Music makebean(ResultSet rs) { //Bean 데이터 집합
        // bean 데이터를 만들어 주는 메소드입니다.
        // 여러 군데서 호출이 되므로 별도로 만들었습니다.
        Music bean = new Music();
        try {
            bean.setPnum(rs.getInt("pnum"));
            bean.setMusicName((rs.getString("musicname")));
            bean.setArtist(rs.getString("artist"));
            bean.setAlbum(rs.getString("album"));
            bean.setBallad(rs.getString("ballad"));
            bean.setStime(rs.getDouble("stime"));
            bean.setImage(rs.getString("image"));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    public Music selectByPK(int pnum) { //프라이머리 키
        Connection conn = null;
        String sql = " select * from Musics ";
        sql += " where pnum = ? ";

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        Music bean = null;
        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, pnum);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                bean = this.makebean(rs);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return bean;
    }

    public int inertData(Music bean) {
        System.out.println(bean);
        int cnt = -1;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = " insert into musics(pnum,musicname,artist,album,ballad,stime,image) ";
        sql += " values(seqmusic.nextval,?,?,?,?,?,?)";

        try {
            conn = super.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, bean.getMusicName());
            pstmt.setString(2, bean.getArtist());
            pstmt.setString(3, bean.getAlbum());
            pstmt.setString(4, bean.getBallad());
            pstmt.setString(5, String.valueOf(bean.getStime()));
            pstmt.setString(6, bean.getImage());

            cnt = pstmt.executeUpdate();
            conn.commit();

        } catch (Exception e) {
            e.printStackTrace();
            try {
                conn.rollback();

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    pstmt.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return cnt;
    }

    public int updateData(Music bean) {
        System.out.println(bean);
        int cnt = -1;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = " update musics set musicName = ?, artist = ?, album  = ?,ballad = ?, stime = ?, image = ? ";
        sql += " where pnum = ?";

        try {
            conn = super.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, bean.getMusicName());
            pstmt.setString(2, bean.getArtist());
            pstmt.setString(3, String.valueOf(bean.getAlbum()));
            pstmt.setString(4, bean.getBallad());
            pstmt.setString(5, String.valueOf(bean.getStime()));;
            pstmt.setString(6, bean.getImage());
            pstmt.setInt(7,bean.getPnum());

            cnt = pstmt.executeUpdate();
            conn.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    pstmt.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return cnt;
    }

    public int deleteData(int pnum) {
        System.out.println("노래 번호" + pnum);
        int cnt = -1;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = " delete from musics";
        sql += " where pnum = ?";
        try {
            conn = super.getConnection();
            conn.setAutoCommit(false);
            pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, pnum);

            cnt = pstmt.executeUpdate();
            conn.commit();

        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                conn.rollback();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    pstmt.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return cnt;
    }

    public int getTotalCount(String ballad) {
        int totalCount = 0;
        String sql = " select count(*) as myMusic from musics ";
        boolean bool = ballad == null || ballad.equals("all");
        if (!bool) {
            sql += " where ballad = ? ";
        }

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);

            if (!bool) {
                pstmt.setString(1, ballad);
            }
            rs = pstmt.executeQuery();

            if (rs.next()) {
                totalCount = rs.getInt("myMusic");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return totalCount;
    }

    public List<Music> selectByMusicName(String musicName) {
        Connection conn = null;

        String sql = " select * from musics ";


        boolean bool = musicName == null || musicName.equals("all");
        if (!bool) {
            sql += " where musicName = ? ";
        }
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<Music> allData = new ArrayList<>();
        try {
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);
            if (!bool) {
                pstmt.setString(1, musicName);
            }

            rs = pstmt.executeQuery();

            while (rs.next()) {
                Music bean = this.makebean(rs);
                allData.add(bean);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return allData;
    }

    public List<Music> getPaginationData(Paging pageInfo) {
        Connection conn = null ;

        String sql = " select pnum, musicName, artist, album, ballad, stime, image ";
        sql += " from ( ";
        sql += " select pnum, musicName, artist, album, ballad, stime, image, ";
        sql += " rank() over(order by pnum asc) as ranking ";
        sql += " from musics ";

        // mode가 'all'이 아니면 where 절이 추가로 필요합니다.
        String mode = pageInfo.getMode();
        boolean bool = mode.equals(null) || mode.equals("null") || mode.equals("") || mode.equals("all");

        if (!bool) {
            sql += " where ballad = ? ";
        }

        sql += " ) ";
        sql += " where ranking between ? and ? ";
        PreparedStatement pstmt = null ;
        ResultSet rs = null ;

        List<Music> allData = new ArrayList<>();
        try{
            conn = super.getConnection();
            pstmt = conn.prepareStatement(sql);

            if (!bool) {
                pstmt.setString(1, mode);
                pstmt.setInt(2,pageInfo.getBeginRow());
                pstmt.setInt(3,pageInfo.getEndRow());
            } else {
                pstmt.setInt(1,pageInfo.getBeginRow());
                pstmt.setInt(2,pageInfo.getEndRow());
            }

            rs = pstmt.executeQuery() ;

            while(rs.next()){
                Music bean = this.makebean(rs);
                allData.add(bean);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally{
            try{
                if(rs!=null){rs.close();}
                if(pstmt!=null){pstmt.close();}
                if(conn!=null){conn.close();}
            }catch(Exception ex){
                ex.printStackTrace();
            }
        }
        return allData;
    }
}

