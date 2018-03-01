package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.ThreadLocalRandom;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static javafx.scene.text.TextAlignment.CENTER;


public class ViewController implements Initializable{

    public String[] validator;
    @FXML
    private Button nextButton;
    @FXML
    private ImageView image;
    @FXML
    private TextField answerTF;
    @FXML
    private Label answerLabel;
    @FXML
    private Button hintButton;
    @FXML
    private VBox vBox;
    private String path;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        answerLabel.setText("");
        answerTF.setOnAction(e -> validateInput());
        setRanImage();
        nextButton.setOnAction(e -> setRanImage());
        hintButton.setOnAction(e -> showHint());
    }

    private void showHint() {
        String[] hints = new String[4];
        String randFile;
        int randomNum = ThreadLocalRandom.current().nextInt(0,hints.length);
        String hint = "";
        for (int i = 0; i<hints.length; i++){
            randFile = getRandomUrlFileName(getRandomImagePath());
            hints[i] = randFile;
        }
        hints[randomNum] = validator[0];
        for (int i = 0; i<hints.length; i++) {
            hint += " - " + hints[i];
        }
        answerLabel.setTextAlignment(CENTER);
        answerLabel.setText(hint);
    }

    private void validateInput() {
        answerLabel.setDisable(false);
        if (matchesChar()) {
            answerLabel.setText("correct");
            answerTF.clear();
            setRanImage();
        }else {
            answerLabel.setText("false, try again");
        }
    }

    private boolean matchesChar() {
        String part1 = validator[0];
        System.out.println("FileName is: " + part1);
        if (answerTF.getText().equals(part1)){
            return true;
        }else return false;
    }

   private String[] getFileNames(){
        String tempPath = new File("").getAbsolutePath();
        String url = tempPath.concat("/src/symbols/");
        path = url;
        File folder = new File(url);
        File[] listOfFiles = folder.listFiles();
        String[] fileNames = new String[listOfFiles.length];
        for(int i=0; i < listOfFiles.length; i++){
            if (listOfFiles[i].isFile()){
                fileNames[i] = listOfFiles[i].getName();
            }
        }
        return fileNames;
    }

    private String getRandomImagePath(){
        String[] filesNames = getFileNames();
        int randomNum = ThreadLocalRandom.current().nextInt(0,filesNames.length);
        String tempPath = path;
        path = tempPath.concat(filesNames[randomNum]);
        return path;
    }

    private void setRanImage(){
        try {
            String url = getRandomImagePath();
            getUrlFileName(url);
            FileInputStream fIS = new FileInputStream(url);
            Image img = new Image(fIS);
            image.setImage(img);
        }catch (FileNotFoundException ex){
            System.out.println(ex.getMessage());
        }catch (NullPointerException nEx){
            System.out.println(nEx.getMessage());
        }
    }
    private void getUrlFileName(String url) {
        String[] stringSplitter = url.split("/");
        try{

            String fileName = stringSplitter[stringSplitter.length-1];
            validator = fileName.split("\\.");

        }catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
            answerLabel.setText("Datei pfad leider nicht korret bitte sicher stellen das die Bilder in " +
                    url + " liegen");
        }
    }

   private String getRandomUrlFileName(String url) {
        String[] stringSplitter = url.split("/");
        try{
            String fileName = stringSplitter[stringSplitter.length-1];
            String[] finalName = fileName.split("\\.");
            return finalName[0];
        }catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println(ex.getMessage());
            answerLabel.setText("Datei pfad leider nicht korret bitte sicher stellen das die Bilder in " +
                    url + " liegen");
        }
        return null;
    }
}


