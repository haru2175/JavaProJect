package com.itgroup.Controller;

import com.itgroup.Utility.Utility;
import com.itgroup.bean.Music;
import com.itgroup.dao.MusicDao;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class MusicUpdateController implements Initializable {
    @FXML private TextField fxmlPnum;
    @FXML private TextField fxmlName;
    @FXML private TextField fxmlArtist;
    @FXML private DatePicker fxmlAlbum;
    @FXML private ComboBox<String> fxmlBallad;
    @FXML private TextField fxmlStime;
    @FXML private TextField fxmlImage;

    private Music oldBean = null;
    private Music newBean = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("수정하겠습니다.");

    }

    public void onMusicUpdate(ActionEvent event) {
        boolean bool = validationCheck();

        if (bool == true) {
            MusicDao dao = new MusicDao();
            int cnt = -1;
            cnt = dao.updateData(this.newBean);

            if (cnt == -1) {
                System.out.println("수정 실패");

            } else {
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        } else {
            System.out.println("유효성 검사를 통과하지 못했습니다.");
        }
    }

    private boolean validationCheck() {
        int pnum = Integer.valueOf(fxmlPnum.getText().trim());

        String[] message = null;
        String name = fxmlName.getText().trim();
        if (name.length() <= 1 || name.length() >= 11) {
            message = new String[]{"유효성 검사 : 노래 제목", "글자 수를 확인하세요.", "이름은 1글자 이상 10글자 이하 이어야합니다."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        String artist = fxmlArtist.getText().trim();
        if (artist.length() <= 1 || artist.length() >= 11) {
            message = new String[]{"유효성 검사 : 가수", "글자 수를 확인하세요.", "이름은 1글자 이상 10글자 이하 이어야합니다."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        LocalDate _albumDate = fxmlAlbum.getValue();
        String albumDate = null;
        if (_albumDate == null) {
            message = new String[]{"유효성 검사 : 앨범 날짜", "무효한 날짜 형식", "올바른 날짜 형식을 입력해 주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;

        } else {
            albumDate = _albumDate.toString();
        }

        int selectedIndex = fxmlBallad.getSelectionModel().getSelectedIndex();
        String ballad = null;
        if (selectedIndex == 0) {
            message = new String[]{"유효성 검사 : 장르", "장르 미선택", "원하시는 장르를 반드시 선택해주세요."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        } else {
            ballad = fxmlBallad.getSelectionModel().getSelectedItem();
            System.out.println("선택된 항목");
            System.out.println(ballad);
        }

        String stime = fxmlStime.getText().trim();
        if (stime.length() <= 1.0 || stime.length() >= 10.0) {
            message = new String[]{"유효성 검사 : 재생 시간", "무효한 재생 시간", "1분 이상 10분 이하 이어야합니다."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }
        boolean bool = false;
        String image = fxmlImage.getText().trim();
        if (image == null || image.length() < 5) {
            message = new String[]{"유효성 검사 : 이미지", "필수 입력 체크", "이미지는 필수 입력 사항입니다."};
            Utility.showAlert(Alert.AlertType.WARNING,message);
            return false;
        }

        bool = image.endsWith(".jpg") || image.endsWith(".png");
        if (!bool) {
            message = new String[]{"유효성 검사 : 이미지", "확장자 점검", "이미지의 확장자는 '.jpg' 또는 '.png' 이하 이어야 합니다."};
            Utility.showAlert(Alert.AlertType.WARNING, message);
            return false;
        }

        this.newBean = new Music();
        // 유효성 검사가 통과되면 비로소 객체 생성합니다.
        newBean.setPnum(pnum);
        newBean.setMusicName(name);
        newBean.setArtist(artist);
        newBean.setAlbum(albumDate);
        newBean.setBallad(ballad);
        newBean.setStime(Double.parseDouble(stime));
        newBean.setImage(image);

        return true;
    }

    public void setBean(Music bean) {
        this.oldBean = bean;

        fillPreviousData(); // 과거에 내가 등록했던 정보들을 해당 컨트롤에 다시 입력하는 메소드

        fxmlPnum.setVisible(false);
    }

    private void fillPreviousData() {
        fxmlPnum.setText(String.valueOf(this.oldBean.getPnum()));
        fxmlName.setText(this.oldBean.getMusicName());
        fxmlArtist.setText(this.oldBean.getArtist());
        fxmlBallad.setValue(this.oldBean.getBallad());
        fxmlStime.setText(String.valueOf(this.oldBean.getStime()));
        fxmlImage.setText(this.oldBean.getImage());

        String albumDate = this.oldBean.getAlbum();
        if (albumDate == null || albumDate.equals("null")) {

        } else {
            fxmlAlbum.setValue(Utility.getDatePicker(albumDate));
        }
    }
}
