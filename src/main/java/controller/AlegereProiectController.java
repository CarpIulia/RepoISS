package controller;

import domain.Proiect;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import service.IService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AlegereProiectController {
    ObservableList<String> modelProiecte = FXCollections.observableArrayList();
    @FXML
    private ListView listViewProiecte;
    @FXML
    private CheckBox optionAdaugare;
    @FXML
    private CheckBox optionValidare;

    private IService srv;

    private Stage logInStage;

    private Stage currentStage;

    private Stage adaugareStage;

    private Stage validareStage;

    private Stage progStage;

    private AdaugareBugController adaugareBugController;

    private ValidareBugController validareBugController;

    private ProgramatorWindowController programatorWindowController;

    private boolean isTester;

    private String programator;

    @FXML
    void initialize() {
        listViewProiecte.setItems(modelProiecte);
    }

    private void setWindowType() {
        if(!isTester) {
            this.optionAdaugare.setDisable(true);
            this.optionValidare.setDisable(true);
        }
        else {
            this.optionAdaugare.setDisable(false);
            this.optionValidare.setDisable(false);
        }
    }

    private void initModelProiecte() {
        Iterable<Proiect> proiecte = srv.getAllProiecte();
        List<String> proiecteList = new ArrayList<>();
        for(Proiect p : proiecte)
            proiecteList.add(p.getDenumire());
        modelProiecte.setAll(proiecteList);
    }

    public void setService(IService srv) {
        this.srv = srv;
        initModelProiecte();
    }

    public void setStage(Stage logInStage, Stage currentStage) {
        this.logInStage = logInStage;
        this.currentStage = currentStage;
    }

    public void handleDelogare(ActionEvent actionEvent) {
        currentStage.close();
        logInStage.show();
    }

    public void handleForTester() {
        Proiect proiect = srv.getProiectByName(listViewProiecte.getSelectionModel().getSelectedItem().toString());
        if(!optionAdaugare.isSelected() && !optionValidare.isSelected() || optionAdaugare.isSelected() && optionValidare.isSelected()) {
            Message.showErrorMessage(null, "Trebuie sa selectati una din optiuni.");
            return;
        }
        if(optionAdaugare.isSelected())
            showAdaugareBug(proiect);
        else
            showValidareBug(proiect);
    }

    public void handleForProg() {
        Proiect proiect = srv.getProiectByName(listViewProiecte.getSelectionModel().getSelectedItem().toString());
        showProgramatorWindow(proiect);
    }

    public void handleProiectAles(MouseEvent mouseEvent) {
        if(isTester)
            handleForTester();
        else
            handleForProg();
    }

    private void showValidareBug(Proiect proiect) {
        FXMLLoader validareBugLoader = new FXMLLoader();
        validareBugLoader.setLocation(getClass().getResource("/views/validareBuguriView.fxml"));
        AnchorPane validareBugLayout = null;
        try {
            validareBugLayout = validareBugLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        validareStage = new Stage();
        validareStage.setTitle("Validare bug");
        validareStage.initModality(Modality.WINDOW_MODAL);

        Scene scene = new Scene(validareBugLayout);
        validareStage.setScene(scene);

        this.validareBugController = validareBugLoader.getController();
        validareBugController.setProiect(proiect);
        validareBugController.setService(this.srv);
        validareBugController.setStage(this.currentStage, this.validareStage);

        validareStage.show();
        currentStage.close();
    }

    private void showProgramatorWindow(Proiect proiect) {
        FXMLLoader progWindLoader = new FXMLLoader();
        progWindLoader.setLocation(getClass().getResource("/views/programatorView.fxml"));
        AnchorPane progWindLayout = null;
        try {
            progWindLayout = progWindLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        progStage = new Stage();
        progStage.setTitle("Programator window");
        progStage.initModality(Modality.WINDOW_MODAL);

        Scene scene = new Scene(progWindLayout);
        progStage.setScene(scene);

        this.programatorWindowController = progWindLoader.getController();
        programatorWindowController.setProiect(proiect);
        programatorWindowController.setService(this.srv);
        programatorWindowController.setStage(this.currentStage, this.progStage);
        programatorWindowController.setProgramator(programator);

        progStage.show();
        currentStage.close();
    }

    private void showAdaugareBug(Proiect proiect) {
        FXMLLoader adaugareBugLoader = new FXMLLoader();
        adaugareBugLoader.setLocation(getClass().getResource("/views/adaugareBugView.fxml"));
        AnchorPane adaugareBugLayout = null;
        try {
            adaugareBugLayout = adaugareBugLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        adaugareStage = new Stage();
        adaugareStage.setTitle("Adaugare bug");
        adaugareStage.initModality(Modality.WINDOW_MODAL);

        Scene scene = new Scene(adaugareBugLayout);
        adaugareStage.setScene(scene);

        this.adaugareBugController = adaugareBugLoader.getController();
        adaugareBugController.setProiect(proiect);
        adaugareBugController.setService(this.srv);
        adaugareBugController.setStage(this.currentStage, this.adaugareStage);

        adaugareStage.show();
        currentStage.close();
    }

    public void setUtilizator(String numeUtilizator) {
        if(numeUtilizator.contains("_prog")) {
            isTester = false;
            this.programator = numeUtilizator;
        }
        else
            isTester = true;
        setWindowType();
    }
}
