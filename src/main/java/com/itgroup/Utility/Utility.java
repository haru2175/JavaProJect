package com.itgroup.Utility;

import javafx.scene.control.Alert;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Utility {
    public static final String FXML_PATH = "/com/itgroup/fxml/"; // fxml 파일이 있는 경로
    public static final String IMAGE_PATH = "/com/itgroup/images/"; // 이미지 파일이 있는 경로
    public static final String CSS_PATH = "/com/itgroup/CSS/"; // CSS 파일이 있는 경로
    public static final String DATA_PATH = "src/main/resources/com/itgroup/data/"; //
    private static Map<String, String> balladMap = new HashMap<>();

    private static String makeMapData(String ballad, String mode) {
        balladMap.put("발라드","발라드");
        balladMap.put("팝송", "팝송");
        balladMap.put("AI", "AI");


        if (mode.equals("value")) {
            return balladMap.get(ballad);
        } else {
            for (String key : balladMap.keySet()) {
                if (balladMap.get(key).equals(ballad)) {
                    return key;
                }
            }
            return null; // key가 없음
        }
    }

    public static String getBalladName(String ballad, String mode) {
        return makeMapData(ballad, mode);
    }


    public static void showAlert(Alert.AlertType alertType, String[] message) {
        // 단순 메세지 박스를 보여 주기 위한 유틸리티 메소드입니다.
        Alert alert = new Alert(alertType);
        alert.setTitle(message[0]); //테이블 선택여부
        alert.setHeaderText(message[1]); // 항목 미체크
        alert.setContentText(message[2]); // 테이블 뷰에서 항목을 체크해주세요.
        alert.showAndWait();
    }

    public static LocalDate getDatePicker(String albumDate) {
        System.out.println(albumDate);

        int year = Integer.parseInt(albumDate.substring(0, 4));
        int month = Integer.parseInt(albumDate.substring(5, 7));
        int day = Integer.parseInt(albumDate.substring(8));

        return LocalDate.of(year, month, day);
    }
}
