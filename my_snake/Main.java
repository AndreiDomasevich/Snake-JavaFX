package my_snake;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class Main extends Application {
    int k = 0;
    Group group;
    Scene scene;
    Stage stage1;
    Stage stage;
    VBox vBox;
    HBox hBox;
    HBox hBox2;
    Scene scene1;
    Label name; 
    Label direction;
    TextField textField;
    TextField textField1;
    Button button, button1, button2;
    GridPane grid;
    Text scenetitle;
    Button btn;
    HBox hbBtn;
    Button btn2;
    HBox hbBtn3;
    Button btn3;
    HBox hbBtn2, hbbutton1, hbbutton2;
    Button btn1;
    Scene scene2;
    HBox hbBtn1;
    BufferedReader br1, br2, br3, br4;
    BufferedWriter bw3, bw2, bw; 
    
                        
                    
                
    public Main() throws IOException{
         
         stage = new Stage();
         vBox = new VBox();
         hBox = new HBox();
         hBox2 = new HBox();
         scene1 = new Scene(vBox, 380, 160);
              name = new Label("Name:                            ");
         direction = new Label("Direction (W-A-S-D):    ");
         textField = new TextField();
         textField1 = new TextField();
         
         button = new Button ("        Easy        ");
         button1 = new Button("      Normal      ");
         button2 = new Button("        Hard        ");
         grid = new GridPane();
         scenetitle = new Text("Snake");
         btn = new Button("              Game             ");
         hbBtn = new HBox(10);
         btn2 = new Button("         Auto Game        ");
         hbBtn2 = new HBox(10);
         btn1 = new Button("               Exit               ");
         btn3 = new Button("             Replay            ");
         hbBtn3 = new HBox(10);
         scene2 = new Scene(grid, 300, 275);
         hbBtn1 = new HBox(10);
         
         
         
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        stage.setScene(scene1);
        stage.setWidth(380);
        stage.setHeight(160);
        stage.setResizable(false);
        stage.setTitle("Welcome");
        
        hBox.getChildren().addAll(name, textField);
        hBox2.getChildren().addAll(direction, textField1);
        vBox.getChildren().addAll(hBox, new Separator(), hBox2, new Separator());
        vBox.setAlignment(Pos.BASELINE_LEFT);
        vBox.getChildren().add(4,button);
        vBox.getChildren().add(5,button1);
        vBox.getChildren().add(6,button2);

        button.setOnAction(e -> {
            if (textField.getText() != null && textField1.getText() != null &&
               (textField1.getText().equals("w")|| textField1.getText().equals("a")||
                textField1.getText().equals("s")|| textField1.getText().equals("d"))) 
            {
      
                stage.close();
                e.consume();
                group = new Group();
                scene = new Scene(group);
                stage1 = new Stage();
                stage1.setScene(scene);
                scene.getStylesheets().add
                (Main.class.getResource("game.css").toExternalForm());
                stage1.setResizable(false);
                stage1.setWidth(500);
                stage1.setHeight(500);
                stage1.setTitle("Snake");
                
                scene.setFill(Color.GRAY);
              
                stage1.show();
                try {
                    new Snake(textField.getText(), textField1.getText(), scene, group, 0, 0, 1, primaryStage, stage1, br1, br3);
                
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        button1.setOnAction(e -> {
            if (textField.getText() != null && textField1.getText() != null &&
               (textField1.getText().equals("w")|| textField1.getText().equals("a")||
                textField1.getText().equals("s")|| textField1.getText().equals("d"))) 
            {
               
                stage.close();
                e.consume();
                 group = new Group();
                scene = new Scene(group);
                stage1 = new Stage();
                stage1.setScene(scene);
                stage1.setResizable(false);
                stage1.setWidth(500);
                stage1.setHeight(500);
                stage1.setTitle("Snake");
                scene.setFill(Color.GRAY);
                stage1.show();
                try {
                    new Snake(textField.getText(), textField1.getText(), scene, group, 0, 1, 2, primaryStage, stage1, br1, br3);
                
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        button2.setOnAction(e -> {
            if (textField.getText() != null && textField1.getText() != null &&
               (textField1.getText().equals("w")|| textField1.getText().equals("a")||
                textField1.getText().equals("s")|| textField1.getText().equals("d"))) 
            {
            
                stage.close();
                e.consume();
                 group = new Group();
                scene = new Scene(group);
                stage1 = new Stage();
                stage1.setScene(scene);
                stage1.setResizable(false);
                stage1.setWidth(500);
                stage1.setHeight(500);
                stage1.setTitle("Snake");
                scene.setFill(Color.GRAY);
                stage1.show();
                try {
                    new Snake(textField.getText(), textField1.getText(), scene, group, 0, 2, 3, primaryStage, stage1, br1, br3);
                
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        primaryStage.setTitle("Snake"); 
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        scenetitle.setId("snake-text");
        grid.add(scenetitle, 0, 0, 2, 1);

        hbBtn2.setAlignment(Pos.CENTER);
        hbBtn2.getChildren().add(btn2);
        grid.add(hbBtn2, 1, 2);
        btn2.setOnAction(e -> {
            primaryStage.close();
                stage.close();
                e.consume();
                 group = new Group();
                scene = new Scene(group);
                stage1 = new Stage();
                stage1.setScene(scene);
                stage1.setResizable(false);
                stage1.setWidth(500);
                stage1.setHeight(500);
                stage1.setTitle("Snake");
                scene.setFill(Color.GRAY);
                stage1.show();
            try {
                new Snake(textField.getText(), textField1.getText(), scene, group, 1, 0, 0, primaryStage, stage1, br1, br3);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        btn3.setOnAction(e -> {
            primaryStage.close();
                stage.close();
                e.consume();
                 group = new Group();
                scene = new Scene(group);
                stage1 = new Stage();
                stage1.setScene(scene);
                stage1.setResizable(false);
                stage1.setWidth(500);
                stage1.setHeight(500);
                stage1.setTitle("Snake");
                scene.setFill(Color.GRAY);
                stage1.show();
                
            try {
                br1 = new BufferedReader(new FileReader(new File
                                ("C:\\Users\\Admin\\workspace\\my_snake\\src\\my_snake\\replay.txt")));
                br3 = new BufferedReader(new FileReader(new File
                                ("C:\\Users\\Admin\\workspace\\my_snake\\src\\my_snake\\feed.txt")));
                br2 = new BufferedReader(new FileReader(new File
                                ("C:\\Users\\Admin\\workspace\\my_snake\\src\\my_snake\\obstacle.txt")));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                String str = br2.readLine();
            int g = Integer.parseInt(str);    
                if(g == 1)
                new Snake(textField.getText(), textField1.getText(), scene, group, 2, 0, 1, primaryStage, stage1, br1, br3);

                if(g == 3)
                new Snake(textField.getText(), textField1.getText(), scene, group, 2, 1, 2, primaryStage, stage1, br1, br3);    
            
                if(g == 5)
                new Snake(textField.getText(), textField1.getText(), scene, group, 2, 2, 3, primaryStage, stage1, br1, br3);    
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 3);
        btn.setOnAction(new EventHandler<ActionEvent>() {
	    public void handle(ActionEvent event) 
            {   
                primaryStage.close();
		scene1.getStylesheets().add
                (Main.class.getResource("Menu.css").toExternalForm());
                stage.show();
	    }
	});

        hbBtn3.setAlignment(Pos.CENTER);
        hbBtn3.getChildren().add(btn3);
        grid.add(hbBtn3, 1, 5);
        
        hbBtn1.setAlignment(Pos.CENTER);
        hbBtn1.getChildren().add(btn1);
        grid.add(hbBtn1, 1, 4);
        btn1.setOnAction(new EventHandler<ActionEvent>() {
	    public void handle(ActionEvent event) 
            {
                System.exit(0);
	    }
	});

        primaryStage.setScene(scene2);
        scene2.getStylesheets().add
        (Main.class.getResource("Menu.css").toExternalForm());
        primaryStage.show();


    }  
    public static void main(String[] args) {
        launch(args);
    }
    
}
