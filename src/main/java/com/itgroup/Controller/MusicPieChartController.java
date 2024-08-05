package com.itgroup.Controller;

import com.itgroup.bean.Music;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MusicPieChartController implements Initializable {
    @FXML
    private PieChart pieChart;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("파이 그래프를 그립니다.");
    }

    public void makePieChart(ObservableList<Music> dataList) {
        List<PieChart.Data> pieChartData = new ArrayList<>();
        for (Music bean : dataList) {
            pieChartData.add(new PieChart.Data(bean.getMusicName(), bean.getStime()));

        }
        pieChart.setData(FXCollections.observableArrayList(pieChartData));
        pieChart.setTitle("재생시간 비율 그래프");

    }
}
