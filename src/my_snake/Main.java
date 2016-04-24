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
        Group group;
    	Scene scene;
    	Stage stage_game;
    	Stage stage_name;
    	VBox vBox;
    	HBox hBox;
    	HBox hBox2;
    	Scene scene1;
    	Label name;
    	Label direction;
   	TextField text_name;
   	TextField text_direction;
  	Button btn_easy;
        Button btn_normal;
        Button btn_hard;
    	GridPane grid;
        Text scenetitle;
    	Button btn_game;
    	HBox hbBtn;
    	Button btn_auto;
    	HBox hbBtn2;
    	Button btn_exit;
    	Scene scene2;
    	HBox hbBtn1;

    public Main(){

         group = new Group();
         scene = new Scene(group);
         stage_game = new Stage();
         stage_name = new Stage();
         vBox = new VBox();
         hBox = new HBox();
         hBox2 = new HBox();
         scene1 = new Scene(vBox, 380, 160);
         name = new Label("Name:                            ");
         direction = new Label("Direction (W-A-S-D):     ");
         text_name = new TextField();
         text_direction = new TextField();
         btn_easy = new Button ("        Easy        ");
         btn_normal = new Button("      Normal      ");
         btn_hard = new Button("        Hard        ");
         grid = new GridPane();
         scenetitle = new Text("Snake");
         btn_game = new Button("              Game             ");
         hbBtn_game = new HBox(10);
         btn_auto = new Button("         Auto Game        ");
         hbBtn_auto = new HBox(10);
         btn_exit = new Button("               Exit               ");
         scene2 = new Scene(grid, 300, 275);
         hbBtn_exit = new HBox(10);
    }

    @Override
    public void start(Stage stage_menu){
        stage_game.setScene(scene);
        stage_game.setResizable(false);
        stage_game.setWidth(500);
        stage_game.setHeight(500);
        stage_game.setTitle("Snake");
        stage_name.setScene(scene1);
        stage_name.setWidth(380);
        stage_name.setHeight(150);
        stage_name.setResizable(false);
        stage_name.setTitle("Welcome");
        hBox.getChildren().addAll(label, text_name);
        hBox2.getChildren().addAll(label1, text_direction);
        vBox.getChildren().addAll(hBox, new Separator(), hBox2, new Separator(), btn_easy);

        btn_easy.setOnAction(e -> {
            if (text_name.getText() != null && text_direction.getText() != null &&
               (text_direction.getText().equals("w") || text_direction.getText().equals("a") ||
                text_direction.getText().equals("s") || text_direction.getText().equals("d"))) {

                stage_name.close();
                e.consume();
                stage_game.setScene(scene);
                scene.getStylesheets().add
                (Main.class.getResource("Fon.css").toExternalForm());
                stage_game.show();
                new Snake(text_name.getText(), text_direction.getText(), scene, group);
            }
        });

        stage_menu.setTitle("Snake"); 
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        scenetitle.setId("snake-text");
        grid.add(scenetitle, 0, 0, 2, 1);

        hbBtn_game.setAlignment(Pos.CENTER);
        hbBtn_game.getChildren().add(btn_game);
        grid.add(hbBtn_game, 1, 2);
        btn_game.setOnAction(new EventHandler<ActionEvent>() {
	    public void handle(ActionEvent event) {
                stage_menu.close();
		scene1.getStylesheets().add
                (Main.class.getResource("Menu.css").toExternalForm());
                stage_name.show();
	    }
	});

        hbBtn_exit.setAlignment(Pos.CENTER);
        hbBtn_exit.getChildren().add(btn_exit);
        grid.add(hbBtn1, 1, 4);
        btn_exit.setOnAction(new EventHandler<ActionEvent>() {
	    public void handle(ActionEvent event) {
                System.exit(0);
	    }
	});

        stage_menu.setScene(scene2);
        scene2.getStylesheets().add
        (Main.class.getResource("Menu.css").toExternalForm());
        stage_menu.show();


    }
    public static void main(String[] args) {
        launch(args);
    }
}
