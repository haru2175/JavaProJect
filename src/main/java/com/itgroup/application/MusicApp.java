package com.itgroup.application;

import com.itgroup.Utility.Utility;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MusicApp extends Application { //애플리케이션과 연결
    @Override
    public void start(Stage stage) throws Exception {
        String fxmlFile = Utility.FXML_PATH + "MusicApp.fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));//URL

        Parent container = fxmlLoader.load(); // 승급
        Scene scene = new Scene(container);

        String myStyle = getClass().getResource(Utility.CSS_PATH +"MusicStyle.css").toString() ;
        scene.getStylesheets().add(myStyle); // 스타일링 파일 지정하기

        stage.setTitle("Music Manager");
        stage.setScene(scene);
        stage.setResizable(false); //화면 창 크기 조절 불가능
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
