package my_snake;

import javafx.application.Platform;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

import java.io.*;
import java.util.*;

public class Snake {
    private Group group;
    private Scene scene;
    private Map<String, Integer> directionsMapping;
    private Deque<Block> blocks;
    private Deque<Rectangle> rectangles;
    private Rectangle feedRectangle;
    private Rectangle regimeRectangle;
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
    private final int down = 0;
    private final int right = 1;
    private final int up = 2;
    private final int left = 3;
    private final int[] dx = {0, 10, 0, -10};
    private final int[] dy = {10, 0, -10, 0};
    private int inputDirection;
    private String playerName;
    private int bestRecord;
    private String bestPlayerName;
    private Label scoreLabel;
    private int score = 0;
    private Timer timer;
    int k=0;
    public Snake(String playerName, String inputDirection, Scene scene, Group group, int a, int n, int m) {
        obstacle = new Rectangle[m+n];
        obstacle_X = new int[m+n];
        obstacle_Y = new int[m+n];
        this.playerName = playerName;
        this.scene = scene;
        this.group = group;

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

        head = new Block(100, 150);
        tale = new Block(90, 150);

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

        feedRectangle = new Rectangle(10, 10);
        feedRectangle.setFill(Color.RED);
        feedRectangle.setX(-20);
        group.getChildren().add(feedRectangle);

        regimeRectangle = new Rectangle(10, 10);
        regimeRectangle.setFill(Color.GREEN);
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
        addFeed();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> play());
            }
        }, 1, 100);
    }

    private void setBestPlayer() {
        List<String> names = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(new File
			("C:\\Users\\Admin\\Documents\\NetBeansProjects\\My_Snake\\src\\my_snake\\best.txt"))); 
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

    private void play() {
        if (isReload)
            reloadGame(n, m);
        if (isEnd|| isPause)
            return;
        
        addFeed(n, m);
        doesEatFeed();
        addRegime();
        doesEatRegime();
        crossBorder();

        head = blocks.peekFirst();

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
    	    System.exit(0);
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

    private void reloadGame(int n, int m) {
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
        } 
	else {
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
                Platform.runLater(() -> play());
            }
        }, 1, 100);

    }

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

    private void saveRecord() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter
			("C:\\Users\\Admin\\Documents\\NetBeansProjects\\My_Snake\\src\\my_snake\\best.txt", true));

            bw.write(playerName);
            bw.newLine();
            bw.write(String.valueOf(score));
            bw.newLine();

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    private void doesEatFeed() {
        if (isEnd)
            return;

        if (Math.abs(blocks.peekFirst().getX() - feedRectangle.getX()) < 10&&
		Math.abs(blocks.peekFirst().getY() - feedRectangle.getY()) < 10) {
            feedRectangle.setX(1200);
            isFeed = false;
            ++feedCounter2;
            ++score;
            scoreLabel.setText(String.valueOf(score));

            head = blocks.peekFirst();
            Block newHead = new Block(head.getX() + dx[inputDirection], head.getY() + dy[inputDirection]);
            Rectangle headRect = new Rectangle(head.getX(), head.getY(), 10, 10);
            blocks.push(newHead);
            rectangles.push(headRect);
        }
    }

    private void addFeed(int n, int m) {
        if (isEnd)
            return;

        if (isFeed)
            return;

        feedX = Math.abs(random.nextInt()) % 400 + 10;
        feedY = Math.abs(random.nextInt()) % 400 + 60;

        List<Block> blockTemp = new ArrayList<>(blocks);

        for (int i = 0; i < blockTemp.size(); i++)
            if (blockTemp.get(i).getX() < feedX&& blockTemp.get(i).getY() < feedY&& 
		blockTemp.get(i).getX() + 10 > feedX&& blockTemp.get(i).getY() + 10 > feedY) {
                feedX = Math.abs(random.nextInt()) % 400 + 10;
                feedY = Math.abs(random.nextInt()) % 400 + 60;
            }

        feedRectangle.setX(feedX);
        feedRectangle.setY(feedY);

        isFeed = true;
        if(k==0)
        {addObstacle(n, m);k=1;}
    }

    private void addObstacle(int n, int m) {
        if (isEnd)
            return;

        if (!isFeed)
            return;

	for (int i=0; i < n+m; i++){
        	obstacle_X[i] = (Math.abs(random.nextInt()) % 40) * 10;
        	obstacle_Y[i] = (Math.abs(random.nextInt()) % 40) * 10;
        	obstacle[i].setX(obstacle_X[i]);
        	obstacle[i].setY(obstacle_Y[i]);
	}
    }
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
