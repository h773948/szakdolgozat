package app.view;

import app.controller.ExcelReader;
import app.model.Station;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class Controller {
    static String fileURl;
    static File file;


    private static HelpWindow h = new HelpWindow("","please wait!");
    private static String errorMsg = "There is a mistake in the input file";

    public void button(ActionEvent actionEvent) throws IOException {
        errorMsg="There is a mistake in the input file";
        Station station;
        Stage stage=new Stage();
        FileChooser fileChooser= new FileChooser();
        fileChooser.setTitle("Choose Excel");
        file = fileChooser.showOpenDialog(stage);

        try {

        h.show();
            try {
                station = ExcelReader.ReadExcel(file.getAbsolutePath());

            }catch (NullPointerException e){
                errorMsg = "No file/folder was selected";
                throw new IOException();
            }

            h.update("Finished Successfully","Finished");


        }catch (Exception e){
            h.update(errorMsg,"Error");
        }
    }

    public static void setErrorMsg(String errorMsg) {
        Controller.errorMsg = errorMsg;
    }

    public static HelpWindow getH() {
        return h;
    }
}
