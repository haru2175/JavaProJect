package com.itgroup.Controller;

import com.itgroup.Utility.Paging;
import com.itgroup.Utility.Utility;
import com.itgroup.bean.Music;
import com.itgroup.dao.MusicDao;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.sound.sampled.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URL;

import java.util.*;

public class MusicController implements Initializable {
    private MusicDao dao = null;

    @FXML private ImageView imageView;
    private String mode = null; // 필드 검색을 위한 mode 변수
    private ObservableList<Music> dataList = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.dao = new MusicDao();

        setTableColumns(); // 테이블 칼럼
        setPagination(0); // 최초 시작시 1 페이지

        musicTable.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    playSelectedMusic(newValue);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        btnMusicFile.setOnAction(event -> {// 버튼 이벤트 실행
            try {
                onStart(event);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });



        ChangeListener<Music> tableListener = new ChangeListener<Music>() {
            @Override
            public void changed(ObservableValue<? extends Music> observableValue, Music oldValue, Music newValue) {
                if (newValue != null) {
                    System.out.println("노래 정보");
                    System.out.println(newValue);

                    String imageFile = "";
                    if (newValue.getImage() != null) {
                        imageFile = Utility.IMAGE_PATH + newValue.getImage().trim();
                    } else {
                        imageFile = Utility.IMAGE_PATH + "noimage.jpg";
                    }

                    Image someImage = null;
                    if (getClass().getResource(imageFile) == null) {
                        imageView.setImage(null);
                    } else {
                        someImage = new Image(getClass().getResource(imageFile).toString());
                        imageView.setImage(someImage);
                    }
                }
            }
        };
        musicTable.getSelectionModel().selectedItemProperty().addListener(tableListener);

        setContextMenu(); // 마우스 우측 클릭할때 나오는 메뉴
        }



    private void setContextMenu() {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem02 = new MenuItem("노래 비율 그래프");
        MenuItem menuItem03 = new MenuItem("재생 시간 그래프");

        contextMenu.getItems().addAll(menuItem02, menuItem03);

        musicTable.setContextMenu(contextMenu);

        menuItem02.setOnAction((event -> {
            try {
                makePicChart();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }));
        menuItem03.setOnAction(event -> {
            try {
                makeBarChartAll();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void makeBarChartAll() throws Exception {
        System.out.println("재생시간 막대 그래프 그리기");

        FXMLLoader fxmlLoader = this.getFxmlLoader("BarChart.fxml");
        Parent parent = fxmlLoader.load();

        MusicBarChartController controller = fxmlLoader.getController();
        controller.makeChartAll(musicTable.getItems());
        this.showModal(parent);
    }


    private void makePicChart() throws Exception {
        System.out.println("재생시간 비율 그래프");

        FXMLLoader fxmlLoader = this.getFxmlLoader("PieChart.fxml");
        Parent parent = fxmlLoader.load();

        MusicPieChartController controller = fxmlLoader.getController();
        controller.makePieChart(musicTable.getItems());
        this.showModal(parent);
    }


    private FXMLLoader getFxmlLoader(String fxmlName) throws Exception {
        Parent parent = null;

        String fileName = Utility.FXML_PATH + fxmlName;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fileName));

        return fxmlLoader;
    }
    private void showModal(Parent parent) {
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    @FXML
    private Pagination pagination;
    private void setPagination(int pageIndex) {  // 페이징 관련 설정
        pagination.setPageFactory(this::createPage); //cratePage 메소드를 사용하여 페이지네이션 만들어준다.
        pagination.setCurrentPageIndex(pageIndex);

        imageView.setImage(null);
    }

    private Node createPage(Integer pageIndex) {
        int totalCount = 0;
        totalCount = dao.getTotalCount(this.mode);

        Paging pageInfo = new Paging(String.valueOf(pageIndex+1), "10", totalCount, null, this.mode, null);

        pagination.setPageCount(pageInfo.getTotalPage()); // 페이지 하단 보여주는곳

        fillTableData(pageInfo); // 데이터를 채운다.

        VBox vbox = new VBox(musicTable);

        return vbox;
    }
    @FXML private Label pageStatus;
    private void fillTableData(Paging pageInfo) {// 테이블 뷰에 목록을 채워줍니다.

        List<Music> musicList = dao.getPaginationData(pageInfo);

        dataList = FXCollections.observableArrayList(musicList);

        musicTable.setItems(dataList); // setItems 콤보박스,테이블뷰,리스트뷰에 쓰인다.
        pageStatus.setText(pageInfo.getPagingStatus()); // 페이지 총 47건[1/5]
    }
    @FXML
    private TableView<Music> musicTable; // 테이블 목록을 보여주는 뷰 //fx:id TableView와 같아야한다.
    private void setTableColumns() {
        String[] fields = {"pnum","musicName","artist","album","ballad","stime"};
        String[] colNames = {"노래 번호","노래 제목", "가수", "앨범 발매일", "장르", "재생 시간"};

        TableColumn tableColumn = null;

        for (int i = 0; i < fields.length; i++) {
            tableColumn = musicTable.getColumns().get(i);
            tableColumn.setText(colNames[i]); // 컬럼을 한글 이름으로 변경
            // Music 빈 클래스의 인스턴스 변수 이름을 셋팅하면 데이터가 자동으로 바인딩됩니다. (fields 변수와 같아야한다.)
            tableColumn.setCellValueFactory(new PropertyValueFactory<>(fields[i]));
            tableColumn.setStyle("-fx-alignment:center;"); // 모든 셀 데이터를 가운데 정렬하기
        }
    }

    public void onInsert(ActionEvent event) throws Exception {
        String fxmlFile = Utility.FXML_PATH + "MusicInsert.fxml";
        URL url = getClass().getResource(fxmlFile);
        FXMLLoader fxmlLoader = new FXMLLoader(url);

        Parent container = fxmlLoader.load();
        Scene scene = new Scene(container);
        Stage stage = new Stage();
        String myStyle = getClass().getResource(Utility.CSS_PATH +"MusicStyle.css").toString() ;
        scene.getStylesheets().add(myStyle); // 스타일링 파일 지정하기
        stage.initModality(Modality.APPLICATION_MODAL); // 창이 안닫히면 다른거 못 누르게 한다.
        stage.setScene(scene); // 창
        stage.setResizable(false); // 화면 창 크기 조절 불가능 기능
        stage.setTitle("음원을 등록합니다.");
        stage.showAndWait(); // 창 뛰우고 대기

        setPagination(0); // 화면 갱신
    }

    public void onUpdate(ActionEvent event) throws Exception {
        int idx = musicTable.getSelectionModel().getSelectedIndex();

        if (idx >= 0) {
            System.out.println("선택된 색인 번호 : " + idx);

            String fxmlFile = Utility.FXML_PATH + "MusicUpdate.fxml";
            URL url = getClass().getResource(fxmlFile);
            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent container = fxmlLoader.load();

            Music bean = musicTable.getSelectionModel().getSelectedItem();
            MusicUpdateController controller = fxmlLoader.getController();
            controller.setBean(bean);

            Scene scene = new Scene(container);
            Stage stage = new Stage();
            String myStyle = getClass().getResource(Utility.CSS_PATH +"MusicStyle.css").toString() ;
            scene.getStylesheets().add(myStyle); // 스타일링 파일 지정하기
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("음원을 수정합니다.");
            stage.showAndWait();

            setPagination(0);
        } else {
            String[] message = new String[]{"선택 확인", "미선택", "수정하고자 하는 노래를 선택해주세요."};
            Utility.showAlert(Alert.AlertType.ERROR, message);
        }
    }

    public void onDelete(ActionEvent event) {
        int idx = musicTable.getSelectionModel().getSelectedIndex();

        if (idx >= 0) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // 예,아니오
            alert.setTitle("삭제 확인 메세지");
            alert.setHeaderText("삭제 항목 선택 대화 상자");
            alert.setContentText("이 항목을 정말로 삭제하시겠습니까?");

            Optional<ButtonType> response = alert.showAndWait();
            if (response.get() == ButtonType.OK) {
                Music bean = musicTable.getSelectionModel().getSelectedItem();
                int pnum = bean.getPnum();
                int cnt = -1;
                cnt = dao.deleteData(pnum);
                if (cnt != -1) {
                    System.out.println("삭제 성공");
                    setPagination(0);
                } else {
                    System.out.println("삭제 실패");
                }
            } else {
                System.out.println("취소 하였습니다.");
            }
        } else {
            String[] message = new String[]{"삭제할 목록 확인", "삭제할 대상 선택", "삭제할 행을 선택해주세요."};
            Utility.showAlert(Alert.AlertType.WARNING,message);
        }
    }

    public void onSaveFile(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        Button myBtn = (Button) event.getSource();
        Window window = myBtn.getScene().getWindow();
        File savedFile = chooser.showSaveDialog(window);

        if (savedFile != null) {
            FileWriter fw = null;
            BufferedWriter bw = null;

            try {
                fw = new FileWriter(savedFile);
                bw = new BufferedWriter(fw);

                for (Music bean : dataList) {
                    bw.write(bean.toString());
                    bw.newLine();
                }
                System.out.println("파일 저장이 완료 되었습니다.");
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bw != null) {
                        bw.close();
                    }
                    if (fw != null) {
                        fw.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        } else {
            System.out.println("파일 저장이 취소 되었습니다.");
        }
    }

    public void onClosing(ActionEvent event) {
        System.out.println("프로그램을 종료합니다.");
        Platform.exit();
    }

    @FXML private ComboBox<String> fieldSearch;
    public void choiceSelect(ActionEvent event) {
        String ballad = fieldSearch.getSelectionModel().getSelectedItem();
        System.out.println("검색 장르 : [" + ballad + "]");

        this.mode = Utility.getBalladName(ballad, "value");
        System.out.println("필드 검색 모드 : [" + this.mode + "]");

        setPagination(0);
    }

    private void playSelectedMusic(Music music) throws Exception { //음악을 선택하면 그 노래가 재생됩니다.
        boolean musicFile = true;
        if (musicFile == true) {
            playMusic(Utility.DATA_PATH + music.getPnum() + ".wav");
        } else {
            System.out.println("재생할 노래 파일이 없습니다.");
        }

    }

    //노래 재생하기위해 만들어놓은거....왜안되냐 진짜로 6시간걸림 ...
    @FXML private Button btnMusicFile;
    private Clip clip;
    private boolean isPlaying = false; //현재 재생 상태를 나타내는 변수
    public void onStart(ActionEvent event) throws Exception{
        if (isPlaying) {
            stopMusic(); // 음악이 재생 중이면 멈춤
        } else {
            playMusic(Utility.DATA_PATH+"14.wav");
            //        playMusic("C:/work/intellij_project/MusicExam/src/main/resources/com/itgroup/data/mnet.wav");

        }
    }
    private void stopMusic() {
        if (clip != null && clip.isRunning()) { //clip.isRunning 은 재생 중인지 여부 확인
            clip.stop();
            clip.close();
            isPlaying = false; // 멈춤 상태로 설정
            System.out.println("노래를 멈춥니다.");

        }
    }

    private void playMusic(String playmusic) throws Exception{
        File file = new File(playmusic);
        if (clip != null && clip.isOpen()) {
            clip.close();
        }
        if (file == null) {
            System.out.println("음악 파일이 없습니다.");
        }
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
        isPlaying = true; // 재생 상태로 설정
        System.out.println("노래를 재생합니다.");

    }
}

