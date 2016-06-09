package my_snake;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.ImagePattern;
import javafx.scene.control.ListView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;


import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.File;
import java.io.IOException;

/** class Snake*/
public class Snake {
    private Group group;
    private Scene scene;
    private Map<String, Integer> directionsMapping;
    private Deque<Block> blocks;
    private Deque<Rectangle> rectangles;
    private Rectangle feedRectangle;
    private Rectangle regimeRectangle;
    private Rectangle[] obstacle;
    private int feedX, feedY, regimeX, regimeY;
    private int[] obstacle_X, obstacle_Y;
    private int feedCounter1 = 0;
    private int feedCounter2 = 0;
    private long currentTime;
    private Block head, tale;
    private boolean isFeed, isRegime;
    private boolean isEnd = false;
    private boolean isPause = false;
    private boolean isReload = false;
    private Random random;
    private final int[] dx = {0, 10, 0, -10};
    private final int[] dy = {10, 0, -10, 0};
    private int inputDirection;
    private String playerName;
    private int bestRecord;
    private String bestPlayerName;
    private Label scoreLabel;
    private int score = 0;
    private Timer timer;
    int k = 0;
    int i = 0;
    int t = 0;
    int y = 0;
    int j = 0;
    int s = 0, d = 0, w = 0, a5 = 0;
    int e;
    int k1 = 0, k2 = 0;
    BufferedReader br1, br2, br3, br4;
    BufferedWriter bw4;
    
    class SnakeAnimate extends Thread
    {
    	/** here is the calculation of the positions of the snake segments and the food*/
    	public void run() {
    		updateGu1();
    	}
    }
/** constructor of class Snake
     * @param playerName player name
     * @param inputDirection input direction
     * @param scene scene    
     * @param group group   
     * @param n    vertical obstacle
     * @param a    regime
     * @param stage1    stage
     * @param primaryStage    stage
     * @param br1 bufferedreader1
     * @param br3 bufferedreader3
     * @param m    horizontal obstacle
     */    
public Snake(String playerName, String inputDirection, Scene scene, Group group, int a, int n, int m, Stage primaryStage, Stage stage1, BufferedReader br1, BufferedReader br3) throws IOException {
    obstacle = new Rectangle[m+n];
    obstacle_X = new int[m+n];
    obstacle_Y = new int[m+n];
    this.playerName = playerName;
    this.scene = scene;
    this.group = group;
	Stage saves1;
	saves1 = new Stage();
	saves1.setTitle("game");
   	Pane saveRoot1 = new Pane();
	saveRoot1.setPrefSize(400, 300);
	saveRoot1.setMinSize(400, 300);
	      saveRoot1.setMaxSize(400, 300);
          ObservableList<String> list1 = FXCollections.observableArrayList();
  	      
	      
	      ListView<String> listSaves1 = new ListView<String>();
	      listSaves1.setItems(list1);
	      listSaves1.setStyle("-fx-border-width:3pt;" + "-fx-border-color:red;"
	          + "-fx-font:bold 10pt ItalicT;" + "-fx-text-fill: red;");
	      listSaves1.setPrefSize(400, 300);
	      listSaves1.setMinSize(400, 300);
	      listSaves1.setMaxSize(400, 300);
	      
	    saveRoot1.getChildren().add(listSaves1);
	      Scene saveScene1 = new Scene(saveRoot1);
	      saves1.setScene(saveScene1);
	    
                
                br4 = new BufferedReader(new FileReader(new File
                        ("C:\\Users\\Admin\\workspace\\my_snake\\src\\my_snake\\numbers.txt")));
                e = Integer.valueOf(br4.readLine());
                bw4 = new BufferedWriter(new FileWriter
                		("C:\\Users\\Admin\\workspace\\my_snake\\src\\my_snake\\numbers.txt", false)); 
                
                bw4.write(String.valueOf(e+1));
                bw4.newLine();
                bw4.close();
                
        switch (inputDirection) {
            case "s":
                this.inputDirection = 0;
                break;

            case "d":
                this.inputDirection = 1;
                break;

            case "w":
                this.inputDirection = 2;
                break;

            case "a":
                this.inputDirection = 3;
                break;
        }

        directionsMapping = new HashMap<>();
        directionsMapping.put("S", 0);
        directionsMapping.put("D", 1);
        directionsMapping.put("W", 2);
        directionsMapping.put("A", 3);

        blocks = new ArrayDeque<>();
        rectangles = new ArrayDeque<>();

        head = new Block(10, 0);
        tale = new Block(0, 0);
        

Image image1 = new Image(getClass().getResourceAsStream("podarok.jpg"));
        ImagePattern imagepattern1 = new ImagePattern(image1);
        if (this.inputDirection != 3) {
            blocks.push(tale);
            blocks.push(head);
             
            
            rectangles.push(new Rectangle(tale.getX(), tale.getY(), 10, 10));
            rectangles.push(new Rectangle(head.getX(), head.getY(), 10, 10));
        } else {
            blocks.push(head);
            blocks.push(tale);

            rectangles.push(new Rectangle(head.getX(), head.getY(), 10, 10));
            rectangles.push(new Rectangle(tale.getX(), tale.getY(), 10, 10));
        }

        random = new Random(System.currentTimeMillis());

        isFeed = false;
        isRegime = false;

        scoreLabel = new Label(String.valueOf(score));
        scoreLabel.setLayoutX(240);
        scoreLabel.setLayoutY(0);

        group.getChildren().add(scoreLabel);

        
        
        feedRectangle = new Rectangle(20, 20);
        
        feedRectangle.setFill(imagepattern1);
        feedRectangle.setX(-20);
        group.getChildren().add(feedRectangle);

        regimeRectangle = new Rectangle(10, 10);
        regimeRectangle.setFill(Color.GOLD);
        regimeRectangle.setX(-20);
        group.getChildren().add(regimeRectangle);

        for(int i = 0; i < m; i++)
        {
            obstacle[i] = new Rectangle(150, 10);
            obstacle[i].setFill(Color.CHOCOLATE);
            obstacle[i].setY(-20);
            group.getChildren().add(obstacle[i]);
        }
        for(int i = m; i < m+n; i++)
        {
            obstacle[i] = new Rectangle(10, 150);
            obstacle[i].setFill(Color.CHOCOLATE);
            obstacle[i].setY(-20);
            group.getChildren().add(obstacle[i]);
        }
        setBestPlayer();
        setBoardListener();
        //addFeed(a, n, m, primaryStage);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            /** method run run*/
            public void run() {
                if(a == 0)
                Platform.runLater(() -> {
                    try {
                        play(a, n, m, primaryStage, stage1, saveRoot1, saves1,list1,saveScene1);
                    } catch (IOException ex) {
                        Logger.getLogger(Snake.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                if(a == 1)
                Platform.runLater(() -> {
                    try {
                        play1(a, n, m, primaryStage, stage1, saveRoot1, saves1, list1,saveScene1);
                    } catch (IOException ex) {
                        Logger.getLogger(Snake.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                if(a == 2)
                Platform.runLater(() -> {
                    try {
                        replay(a, n, m, primaryStage, stage1, br1, br3, saveRoot1, saves1, list1,saveScene1);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Snake.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Snake.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        }, 1, 100);
    }
/** 
 * find best player
 */
    private void setBestPlayer() {
        List<String> names = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File
			("C:\\Users\\Admin\\workspace\\my_snake\\src\\my_snake\\best.txt"))); 
            String nameTemp = br.readLine();
            String scoreTemp = br.readLine();

            String second = br.readLine();

            names.add(nameTemp);
            scores.add(Integer.parseInt(scoreTemp));

            while (second != null) {
                names.add(second);
                scores.add(Integer.parseInt(br.readLine()));

                second = br.readLine();
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        bestRecord = 0;
        for (int i = 0; i < scores.size(); i++)
            if (scores.get(i) > bestRecord) {
                bestRecord = scores.get(i);
                bestPlayerName = names.get(i);
            }
    }
/** logic of the game in human regime*/
    private void play(int a, int n, int m, Stage primaryStage, Stage stage1, Pane saveRoot1, Stage saves1,ObservableList<String> list1, Scene saveScene1 ) throws IOException {
        if (isReload)
            {reloadGame(a, n, m, primaryStage, stage1, saveRoot1, saves1, list1, saveScene1);list1.add("Новая игра");}
        if (isEnd) 
        {
        	k2++;
        	if(k2 == 1)
        	{
        		list1.add("Столкновение с препятствием");
        		list1.add("Конец игры");
        		saves1.show();
        	}
        	return;
        }
        if (isPause) {list1.add("Пауза"); return;}
        
        if(n == 0 && m == 1 && k1 == 0) {list1.add("был выбран уровень Easy(1 препятствие)"); k1 ++;}
        if(n == 1 && m == 2 && k1 == 0) {list1.add("был выбран уровень Normal(3 препятствия)"); k1 ++;}
        if(n == 2  && m ==3  && k1 == 0) {list1.add("был выбран уровень Hard(5 препятствий)"); k1 ++;}
        
        addFeed(a, n, m, primaryStage, list1);
        if(k==0)
        {
            addObstacle(n, m);
            k++;
        }
        doesEatFeed(list1);
        addRegime();
        doesEatRegime();
        crossBorder();
          
            head = blocks.peekFirst();
            try {
            BufferedWriter bw = new BufferedWriter(new FileWriter
			("C:\\Users\\Admin\\workspace\\my_snake\\src\\my_snake\\replay.txt", true));

            bw.write(inputDirection);
            
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
            
	      
            if(inputDirection == 0) {s++;System.out.println("Движение вниз");list1.add("Движение вниз");} 
            if(inputDirection == 1) {d++;System.out.println("Движение вправо");list1.add("Движение вправо");} 
            if(inputDirection == 2) {w++;System.out.println("Движение вверх");list1.add("Движение вверх");}
            if(inputDirection == 3) {a5++;System.out.println("Движение влево");list1.add("Движение влево");}
            Block newHead = new Block(head.getX() + dx[inputDirection], head.getY() + dy[inputDirection]);
            Rectangle headRect = new Rectangle(head.getX(), head.getY(), 10, 10);
                
        
        if (isEnd(newHead, n, m)) {
        	list1.add("Столкновение с препятствием.");
        	list1.add("Конец игры.");
            isEnd = true;
            saveRecord();
            showRecord();
            Stage stage4 = new Stage();
       stage4.setTitle("Snake");
 GridPane grid4 = new GridPane();
grid4.setAlignment(Pos.CENTER);
grid4.setHgap(10);
grid4.setVgap(10);
grid4.setPadding(new Insets(25, 25, 25, 25));
Text scenetitle4 = new Text("GAME OVER");
scenetitle4.setId("game_over-text");
grid4.add(scenetitle4, 0, 0, 2, 1);
Scene scene4 = new Scene(grid4, 300, 275);
stage4.setScene(scene4);
scene4.getStylesheets().add
 (Main.class.getResource("gameover.css").toExternalForm());
stage4.show();
stage4.setOnCloseRequest(event -> {
	System.out.println("s" + "  " + s);
    System.out.println("d" + "  " + d);
    System.out.println("w" + "  " + w);
    System.out.println("a" + "  " + a5);
   
    Stage stage6 = new Stage();
    stage6.setTitle("Snake");
    
GridPane grid6 = new GridPane();
grid6.setAlignment(Pos.CENTER);
grid6.setHgap(10);
grid6.setVgap(10);
grid6.setPadding(new Insets(25, 25, 25, 25));
Scene scene6 = new Scene(grid6, 500, 275);
stage6.setScene(scene6);

    
    scene6.getStylesheets().add
    (Main.class.getResource("Menu.css").toExternalForm());
    stage6.setWidth(300);
    stage6.setHeight(200);
    
    Text scenetitle1 = new Text("Max: Up");
    Text scenetitle2 = new Text("Max: Down");
    Text scenetitle3 = new Text("Max: Left");
    Text scenetitle5 = new Text("Max: Right");
    Text scenetitle6 = new Text("Min: Up");
    Text scenetitle7 = new Text("Min: Down");
    Text scenetitle8 = new Text("Min: Left");
    Text scenetitle9 = new Text("Min: Right");
    scenetitle1.setId("result");
    scenetitle2.setId("result");
    scenetitle3.setId("result");
    scenetitle5.setId("result");
    scenetitle6.setId("result");
    scenetitle7.setId("result");
    scenetitle8.setId("result");
    scenetitle9.setId("result");
    if(w > a && w > s && w > d) grid6.add(scenetitle1, 0, 0, 2, 1);
    if(a > w && a > s && a > d) grid6.add(scenetitle3, 0, 0, 2, 1);
    if(s > a && s > w && s > d) grid6.add(scenetitle2, 0, 0, 2, 1);
    if(d > a && d > s && d > w) grid6.add(scenetitle5, 0, 0, 2, 1);

    if(w < a && w < s && w < d) grid6.add(scenetitle6, 0, 0, 10, 10);
    if(a < w && a < s && a < d) grid6.add(scenetitle8, 0, 0, 10, 10);
    if(s < a && s < w && s < d) grid6.add(scenetitle7, 0, 0, 10, 10);
    if(d < a && d < s && d < w) grid6.add(scenetitle9, 0, 0, 10, 10);
    
	stage1.close();
	Stage stage5 = new Stage();
    stage5.setTitle("Snake");
    
GridPane grid5 = new GridPane();
grid5.setAlignment(Pos.CENTER);
grid5.setHgap(10);
grid5.setVgap(10);
grid5.setPadding(new Insets(25, 25, 25, 25));
Scene scene5 = new Scene(grid5, 500, 275);
stage5.setScene(scene5);

    primaryStage.show();
    scene5.getStylesheets().add
    (Main.class.getResource("Menu.css").toExternalForm());
    stage5.setWidth(550);
    stage5.setHeight(350);
    stage5.show();
    Text scenetitle = new Text("Sort selection");
    scenetitle.setId("snake-text");
    grid5.add(scenetitle, 0, 0, 2, 1);
    
    
    HBox hbBtn1_5, hbBtn2_5, hbBtn3_5, hbBtn4_5;
    hbBtn1_5 = new HBox(10);
    hbBtn2_5 = new HBox(10);
    hbBtn3_5 = new HBox(10);
    hbBtn4_5 = new HBox(10);
    
    Button btn1_5, btn2_5, btn3_5, btn4_5;
    btn1_5 = new Button("Scala Sorting(Score)");
    btn2_5 = new Button("Scala Sorting(Name)");
    btn3_5 = new Button("Java Sorting(Score)");
    btn4_5 = new Button("Java Sorting(Name)");
    
    hbBtn1_5.setAlignment(Pos.CENTER);
    hbBtn1_5.getChildren().add(btn1_5);
    grid5.add(hbBtn1_5, 1, 2);
    
    hbBtn2_5.setAlignment(Pos.CENTER);
    hbBtn2_5.getChildren().add(btn2_5);
    grid5.add(hbBtn2_5, 2, 2);
    
    hbBtn3_5.setAlignment(Pos.CENTER);
    hbBtn3_5.getChildren().add(btn3_5);
    grid5.add(hbBtn3_5, 3, 2);

    hbBtn4_5.setAlignment(Pos.CENTER);
    hbBtn4_5.getChildren().add(btn4_5);
    grid5.add(hbBtn4_5, 4, 2);
    
    stage6.show();
    
    btn1_5.setOnAction(e1 -> {
    	
        stage5.close();
            e1.consume();
            reloadGame(a, n, m, primaryStage, stage1, saveRoot1, saves1, list1, saveScene1);
            stage1.close();
            BufferedReader br5;
        	try {
        		
        		int [] array = new int[e];
        		String [] string = new String[e];
        		for(int i = 1; i < e+1; i++ )
        		{
        			br5 = new BufferedReader(new FileReader
        					("C:\\Users\\Admin\\workspace\\my_snake\\src\\my_snake\\res\\results" + String.valueOf(i) + ".txt"));
        		
        			String str = br5.readLine();
        			String str1 = br5.readLine();
        			array[i-1] = Integer.valueOf(str1);
        			string[i-1] = str;
        			
        		}
        		
        		Sort sort = new Sort();
        		sort.sort(string,array);
        		       		
        		Stage saves;
        	      saves = new Stage();
        	      saves.setTitle("Scala Sorting(Score)");
        	      Pane saveRoot = new Pane();
        	      saveRoot.setPrefSize(600, 200);
        	      saveRoot.setMinSize(600, 200);
        	      saveRoot.setMaxSize(600, 200);

        	      ObservableList<String> list = FXCollections.observableArrayList();
        	      for (int i = 1; i < e+1; i++) {
        	        list.add(string[i-1] + "  " + array[i-1]);
        	      }
        	      ListView<String> listSaves = new ListView<String>();
        	      listSaves.setItems(list);
        	      listSaves.setStyle("-fx-border-width:3pt;" + "-fx-border-color:red;"
        	          + "-fx-font:bold 10pt ItalicT;" + "-fx-text-fill: red;");
        	      listSaves.setPrefSize(600, 200);
        	      listSaves.setMinSize(600, 200);
        	      listSaves.setMaxSize(600, 200);

        	      saveRoot.getChildren().add(listSaves);
        	      Scene saveScene = new Scene(saveRoot);
        	      saves.setScene(saveScene);
        	      saves.show();
        	       
        	} catch (Exception e2) {
        		
        		e2.printStackTrace();
        	}

    });
    btn2_5.setOnAction(e1 -> {
    	
        stage5.close();
            e1.consume();
            reloadGame(a, n, m, primaryStage, stage1, saveRoot1, saves1, list1, saveScene1);
            stage1.close();
            BufferedReader br5;
        	try {
        		
        		int [] array = new int[e];
        		String [] string = new String[e];
        		for(int i = 1; i < e+1; i++ )
        		{
        			br5 = new BufferedReader(new FileReader
        					("C:\\Users\\Admin\\workspace\\my_snake\\src\\my_snake\\res\\results" + String.valueOf(i) + ".txt"));
        		
        			String str = br5.readLine();
        			String str1 = br5.readLine();
        			array[i-1] = Integer.valueOf(str1);
        			string[i-1] = str;
        			
        		}
        		
        		Sort sort = new Sort();
        		sort.AZsort(string,array);
        		
        		Stage saves;
        	      saves = new Stage();
        	      saves.setTitle("Scala Sorting(Name)");
        	      Pane saveRoot = new Pane();
        	      saveRoot.setPrefSize(600, 200);
        	      saveRoot.setMinSize(600, 200);
        	      saveRoot.setMaxSize(600, 200);

        	      ObservableList<String> list = FXCollections.observableArrayList();
        	      for (int i = 1; i < e+1; i++) {
        	        list.add(string[i-1] + "  " + array[i-1]);
        	      }
        	      ListView<String> listSaves = new ListView<String>();
        	      listSaves.setItems(list);
        	      listSaves.setStyle("-fx-border-width:3pt;" + "-fx-border-color:red;"
        	          + "-fx-font:bold 10pt ItalicT;" + "-fx-text-fill: red;");
        	      listSaves.setPrefSize(600, 200);
        	      listSaves.setMinSize(600, 200);
        	      listSaves.setMaxSize(600, 200);

        	      saveRoot.getChildren().add(listSaves);
        	      Scene saveScene = new Scene(saveRoot);
        	      saves.setScene(saveScene);
        	      saves.show();
        	    
        	} catch (Exception e2) {
        		
        		e2.printStackTrace();
        	}

    });
    btn3_5.setOnAction(e1 -> {
    	
        stage5.close();
            e1.consume();
            reloadGame(a, n, m, primaryStage, stage1, saveRoot1, saves1, list1, saveScene1);
            stage1.close();
            BufferedReader br5;
        	try {
        		
        		int [] array = new int[e];
        		String [] string = new String[e];
        		for(int i = 1; i < e+1; i++ )
        		{
        			br5 = new BufferedReader(new FileReader
        					("C:\\Users\\Admin\\workspace\\my_snake\\src\\my_snake\\res\\results" + String.valueOf(i) + ".txt"));
        		
        			String str = br5.readLine();
        			String str1 = br5.readLine();
        			array[i-1] = Integer.valueOf(str1);
        			string[i-1] = str;
        			
        		}
        		
        		SortingJ sort_j = new SortingJ();
        		sort_j.SCsort(array, string, 0, e-1);
        		
        		Stage saves;
        	      saves = new Stage();
        	      saves.setTitle("Java Sorting(Score)");
        	      Pane saveRoot = new Pane();
        	      saveRoot.setPrefSize(600, 200);
        	      saveRoot.setMinSize(600, 200);
        	      saveRoot.setMaxSize(600, 200);

        	      ObservableList<String> list = FXCollections.observableArrayList();
        	      for (int i = 1; i < e+1; i++) {
        	        list.add(string[i-1] + "  " + array[i-1]);
        	      }
        	      ListView<String> listSaves = new ListView<String>();
        	      listSaves.setItems(list);
        	      listSaves.setStyle("-fx-border-width:3pt;" + "-fx-border-color:red;"
        	          + "-fx-font:bold 10pt ItalicT;" + "-fx-text-fill: red;");
        	      listSaves.setPrefSize(600, 200);
        	      listSaves.setMinSize(600, 200);
        	      listSaves.setMaxSize(600, 200);

        	      saveRoot.getChildren().add(listSaves);
        	      Scene saveScene = new Scene(saveRoot);
        	      saves.setScene(saveScene);
        	      saves.show();
        	    
        	} catch (Exception e2) {
        		
        		e2.printStackTrace();
        	}
        	
    });
    btn4_5.setOnAction(e1 -> {
    	
        stage5.close();
            e1.consume();
            reloadGame(a, n, m, primaryStage, stage1, saveRoot1, saves1, list1, saveScene1);
            stage1.close();
            BufferedReader br5;
        	try {
        		
        		int [] array = new int[e];
        		String [] string = new String[e];
        		for(int i = 1; i < e+1; i++ )
        		{
        			br5 = new BufferedReader(new FileReader
        					("C:\\Users\\Admin\\workspace\\my_snake\\src\\my_snake\\res\\results" + String.valueOf(i) + ".txt"));
        		
        			String str = br5.readLine();
        			String str1 = br5.readLine();
        			array[i-1] = Integer.valueOf(str1);
        			string[i-1] = str;
        			
        		}
        		
        		SortingJ sort_j = new SortingJ();
        		sort_j.AZSort(array, string, 0, e-1);
        		
        		Stage saves;
        	      saves = new Stage();
        	      saves.setTitle("Java Sorting(Name)");
        	      Pane saveRoot = new Pane();
        	      saveRoot.setPrefSize(600, 200);
        	      saveRoot.setMinSize(600, 200);
        	      saveRoot.setMaxSize(600, 200);

        	      ObservableList<String> list = FXCollections.observableArrayList();
        	      for (int i = 1; i < e+1; i++) {
        	        list.add(string[i-1] + "  " + array[i-1]);
        	      }
        	      ListView<String> listSaves = new ListView<String>();
        	      listSaves.setItems(list);
        	      listSaves.setStyle("-fx-border-width:3pt;" + "-fx-border-color:red;"
        	          + "-fx-font:bold 10pt ItalicT;" + "-fx-text-fill: red;");
        	      listSaves.setPrefSize(600, 200);
        	      listSaves.setMinSize(600, 200);
        	      listSaves.setMaxSize(600, 200);

        	      saveRoot.getChildren().add(listSaves);
        	      Scene saveScene = new Scene(saveRoot);
        	      saves.setScene(saveScene);
        	      saves.show();
        		
        	} catch (Exception e2) {
        		
        		e2.printStackTrace();
        	}
 
    });
    	      
});


            return;
        }
        else {
            blocks.push(newHead);
            rectangles.push(headRect);

            blocks.pollLast();

            updateGui();
        }
        
        }
 
    private void replay(int a, int n, int m, Stage primaryStage, Stage stage1, BufferedReader br1, BufferedReader br3, Pane saveRoot1, Stage saves1, ObservableList<String> list1, Scene saveScene1 ) throws FileNotFoundException, IOException {
        if (isReload)
            reloadGame(a, n, m, primaryStage, stage1, saveRoot1, saves1, list1, saveScene1);
        if (isEnd|| isPause)
            return;
        addFeed1(a, n, m, primaryStage, br3);
        if(t==0)
        {
            addObstacle1(n, m);
            t++;
        }
        doesEatFeed(list1);
        addRegime();
        doesEatRegime();
        crossBorder();
         head = blocks.peekFirst();
        try {
      
      
           inputDirection = br1.read();
      
           Block newHead = new Block(head.getX() + dx[inputDirection], head.getY() + dy[inputDirection]);
           Rectangle headRect = new Rectangle(head.getX(), head.getY(), 10, 10);
               
        
        if (isEnd(newHead, n, m)) {
            isEnd = true;
            saveRecord();
            showRecord();
            Stage stage4 = new Stage();
       stage4.setTitle("Snake");
 GridPane grid4 = new GridPane();
grid4.setAlignment(Pos.CENTER);
grid4.setHgap(10);
grid4.setVgap(10);
grid4.setPadding(new Insets(25, 25, 25, 25));
Text scenetitle4 = new Text("GAME OVER");
scenetitle4.setId("game_over-text");
grid4.add(scenetitle4, 0, 0, 2, 1);
Scene scene4 = new Scene(grid4, 300, 275);
stage4.setScene(scene4);
scene4.getStylesheets().add
 (Main.class.getResource("gameover.css").toExternalForm());
stage4.show();
stage4.setOnCloseRequest(event -> {
    primaryStage.show();
    reloadGame(a, n, m, primaryStage, stage1, saveRoot1, saves1, list1, saveScene1);
    stage1.close();
    
});
            return;
        }
        else {
            blocks.push(newHead);
            rectangles.push(headRect);

            blocks.pollLast();

            updateGui();
        }
        
        
        } 
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
                  
        }

/** logic of the game in computer regime*/          
    private void play1(int a, int n, int m, Stage primaryStage, Stage stage1, Pane saveRoot1, Stage saves1, ObservableList<String> list1, Scene saveScene1) throws IOException {
        
        if (isReload)
            reloadGame(a, n, m, primaryStage, stage1, saveRoot1, saves1, list1,saveScene1);
        if (isEnd || isPause)
            return;
        addFeed(a, n, m, primaryStage, list1);
        
        doesEatFeed(list1);
        addRegime();
        doesEatRegime();
        crossBorder();
            head = blocks.peekFirst();
           
            inputDirection = 1;
            Block newHead = new Block(head.getX() + dx[inputDirection], head.getY() + dy[inputDirection]);
            Rectangle headRect = new Rectangle(head.getX(), head.getY(), 10, 10);
            headRect.setFill(Color.BLACK);
           
                if(head.getX() == feedX) 
                {
                     inputDirection = 2;
                     newHead = new Block(head.getX() + dx[inputDirection], head.getY() + dy[inputDirection]);
                     headRect = new Rectangle(head.getX(), head.getY(), 10, 10);
                     headRect.setFill(Color.BLACK);
                     
                }
        
        if (isEnd(newHead, n, m)) {
            isEnd = true;
            saveRecord();
            showRecord();
            Stage stage4 = new Stage();
       stage4.setTitle("Snake");
 GridPane grid4 = new GridPane();
grid4.setAlignment(Pos.CENTER);
grid4.setHgap(10);
grid4.setVgap(10);
grid4.setPadding(new Insets(25, 25, 25, 25));
Text scenetitle4 = new Text("GAME OVER");
scenetitle4.setId("game_over-text");
grid4.add(scenetitle4, 0, 0, 2, 1);
Scene scene4 = new Scene(grid4, 300, 275);
stage4.setScene(scene4);
scene4.getStylesheets().add
 (Main.class.getResource("gameover.css").toExternalForm());
stage4.show();
stage4.setOnCloseRequest(event -> {
    primaryStage.show();
    reloadGame(a, n, m, primaryStage, stage1, saveRoot1, saves1, list1, saveScene1);
    stage1.close();
});
            return;
        }
        else {
            blocks.push(newHead);
            rectangles.push(headRect);
            blocks.pollLast();
            updateGui();
        }
     
    }
    /**reload game*/
    private void reloadGame(int a, int n, int m, Stage primaryStage, Stage stage1, Pane saveRoot1, Stage saves1,ObservableList<String> list1, Scene saveScene1) {
        if (!isReload)
            return;

        score = 0;
        isReload = false;
        isEnd = false;
        feedCounter1 = 0;
        feedCounter2 = 0;

        List<Rectangle> rectangleList = new ArrayList<>(rectangles);
        for (Rectangle r:rectangleList)
            group.getChildren().remove(r);

        regimeRectangle.setX(-20);

        Deque<Block> blocksTemp = new ArrayDeque<>();
        Deque<Rectangle> rectanglesTemp = new ArrayDeque<>();

        head = new Block(100, 150);
        tale = new Block(90, 150);

        if (inputDirection != 3) {
            blocksTemp.push(tale);
            blocksTemp.push(head);

            
            rectanglesTemp.push(new Rectangle(tale.getX(), tale.getY(), 10, 10));
            rectanglesTemp.push(new Rectangle(head.getX(), head.getY(), 10, 10));
        } else {
            blocksTemp.push(head);
            blocksTemp.push(tale);

            rectanglesTemp.push(new Rectangle(head.getX(), head.getY(), 10, 10));
            rectanglesTemp.push(new Rectangle(tale.getX(), tale.getY(), 10, 10));
        }

        blocks = blocksTemp;
        rectangles = rectanglesTemp;

        group.getChildren().add(rectangles.getFirst());
        group.getChildren().add(rectangles.getLast());
        
        timer.cancel();
        timer = null;
        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(a == 0)
                Platform.runLater(() -> {
                    try {
                        play(a, n, m, primaryStage, stage1, saveRoot1, saves1,list1,saveScene1);
                    } catch (IOException ex) {
                        Logger.getLogger(Snake.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                if(a == 1)
                Platform.runLater(() -> {
                    try {
                        play1(a, n, m, primaryStage, stage1, saveRoot1, saves1, list1, saveScene1);
                    } catch (IOException ex) {
                        Logger.getLogger(Snake.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                if(a == 2)
                Platform.runLater(() -> {
                    try {
                        replay(a, n, m, primaryStage, stage1, br1, br3, saveRoot1, saves1, list1, saveScene1);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(Snake.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(Snake.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                
            }
        }, 1, 100);

    }
/** show record*/
    private void showRecord() {
        Label playerNameLabel = new Label(playerName);
        Label bestPlayerNameLabel = new Label(bestPlayerName);
        Label bestPlayerScoreLabel = new Label(String.valueOf(bestRecord));

        HBox hBox1 = new HBox(playerNameLabel, new Separator(Orientation.VERTICAL), scoreLabel);
        HBox hBox2 = new HBox(bestPlayerNameLabel, new Separator(Orientation.VERTICAL), bestPlayerScoreLabel);
        VBox vBox = new VBox(hBox1, new Separator(), hBox2);
        vBox.setLayoutX(200);
        vBox.setLayoutY(0);
        group.getChildren().add(vBox);
    }
/** save record*/
    private void saveRecord() {
        try {
        	 
                
            BufferedWriter bw = new BufferedWriter(new FileWriter
			("C:\\Users\\Admin\\workspace\\my_snake\\src\\my_snake\\best.txt", true)); 

            bw.write(playerName);
            bw.newLine();
            bw.write(String.valueOf(score));
            bw.newLine();

            bw.close();
            
            BufferedWriter bw5 = new BufferedWriter(new FileWriter
        			("C:\\Users\\Admin\\workspace\\my_snake\\src\\my_snake\\res\\results" + String.valueOf(e) + ".txt", true)); 

                    bw5.write(playerName);
                    bw5.newLine();
                    bw5.write(String.valueOf(score));
                    bw5.newLine();

                   bw5.close();
          //          int qwer = 1;
            //        for(int i = 0; i < 1000; i ++)
              //      {
                //    	BufferedWriter bw6 = new BufferedWriter(new FileWriter
                  //  			("C:\\Users\\Admin\\workspace\\my_snake\\src\\my_snake\\res\\results" + String.valueOf(e+i) + ".txt", true)); 
//
  //                              bw6.write("player" + qwer);
    //                            bw6.newLine();
      //                          bw6.write(String.valueOf(Math.abs(random.nextInt()) % 150));
        //                        bw6.newLine();

          //                      bw6.close();
            //                    qwer++;
                                
              //      }
                    
                    
                    
                  
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
/** update game board*/
    private void updateGui() {
        if (isEnd)
            return;

        group.getChildren().remove(rectangles.peekLast());
        rectangles.pollLast();
        List<Rectangle> rectangleList = new ArrayList<>(rectangles);

        try {
            for (int i = 0; i < rectangleList.size() - 1; i++)
                group.getChildren().add(rectangleList.get(i));
        } catch (Exception e) {

        }
    }
/** check is End*/
    private boolean isEnd(Block newHead, int n, int m) {
        List<Block> blockTemp = new ArrayList<>(blocks);

        for (int i = 0; i < blockTemp.size(); i++)
            if (blockTemp.get(i).getX() == newHead.getX()&& blockTemp.get(i).getY() == newHead.getY())
                return true;

        for (int i = 0; i < m; i++)
        {
        if (newHead.getX() >= obstacle[i].getX()&& newHead.getX() <= obstacle[i].getX() + 150&&(
                (newHead.getY() >= obstacle[i].getY()&& newHead.getY() <= obstacle[i].getY() + 5)||
                (newHead.getY() <= obstacle[i].getY()&& newHead.getY() >= obstacle[i].getY() - 5)))
            return true;
        }
        for (int i = m; i < n+m; i++)
        {
        if (newHead.getY() >= obstacle[i].getY()&& newHead.getY() <= obstacle[i].getY() + 150&&(
                (newHead.getX() >= obstacle[i].getX()&& newHead.getX() <= obstacle[i].getX() + 5)||
                (newHead.getX() <= obstacle[i].getX()&& newHead.getX() >= obstacle[i].getX() - 5)))
            return true;
        }
        
    return false;
    }
/** time*/    
    private void doesEatRegime() {
        if (isEnd)
            return;

        if (Math.abs(blocks.peekFirst().getX() - regimeRectangle.getX()) < 10&&
		Math.abs(blocks.peekFirst().getY() - regimeRectangle.getY()) < 10&&
                System.currentTimeMillis() - currentTime <= 10000) {

            regimeRectangle.setX(1200);
            isRegime = false;
            ++score;
            scoreLabel.setText(String.valueOf(score));

            blocks.pollLast();
            blocks.pollLast();
            blocks.pollLast();

            group.getChildren().remove(rectangles.peekLast());
            rectangles.pollLast();
            group.getChildren().remove(rectangles.peekLast());
            rectangles.pollLast();
            group.getChildren().remove(rectangles.peekLast());
            rectangles.pollLast();
        }

        if (System.currentTimeMillis() - currentTime > 10000) {
            regimeRectangle.setX(1200);
            isRegime = false;
        }
    }
/** add regime*/
    private void addRegime() {
        if (isEnd)
            return;

        if (feedCounter2 - feedCounter1 != 4|| isRegime|| feedCounter2 == 0)
            return;

        feedCounter1 = feedCounter2;

        currentTime = System.currentTimeMillis();

        regimeX = Math.abs(random.nextInt()) % 400 + 10;
        regimeY = Math.abs(random.nextInt()) % 400 + 10;

        List<Block> blockTemp = new ArrayList<>(blocks);

        for (int i = 0; i < blockTemp.size(); i++)
            if (blockTemp.get(i).getX() < regimeX&& blockTemp.get(i).getY() < regimeY&&
		blockTemp.get(i).getX() + 10 > regimeX&& blockTemp.get(i).getY() + 10 > regimeY) {
                regimeX = Math.abs(random.nextInt()) % 400 + 10;
                regimeY = Math.abs(random.nextInt()) % 400 + 10;
            }

        regimeRectangle.setX(regimeX);
        regimeRectangle.setY(regimeY);

        isRegime = true;
    }
/** if eat*/
    private void doesEatFeed(ObservableList<String> list1) {
        if (isEnd)
            return;

        if (Math.abs(blocks.peekFirst().getX() - feedRectangle.getX()) < 20&&
		Math.abs(blocks.peekFirst().getY() - feedRectangle.getY()) < 20) {
            feedRectangle.setX(1200);
            isFeed = false;
            ++feedCounter2;
            ++score;
            scoreLabel.setText(String.valueOf(score));
            list1.add("Подарок был собран. Score = " + score);

            head = blocks.peekFirst();
            Block newHead = new Block(head.getX() + dx[inputDirection], head.getY() + dy[inputDirection]);
            Rectangle headRect = new Rectangle(head.getX(), head.getY(), 10, 10);
          
            blocks.push(newHead);
            rectangles.push(headRect);
           
           
        }
    }

    private void updateGu1(){
 //if (isEnd)
   //     return;

    //group.getChildren().remove(rectangles.peekLast());
    //rectangles.pollLast();
    //List<Rectangle> rectangleList = new ArrayList<>(rectangles);

    //try {
      //  for (int i = 0; i < rectangleList.size() - 1; i++)
        //    group.getChildren().add(rectangleList.get(i));
    //} catch (Exception e) {

    //}
    }
    /** add feed*/
    private void addFeed1(int a, int n, int m, Stage primaryStage, BufferedReader br3) throws IOException {
        if (isEnd)
            return;

        if (isFeed)
            return;


        feedX = Integer.parseInt(br3.readLine());
        feedY = Integer.parseInt(br3.readLine());
        
        
        List<Block> blockTemp = new ArrayList<>(blocks);

        for (int i = 0; i < blockTemp.size(); i++)
            if (blockTemp.get(i).getX() < feedX&& blockTemp.get(i).getY() < feedY&& 
		blockTemp.get(i).getX() + 10 > feedX&& blockTemp.get(i).getY() + 10 > feedY) {
                feedX = Integer.parseInt(br3.readLine());
                feedY = Integer.parseInt(br3.readLine());
            }

        feedRectangle.setX(feedX);
        feedRectangle.setY(feedY);

        isFeed = true;
               
    }
    private void addFeed(int a, int n, int m, Stage primaryStage,ObservableList<String> list1) throws IOException {
        if (isEnd)
            return;

        if (isFeed)
            return;
try {
            BufferedWriter bw2 = new BufferedWriter(new FileWriter
			("C:\\Users\\Admin\\workspace\\my_snake\\src\\my_snake\\feed.txt", true));

        feedX = (Math.abs(random.nextInt()) % 39 + 10)*10;
        feedY = Math.abs(random.nextInt()) % 390 + 60;
        
        list1.add("Появился подарок с координатами:" + " " + feedX + " " + feedY);
        
        String str = String.valueOf(feedX);
        String str1 = String.valueOf(feedY);
        
        bw2.write(str);
        bw2.newLine();
        bw2.write(str1);
        bw2.newLine();
        
        
        List<Block> blockTemp = new ArrayList<>(blocks);

        for (int i = 0; i < blockTemp.size(); i++)
            if (blockTemp.get(i).getX() < feedX&& blockTemp.get(i).getY() < feedY&& 
		blockTemp.get(i).getX() + 10 > feedX&& blockTemp.get(i).getY() + 10 > feedY) {
                feedX = (Math.abs(random.nextInt()) % 39 + 10)*10;
                feedY = Math.abs(random.nextInt()) % 390 + 60;
                String str2 = String.valueOf(feedX);
                String str3 = String.valueOf(feedY);
                list1.add("Появился подарок с координатами:" + " " + feedX + " " + feedY);        
                bw2.write(str2);
                bw2.newLine();
                bw2.write(str3);
                bw2.newLine();
            }
bw2.close();
        feedRectangle.setX(feedX);
        feedRectangle.setY(feedY);    
        
        } catch (IOException e) {
            e.printStackTrace();
        }


        

        isFeed = true;
        
    }
/** add obstacle*/
    private void addObstacle(int n, int m) {
        if (isEnd)
            return;

        if (!isFeed)
            return;

        
for (int i=0; i < n+m; i++)
{
    try {
            BufferedWriter bw1 = new BufferedWriter(new FileWriter
			("C:\\Users\\Admin\\workspace\\my_snake\\src\\my_snake\\obstacle.txt", true));

            
        obstacle_X[i] = (Math.abs(random.nextInt()) % 40) * 10;
        obstacle_Y[i] = (Math.abs(random.nextInt()) % 40) * 10;
        obstacle[i].setX(obstacle_X[i]);
        obstacle[i].setY(obstacle_Y[i]);
if(y == 0)
{
        String str = String.valueOf(n+m);
        bw1.write(str);
        bw1.newLine();
        y ++;
}
        
 String str = String.valueOf(obstacle_X[i]);
        bw1.write(str);
        bw1.newLine();
        str = String.valueOf(obstacle_Y[i]);
        bw1.write(str);
        bw1.newLine();
            
            bw1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
}

    }
    private void addObstacle1(int n, int m) throws IOException {
        if (isEnd)
            return;

        if (!isFeed)
            return;
        
        
    try {
                br2 = new BufferedReader(new FileReader(new File
                                ("C:\\Users\\Admin\\workspace\\my_snake\\src\\my_snake\\obstacle.txt")));
           br2.readLine();
                if(n+m == 1)
                {
                    String str = br2.readLine();
                    String str1 = br2.readLine();
                    int f = Integer.parseInt(str);
                    int f1 = Integer.parseInt(str1);
                    obstacle[0].setX(f);
                    obstacle[0].setY(f1);
                }
                if(n+m == 3)
                {
                    String str = br2.readLine();
                    String str1 = br2.readLine();
                    String str2 = br2.readLine();
                    String str3 = br2.readLine();
                    String str4 = br2.readLine();
                    String str5 = br2.readLine();
                    int f = Integer.parseInt(str);
                    int f1 = Integer.parseInt(str1);
                    int f2 = Integer.parseInt(str2);
                    int f3 = Integer.parseInt(str3);
                    int f4 = Integer.parseInt(str4);
                    int f5 = Integer.parseInt(str5);
                    obstacle[0].setX(f);
                    obstacle[0].setY(f1);
                    obstacle[1].setX(f2);
                    obstacle[1].setY(f3);
                    obstacle[2].setX(f4);
                    obstacle[2].setY(f5);
                }
                if(n+m == 5)
                {
                    String str = br2.readLine();
                    String str1 = br2.readLine();
                    String str2 = br2.readLine();
                    String str3 = br2.readLine();
                    String str4 = br2.readLine();
                    String str5 = br2.readLine();
                    String str6 = br2.readLine();
                    String str7 = br2.readLine();
                    String str8 = br2.readLine();
                    String str9 = br2.readLine();
                    int f = Integer.parseInt(str);
                    int f1 = Integer.parseInt(str1);
                    int f2 = Integer.parseInt(str2);
                    int f3 = Integer.parseInt(str3);
                    int f4 = Integer.parseInt(str4);
                    int f5 = Integer.parseInt(str5);
                    int f6 = Integer.parseInt(str6);
                    int f7 = Integer.parseInt(str7);
                    int f8 = Integer.parseInt(str8);
                    int f9 = Integer.parseInt(str9);
                    obstacle[0].setX(f);
                    obstacle[0].setY(f1);
                    obstacle[1].setX(f2);
                    obstacle[1].setY(f3);
                    obstacle[2].setX(f4);
                    obstacle[2].setY(f5);
                    obstacle[3].setX(f6);
                    obstacle[3].setY(f7);
                    obstacle[4].setX(f8);
                    obstacle[4].setY(f9);
                }
                
                
        } catch (FileNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }


    }
    /** set board*/
    private void setBoardListener() {
        scene.setOnKeyPressed(event -> {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    if (directionsMapping.keySet().contains(event.getCode().toString())) {
                        if ((inputDirection == 0&& event.getCode().toString().equals("W"))||
                                (inputDirection == 1&& event.getCode().toString().equals("A"))||
                                (inputDirection == 2&& event.getCode().toString().equals("S"))||
                                (inputDirection == 3&& event.getCode().toString().equals("D")))
                            return;

                        inputDirection = directionsMapping.get(event.getCode().toString());

                    }

                    SnakeAnimate sA = new SnakeAnimate();
          		  sA.run();
          		  try {
          			sA.join();
          		  } catch (InterruptedException e) {
          			e.printStackTrace();
          			};
                    if (event.getCode().equals(KeyCode.Q)) {
                        if (isPause)
                            isPause = false;
                        else isPause = true;
                    }

                    if (event.getCode().equals(KeyCode.R)) {
                        isReload = true;
                    }
                }
            };
            thread.start();
        });
    }
/** no boards*/
    private void crossBorder() {
        if (isEnd)
            return;

        List<Block> blockTemp = new ArrayList<>(blocks);
        switch (inputDirection) {
            case 0:
                for (int i = 0; i < blockTemp.size(); i++)
                    if (blockTemp.get(i).getY() >= 460) {
                        Block temp = blockTemp.get(i);
                        temp.setY(0);
                        blockTemp.set(i, temp);
                    }
                break;

            case 1:
                for (int i = 0; i < blockTemp.size(); i++)
                    if (blockTemp.get(i).getX() >= 490) {
                        Block temp = blockTemp.get(i);
                        temp.setX(0);
                        blockTemp.set(i, temp);
                    }
                break;

            case 2:
                for (int i = 0; i < blockTemp.size(); i++)
                    if (blockTemp.get(i).getY() <= 0) {
                        Block temp = blockTemp.get(i);
                        temp.setY(450);
                        blockTemp.set(i, temp);
                    }
                break;

            case 3:
                for (int i = 0; i < blockTemp.size(); i++)
                    if (blockTemp.get(i).getX() <= 0) {
                        Block temp = blockTemp.get(i);
                        temp.setX(480);
                        blockTemp.set(i, temp);
                    }
                break;
        }

        Deque<Block> blocksTemp = new ArrayDeque<>();

        for (int i = blockTemp.size() - 1; i >= 0; i--)
            blocksTemp.push(blockTemp.get(i));

        blocks = blocksTemp;
    }
}