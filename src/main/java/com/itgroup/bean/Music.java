package com.itgroup.bean;

public class Music { // bean 클래스 칼럼들 가져오는곳
    //변수 선언,기본 생성자,toString,set,get
    private int pnum;
    private String musicName;
    private String artist;
    private String album;
    private String ballad;
    private double stime;
    private String image;

    public Music() {
    }

    @Override
    public String toString() {
        return "Music{" +
                "pnum=" + pnum +
                ", musicName='" + musicName + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                ", ballad='" + ballad + '\'' +
                ", stime=" + stime +
                ", image='" + image + '\'' +
                '}';
    }

    public int getPnum() {
        return pnum;
    }

    public void setPnum(int pnum) {
        this.pnum = pnum;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getBallad() {
        return ballad;
    }

    public void setBallad(String ballad) {
        this.ballad = ballad;
    }

    public double getStime() {
        return stime;
    }

    public void setStime(double stime) {
        this.stime = stime;
    }
}
