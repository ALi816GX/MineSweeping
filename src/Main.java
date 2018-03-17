import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {

    TextArea DetailNews = new TextArea();
    TextArea Tip = new TextArea();
    ImageView imageView[][] = new ImageView[11][11];
    Image OpenImageList[][] = new Image[11][11];
    Image WallImageList[][] = new Image[11][11];
    Image FlagImageList[][] = new Image[11][11];
    int MineList[][] = new int[11][11];
    int IsGameOver = 0;              //1 为 继续 // 0 为 死亡
    int IsDifficult = 0;             //1 为 难  //  0 为 易
    int ClickX;
    int ClickY;
    int TotalKill = 0;
    int ZeroIndex[][] = new int[11][11];
    long StartSeconds;
    long EndSeconds;

    void DifficultRandomMineList(int MineList[][]) {         //难
        int F1st;
        int T2nd;
        int judge;
        for (int i = 0; i < 11; i++)
            for (int j = 0; j < 11; j++)
                MineList[i][j] = 0;
        Random r = new Random();

        for (int i = 1; i <= 9; i++) {
            F1st = r.nextInt(9) + 1;
            while (true) {
                judge = r.nextInt(9) + 1;
                if (judge != F1st) {
                    T2nd = judge;
                    break;
                }
            }

            MineList[i][F1st] = -2;
            MineList[i][T2nd] = -2;
        }

        for (int i = 1; i < 10; i++)
            for (int j = 1; j < 10; j++)
                if (MineList[i][j] != -2) {
                    if (MineList[i + 1][j] == -2)
                        MineList[i][j]++;
                    if (MineList[i - 1][j] == -2)
                        MineList[i][j]++;
                    if (MineList[i][j + 1] == -2)
                        MineList[i][j]++;
                    if (MineList[i][j - 1] == -2)
                        MineList[i][j]++;
                    if (MineList[i + 1][j + 1] == -2)
                        MineList[i][j]++;
                    if (MineList[i - 1][j - 1] == -2)
                        MineList[i][j]++;
                    if (MineList[i + 1][j - 1] == -2)
                        MineList[i][j]++;
                    if (MineList[i - 1][j + 1] == -2)
                        MineList[i][j]++;
                }
    }

    void EasyRandomMineList(int MineList[][]) {         //容易
        for (int i = 0; i < 11; i++)
            for (int j = 0; j < 11; j++)
                MineList[i][j] = 0;
        Random r = new Random();

        for (int i = 1; i <= 9; i++) {
            int F1st = r.nextInt(9) + 1;
            MineList[i][F1st] = -2;
        }

        for (int i = 1; i < 10; i++)
            for (int j = 1; j < 10; j++)
                if (MineList[i][j] != -2) {
                    if (MineList[i + 1][j] == -2)
                        MineList[i][j]++;
                    if (MineList[i - 1][j] == -2)
                        MineList[i][j]++;
                    if (MineList[i][j + 1] == -2)
                        MineList[i][j]++;
                    if (MineList[i][j - 1] == -2)
                        MineList[i][j]++;
                    if (MineList[i + 1][j + 1] == -2)
                        MineList[i][j]++;
                    if (MineList[i - 1][j - 1] == -2)
                        MineList[i][j]++;
                    if (MineList[i + 1][j - 1] == -2)
                        MineList[i][j]++;
                    if (MineList[i - 1][j + 1] == -2)
                        MineList[i][j]++;
                }
    }

    void SaveImageList(Image OpenImageList[][], int MineList[][]) {

        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                switch (MineList[i][j]) {
                    case -2:
                        OpenImageList[i][j] = new Image("/ms/9.png");
                        break;
                    case 0:
                        OpenImageList[i][j] = new Image("/ms/0.png");
                        break;
                    case 1:
                        OpenImageList[i][j] = new Image("/ms/1.png");
                        break;
                    case 2:
                        OpenImageList[i][j] = new Image("/ms/2.png");
                        break;
                    case 3:
                        OpenImageList[i][j] = new Image("/ms/3.png");
                        break;
                    case 4:
                        OpenImageList[i][j] = new Image("/ms/4.png");
                        break;
                    case 5:
                        OpenImageList[i][j] = new Image("/ms/5.png");
                        break;
                    case 6:
                        OpenImageList[i][j] = new Image("/ms/6.png");
                        break;
                    case 7:
                        OpenImageList[i][j] = new Image("/ms/7.png");
                        break;
                    case 8:
                        OpenImageList[i][j] = new Image("/ms/8.png");
                        break;

                }
            }
        }

        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                if (i == 0 || i == 10 || j == 0 || j == 10)
                    OpenImageList[i][j] = new Image("/ms/0.png");
            }
        }

    }

    void SaveWallImageList(Image WallImageList[][]) {

        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                WallImageList[i][j] = new Image("/ms/10.png");
            }
        }
    }

    void SaveFlagImageList(Image FlagImageList[][]) {

        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                FlagImageList[i][j] = new Image("/ms/11.png");
            }
        }

    }

    void ZeroIndex() {
        for (int i = 0; i < 11; i++)
            for (int j = 0; j < 11; j++)
                ZeroIndex[i][j] = 0;
    }

    void IsWin() {

        TotalKill = 0;

        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                if (ZeroIndex[i][j] == 1)
                    TotalKill++;

            }
        }

        if (IsDifficult == 1) {
            if (TotalKill == 63) {
                EndSeconds = System.currentTimeMillis();
                DetailNews.setText("\n\n\n    Excellent,you have won the game!!!" + "\n\n\n    Grades : " + (EndSeconds - StartSeconds) / 1000 + " Seconds");
                ZeroIndex();
                IsGameOver = 0;
            }
        } else {
            if (TotalKill == 72) {
                EndSeconds = System.currentTimeMillis();
                DetailNews.setText("\n\n\n    Excellent,you have won the game!!!" + "\n\n\n    Grades : " + (EndSeconds - StartSeconds) / 1000 + " Seconds");
                ZeroIndex();
                IsGameOver = 0;
            }
        }
    }

    void IsLose() {

        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                if (MineList[i][j] == -2)
                    imageView[i][j].setImage(OpenImageList[i][j]);
            }
        }

    }

    void ZeroAction(ImageView[][] imageView, Image[][] OpenImageList, int[][] MineList, int x, int y) {

        if (x >= 1 && x <= 9 && y >= 1 && y <= 9) {
            //if (MineList[x + 1][y] != -2 && MineList[x - 1][y] != -2 && MineList[x][y + 1] != -2 && MineList[x][y - 1] != -2 && MineList[x + 1][y + 1] != -2 && MineList[x - 1][y - 1] != -2 && MineList[x + 1][y - 1] != -2 && MineList[x - 1][y + 1] != -2) {

            if (x >= 1 && x <= 9 && y >= 1 && y <= 9) {
                imageView[x][y].setImage(OpenImageList[x][y]);
                ZeroIndex[x][y] = 1;
            }
            if (x + 1 >= 1 && x + 1 <= 9 && y >= 1 && y <= 9) {
                imageView[x + 1][y].setImage(OpenImageList[x + 1][y]);
                if (MineList[x + 1][y] != 0)
                    ZeroIndex[x + 1][y] = 1;
            }
            if (x - 1 >= 1 && x - 1 <= 9 && y >= 1 && y <= 9) {
                imageView[x - 1][y].setImage(OpenImageList[x - 1][y]);
                if (MineList[x - 1][y] != 0)
                    ZeroIndex[x - 1][y] = 1;
            }
            if (x >= 1 && x <= 9 && y + 1 >= 1 && y + 1 <= 9) {
                imageView[x][y + 1].setImage(OpenImageList[x][y + 1]);
                if (MineList[x][y + 1] != 0)
                    ZeroIndex[x][y + 1] = 1;
            }
            if (x >= 1 && x <= 9 && y - 1 >= 1 && y - 1 <= 9) {
                imageView[x][y - 1].setImage(OpenImageList[x][y - 1]);
                if (MineList[x][y - 1] != 0)
                    ZeroIndex[x][y - 1] = 1;
            }
            if (x + 1 >= 1 && x + 1 <= 9 && y + 1 >= 1 && y + 1 <= 9) {
                imageView[x + 1][y + 1].setImage(OpenImageList[x + 1][y + 1]);
                if (MineList[x + 1][y + 1] != 0)
                    ZeroIndex[x + 1][y + 1] = 1;
            }
            if (x - 1 >= 1 && x - 1 <= 9 && y - 1 >= 1 && y - 1 <= 9) {
                imageView[x - 1][y - 1].setImage(OpenImageList[x - 1][y - 1]);
                if (MineList[x - 1][y - 1] != 0)
                    ZeroIndex[x - 1][y - 1] = 1;
            }
            if (x + 1 >= 1 && x + 1 <= 9 && y - 1 >= 1 && y - 1 <= 9) {
                imageView[x + 1][y - 1].setImage(OpenImageList[x + 1][y - 1]);
                if (MineList[x + 1][y - 1] != 0)
                    ZeroIndex[x + 1][y - 1] = 1;
            }
            if (x - 1 >= 1 && x - 1 <= 9 && y + 1 >= 1 && y + 1 <= 9) {
                imageView[x - 1][y + 1].setImage(OpenImageList[x - 1][y + 1]);
                if (MineList[x - 1][y + 1] != 0)
                    ZeroIndex[x - 1][y + 1] = 1;
            }

            if (MineList[x + 1][y] == 0 && x + 1 >= 1 && x + 1 <= 9 && y >= 1 && y <= 9 && ZeroIndex[x + 1][y] != 1)
                ZeroAction(imageView, OpenImageList, MineList, x + 1, y);
            if (MineList[x - 1][y] == 0 && x - 1 >= 1 && x - 1 <= 9 && y >= 1 && y <= 9 && ZeroIndex[x - 1][y] != 1)
                ZeroAction(imageView, OpenImageList, MineList, x - 1, y);
            if (MineList[x][y + 1] == 0 && x >= 1 && x <= 9 && y + 1 >= 1 && y + 1 <= 9 && ZeroIndex[x][y + 1] != 1)
                ZeroAction(imageView, OpenImageList, MineList, x, y + 1);
            if (MineList[x][y - 1] == 0 && x >= 1 && x <= 9 && y - 1 >= 1 && y - 1 <= 9 && ZeroIndex[x][y - 1] != 1)
                ZeroAction(imageView, OpenImageList, MineList, x, y - 1);
            if (MineList[x + 1][y + 1] == 0 && x + 1 >= 1 && x + 1 <= 9 && y + 1 >= 1 && y + 1 <= 9 && ZeroIndex[x + 1][y + 1] != 1)
                ZeroAction(imageView, OpenImageList, MineList, x + 1, y + 1);
            if (MineList[x - 1][y - 1] == 0 && x - 1 >= 1 && x - 1 <= 9 && y - 1 >= 1 && y - 1 <= 9 && ZeroIndex[x - 1][y - 1] != 1)
                ZeroAction(imageView, OpenImageList, MineList, x - 1, y - 1);
            if (MineList[x + 1][y - 1] == 0 && x + 1 >= 1 && x + 1 <= 9 && y - 1 >= 1 && y - 1 <= 9 && ZeroIndex[x + 1][y - 1] != 1)
                ZeroAction(imageView, OpenImageList, MineList, x + 1, y - 1);
            if (MineList[x - 1][y + 1] == 0 && x - 1 >= 1 && x - 1 <= 9 && y + 1 >= 1 && y + 1 <= 9 && ZeroIndex[x - 1][y + 1] != 1)
                ZeroAction(imageView, OpenImageList, MineList, x - 1, y + 1);
        } else
            return;
    }

    void UnZeroAction() {

        imageView[ClickX][ClickY].setImage(OpenImageList[ClickX][ClickY]);
        ZeroIndex[ClickX][ClickY] = 1;

    }


    @Override
    public void start(Stage primaryStage) throws Exception {


        //组件面板之类的属性配置
        GridPane gridPane = new GridPane();
        Pane pane = new Pane();
        DetailNews.setLayoutX(455);
        DetailNews.setLayoutY(260);
        DetailNews.setMinHeight(190);
        DetailNews.setMaxWidth(250);

        Tip.setLayoutX(455);
        Tip.setLayoutY(5);
        Tip.setMaxHeight(120 + 20);
        Tip.setMaxWidth(250);
        Tip.setText("————————Tips———————\n" + "Tip1:游戏开始先点击 Start键\n" + "Tip2:默认Easy难度\n" + "Tip3:选难度时需要在游戏开始前\n" + "Tip4:左键打开图片,右键标记地雷\n" + "Tip5:游戏规则,数字代表九宫格的雷数\n");


        Button Start = new Button();
        Start.setText("StartGame");
        Start.setLayoutX(455);
        Start.setLayoutY(200 - 20);
        Start.setMinHeight(20);
        Start.setMinWidth(120);

        Button ReStart = new Button();
        ReStart.setText("ReStartGame");
        ReStart.setLayoutX(578);
        ReStart.setLayoutY(200 - 20);
        ReStart.setMinWidth(120);
        ReStart.setMinHeight(20);

        Button DontPlay = new Button();
        DontPlay.setText("Don't Want Continue:Close");
        DontPlay.setLayoutX(455);
        DontPlay.setLayoutY(230);
        DontPlay.setMinWidth(240);
        DontPlay.setMinHeight(20);

        Button Difficult = new Button();
        Difficult.setText("Difficult");
        Difficult.setLayoutX(455);
        Difficult.setLayoutY(170 - 20);
        Difficult.setMinHeight(20);
        Difficult.setMinWidth(120);

        Button Easy = new Button();
        Easy.setText("Easy");
        Easy.setLayoutX(578);
        Easy.setLayoutY(170 - 20);
        Easy.setMinHeight(20);
        Easy.setMinWidth(120);
        //

        //全为0的数组//只需初始化一次
        SaveWallImageList(WallImageList);
        SaveFlagImageList(FlagImageList);

        //选难度初始化
        if (IsDifficult == 0) {          //容易
            EasyRandomMineList(MineList);
            SaveImageList(OpenImageList, MineList);
        } else {                           //难
            DifficultRandomMineList(MineList);
            SaveImageList(OpenImageList, MineList);
        }


        //添加第一层墙  初始化
        for (int i = 0; i < 11; i++)
            for (int j = 0; j < 11; j++) {
                if (i == 0 || i == 10 || j == 0 || j == 10)
                    imageView[i][j] = new ImageView(WallImageList[i][j]);
                else {
                    imageView[i][j] = new ImageView(WallImageList[i][j]);
                    imageView[i][j].setFitHeight(50);
                    imageView[i][j].setFitWidth(50);
                    gridPane.add(imageView[i][j], i, j);
                }
            }
        //


        //调整面板界面

        pane.getChildren().add(gridPane);
        pane.getChildren().addAll(Start, ReStart, DontPlay, DetailNews, Tip, Difficult, Easy);

        gridPane.layoutXProperty();
        gridPane.layoutYProperty();

        Scene scene = new Scene(pane, 700, 550);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MineSweeping");
        primaryStage.setMinWidth(710);
        primaryStage.setMinHeight(475);
        primaryStage.setMaxWidth(710);
        primaryStage.setMaxHeight(475);
        primaryStage.show();

        //

        gridPane.setOnMouseClicked(event -> {

            if (event.getButton() == MouseButton.PRIMARY) {
                ClickX = (int) (Math.floor(event.getX() / 50) + 1);
                ClickY = (int) (Math.floor(event.getY() / 50) + 1);
                if (IsGameOver == 1 && MineList[ClickX][ClickY] != -2) {
                    if (MineList[ClickX][ClickY] != 0) {
                        UnZeroAction();
                        DetailNews.setText("\nGood:kill row " + ClickY + " col " + ClickX + " \n" + DetailNews.getText());
                    } else {
                        ZeroAction(imageView, OpenImageList, MineList, ClickX, ClickY);
                        DetailNews.setText("\nGreat:Fire the hole from row " + ClickY + " col " + ClickX + " \n" + DetailNews.getText());
                    }
                } else if (IsGameOver == 1 && MineList[ClickX][ClickY] == -2) {
                    IsLose();
                    IsWin();
                    DetailNews.setText("\n\n\n       Game over!!!" + "\n\n\n       TotalKill : " + TotalKill + " pieces");
                    IsGameOver = 0;
                }
                IsWin();
            } else if (event.getButton() == MouseButton.SECONDARY) {
                ClickX = (int) (Math.floor(event.getX() / 50) + 1);
                ClickY = (int) (Math.floor(event.getY() / 50) + 1);
                if (IsGameOver == 1) {
                    if (imageView[ClickX][ClickY].getImage() == WallImageList[ClickX][ClickY]) {
                        imageView[ClickX][ClickY].setImage(FlagImageList[ClickX][ClickY]);
                        DetailNews.setText("\nMark Flag: row " + ClickY + " col " + ClickX + " \n" + DetailNews.getText());
                    } else if (imageView[ClickX][ClickY].getImage() == FlagImageList[ClickX][ClickY]) {
                        imageView[ClickX][ClickY].setImage(WallImageList[ClickX][ClickY]);
                        DetailNews.setText("\nCancel Flag: row " + ClickY + " col " + ClickX + " \n" + DetailNews.getText());
                    }
                }
            }

        });


        Start.setOnAction(event -> {
            StartSeconds = System.currentTimeMillis();
            ZeroIndex();
            IsGameOver = 1;
            DetailNews.setText("\nStart Game:");

        });


        ReStart.setOnAction(event -> {

            StartSeconds = System.currentTimeMillis();
            //选难度初始化
            if (IsDifficult == 1) {          //难
                DifficultRandomMineList(MineList);
                SaveImageList(OpenImageList, MineList);
            } else {                           //易
                EasyRandomMineList(MineList);
                SaveImageList(OpenImageList, MineList);
            }
            //

            //铺墙
            for (int i = 1; i < 10; i++)
                for (int j = 1; j < 10; j++)
                    imageView[i][j].setImage(WallImageList[i][j]);
            //

            //StartMinutes = calendar.MINUTE;
            DetailNews.setText("\nReStart Game:");
            IsGameOver = 1;
            ZeroIndex();

        });


        DontPlay.setOnAction(event -> {
            primaryStage.close();
        });

        Difficult.setOnAction(event -> {
            IsDifficult = 1;
        });

        Easy.setOnAction(event -> {
            IsDifficult = 0;
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}
