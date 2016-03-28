
package my_snake;

import static javafx.application.Application.launch;
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

/**
 *
 * @author Admin
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Group group = new Group();
        Scene scene = new Scene(group);
        Stage stage1 = new Stage();
        stage1.setScene(scene);
        stage1.setResizable(false);
        stage1.setWidth(500);
        stage1.setHeight(500);
        stage1.setTitle("Snake");

//        Stage stage4 = new Stage();
//       stage4.setTitle("Snake");
// GridPane grid4 = new GridPane();
//grid4.setAlignment(Pos.CENTER);
//grid4.setHgap(10);
//grid4.setVgap(10);
//grid4.setPadding(new Insets(25, 25, 25, 25));
//Text scenetitle4 = new Text("GAME OVER");
//scenetitle4.setId("game_over-text");
//grid4.add(scenetitle4, 0, 0, 2, 1);

 

        Stage stage = new Stage();
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        HBox hBox2 = new HBox();
        Scene scene1 = new Scene(vBox);
        stage.setScene(scene1);
        stage.setWidth(380);
        stage.setHeight(150);
        stage.setResizable(false);
        stage.setTitle("Welcome");
        
        Label label = new Label("Name:                            ");
        Label label1 = new Label("Direction (W-A-S-D):     ");
        TextField textField = new TextField();
        TextField textField1 = new TextField();
        Button button = new Button("OK");
        hBox.getChildren().addAll(label, textField);
        hBox2.getChildren().addAll(label1, textField1);
        vBox.getChildren().addAll(hBox, new Separator(), hBox2, new Separator(), button);

        button.setOnAction(e -> {
            if (textField.getText() != null&& textField1.getText() != null&&
                    (textField1.getText().equals("w")|| textField1.getText().equals("a")||
                    textField1.getText().equals("s")|| textField1.getText().equals("d"))) {
                stage.close();
                e.consume();
               stage1.setScene(scene);
scene.getStylesheets().add
 (Main.class.getResource("Fon.css").toExternalForm());
stage1.show();
                
                stage1.setOnCloseRequest(event -> {
                    //System.exit(0);
                    stage1.close();
//                    stage1.close();
//                    Scene scene4 = new Scene(grid4, 300, 275);
//stage4.setScene(scene4);
//scene4.getStylesheets().add
// (Main.class.getResource("gameover.css").toExternalForm());
//stage4.show();
                });
                new Snake(textField.getText(), textField1.getText(), scene, group);
            }
        });

    

        primaryStage.setTitle("Snake");
 GridPane grid = new GridPane();
grid.setAlignment(Pos.CENTER);
grid.setHgap(10);
grid.setVgap(10);
grid.setPadding(new Insets(25, 25, 25, 25));
Text scenetitle = new Text("Snake");
scenetitle.setId("snake-text");
grid.add(scenetitle, 0, 0, 2, 1);

 Button btn = new Button("              Game             ");
HBox hbBtn = new HBox(10);
hbBtn.setAlignment(Pos.CENTER);
hbBtn.getChildren().add(btn);
grid.add(hbBtn, 1, 2);
btn.setOnAction(new EventHandler<ActionEvent>() {
	    public void handle(ActionEvent event) 
            {   
                primaryStage.close();
		scene1.getStylesheets().add
(Main.class.getResource("Menu.css").toExternalForm());
stage.show();
    
	    }
	});

//Button btn1 = new Button("           Records            ");
//HBox hbBtn1 = new HBox(10);
//hbBtn1.setAlignment(Pos.CENTER);
//hbBtn1.getChildren().add(btn1);
//grid.add(hbBtn1, 1, 3);
//Button btn2 = new Button("           Options            ");
//HBox hbBtn2 = new HBox(10);
//hbBtn2.setAlignment(Pos.CENTER);
//hbBtn2.getChildren().add(btn2);
//grid.add(hbBtn2, 1, 4);
Button btn3 = new Button("               Exit               ");
HBox hbBtn3 = new HBox(10);
hbBtn3.setAlignment(Pos.CENTER);
hbBtn3.getChildren().add(btn3);
grid.add(hbBtn3, 1, 4);
btn3.setOnAction(new EventHandler<ActionEvent>() {
	    public void handle(ActionEvent event) 
            {   
                System.exit(0);
	    }
	});
Scene scene2 = new Scene(grid, 300, 275);
primaryStage.setScene(scene2);
scene2.getStylesheets().add
 (Main.class.getResource("Menu.css").toExternalForm());
primaryStage.show();


    }  

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}