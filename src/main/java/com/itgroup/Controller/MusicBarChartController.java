package com.itgroup.Controller;

import com.itgroup.bean.Music;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MusicBarChartController implements Initializable {
    @FXML private BarChart<String,Double> barChart; // BarChart,

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("재생 시간 그래프를 그립니다.");
    }


    public void makeChartAll(ObservableList<Music> dataList) {
        for (Music bean : dataList) {
            List<XYChart.Data<String,Double>> lists = new ArrayList<>();



            lists.add(new XYChart.Data<String, Double>(bean.getMusicName(), bean.getStime()));

            XYChart.Series<String, Double> series = new XYChart.Series<>();
            series.setName(String.valueOf(bean.getStime()));
            series.setData(FXCollections.observableArrayList(lists));
            barChart.getData().add(series);
            barChart.setBarGap(5); //막대 간의 간격 조절
            barChart.setCategoryGap(59); // 카테고리간의 간격을 조절
            barChart.setPrefSize(800,600);


        }
    }
}
