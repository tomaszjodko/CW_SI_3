package fisher.view;

import fisher.common.GeneratorFisher;
import fisher.common.SystemDecyzyjny;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;

import java.io.File;

public class fisherController {
    private GeneratorFisher generator = new GeneratorFisher();

    @FXML
    private GridPane rootNode;

    @FXML
    private Button btnChooseFile;

    @FXML
    private TextField tfFilePath;

    @FXML
    private TextArea taInput;

    @FXML
    private TextArea taOutput;


    @FXML
    private void initialize() {
        btnChooseFile.setOnAction(this::chooseFile);
    }

    @FXML
    private void chooseFile(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz system decyzyjny");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File selectedFile = fileChooser.showOpenDialog(rootNode.getScene().getWindow());
        if(selectedFile != null) {
            this.generator.setSd(new SystemDecyzyjny(selectedFile));
            tfFilePath.setText(selectedFile.getPath());
            this.taInput.setText(generator.getSd().toString());
            this.generator.generuj();
            taOutput.setText(this.generator.toString());
        }
    }
}
