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

public class Main extends Application {
        Group group = new Group();
        Scene scene = new Scene(group);
        Stage stage1 = new Stage();
        Stage stage = new Stage();
        VBox vBox = new VBox();
        HBox hBox = new HBox();
        HBox hBox2 = new HBox();
        Scene scene1 = new Scene(vBox);
        Label label = new Label("Name:                            ");
        Label label1 = new Label("Direction (W-A-S-D):     ");
        TextField textField = new TextField();
        TextField textField1 = new TextField();
        Button button = new Button("OK");
        GridPane grid = new GridPane();
        Text scenetitle = new Text("Snake");
        Button btn = new Button("              Game             ");
        HBox hbBtn = new HBox(10);
        Button btn3 = new Button("               Exit               ");
        Scene scene2 = new Scene(grid, 300, 275);
HBox hbBtn3 = new HBox(10);
    @Override
    public void start(Stage primaryStage) {
        
        stage1.setScene(scene);
        stage1.setResizable(false);
        stage1.setWidth(500);
        stage1.setHeight(500);
        stage1.setTitle("Snake");
        stage.setScene(scene1);
        stage.setWidth(380);
        stage.setHeight(150);
        stage.setResizable(false);
        stage.setTitle("Welcome");
        
        hBox.getChildren().addAll(label, textField);
        hBox2.getChildren().addAll(label1, textField1);
        vBox.getChildren().addAll(hBox, new Separator(), hBox2, new Separator(), button);

        button.setOnAction(e -> {
            if (textField.getText() != null&& textField1.getText() != null&&
                    (textField1.getText().equals("w")|| textField1.getText().equals("a")||
                    textField1.getText().equals("s")|| textField1.getText().equals("d"))) 
            {
                stage.close();
                e.consume();
                stage1.setScene(scene);
                scene.getStylesheets().add
                (Main.class.getResource("Fon.css").toExternalForm());
                stage1.show();
                new Snake(textField.getText(), textField1.getText(), scene, group);
            }
        });

        primaryStage.setTitle("Snake"); 
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        scenetitle.setId("snake-text");
        grid.add(scenetitle, 0, 0, 2, 1);

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

        hbBtn3.setAlignment(Pos.CENTER);
        hbBtn3.getChildren().add(btn3);
        grid.add(hbBtn3, 1, 4);
        btn3.setOnAction(new EventHandler<ActionEvent>() {
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