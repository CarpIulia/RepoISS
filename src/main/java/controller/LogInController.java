package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.IService;

import java.awt.*;
import java.io.IOException;

public class LogInController {
    @FXML
    private TextField textFieldNumeUtilizator;
    @FXML
    private PasswordField passwordFieldParola;

    private AlegereProiectController alegereProiectController;

    private IService srv;

    private Stage alegereProiectStage;

    private Stage currentStage;

    @FXML
    public void initialize() {
        FXMLLoader alegereProiectLoader = new FXMLLoader();
        alegereProiectLoader.setLocation(getClass().getResource("/views/alegereProiectView.fxml"));
        AnchorPane alegereProiectLayout = null;
        try {
            alegereProiectLayout = alegereProiectLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        alegereProiectStage = new Stage();
        alegereProiectStage.setTitle("Alegere proiect");
        alegereProiectStage.initModality(Modality.WINDOW_MODAL);

        Scene scene = new Scene(alegereProiectLayout);
        alegereProiectStage.setScene(scene);

        this.alegereProiectController = alegereProiectLoader.getController();
    }

    public void setService(IService srv) {
        this.srv = srv;
    }

    public void setStage(Stage primaryStage) {
        this.currentStage = primaryStage;
    }

    public void handleConectare(ActionEvent actionEvent) {
        String numeUtilizator = textFieldNumeUtilizator.getText();
        String parola = passwordFieldParola.getText();
        if(srv.checkUsernameAndPassword(numeUtilizator, parola)) {
            showAlegereProiect(numeUtilizator);
            this.currentStage.close();
            textFieldNumeUtilizator.clear();
            passwordFieldParola.clear();
        }
        else
        {
            Message.showErrorMessage(null, "Nume de utilizator sau parola incorecta!");
            textFieldNumeUtilizator.clear();
            passwordFieldParola.clear();
        }
    }

    private void showAlegereProiect(String numeUtilizator) {
        alegereProiectController.setUtilizator(numeUtilizator);
        alegereProiectController.setService(this.srv);
        alegereProiectController.setStage(this.currentStage, this.alegereProiectStage);

        alegereProiectStage.show();
    }
}
