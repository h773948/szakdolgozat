package app.view;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class HelpWindow {
    private String message = "Please wait...";
    private String title = "Running";
    private Stage stage;
    private Label label;

    public HelpWindow(String message, String title) {
        this.message = message;
        this.title = title;


    }
    public void show(){
        stage = new Stage();
        label = new javafx.scene.control.Label();
        label.setText(message);
        stage.setTitle(title);
        VBox vBox= new VBox(label);
        Scene scene= new Scene(vBox,300,100);
        stage.setScene(scene);
        stage.show();

    }
    public void close(){
        this.stage.close();
    }
    public void update(String msg, String title){
        label.setText(msg);
        stage.setTitle(title);
        VBox vBox= new VBox(label);
        Scene scene= new Scene(vBox,300,100);
        stage.setScene(scene);
        stage.show();

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
