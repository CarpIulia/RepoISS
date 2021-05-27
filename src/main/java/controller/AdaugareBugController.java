package controller;

import domain.Bug;
import domain.GradUrgenta;
import domain.Proiect;
import domain.Status;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import service.IService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class AdaugareBugController {
    ObservableList<Bug> modelBuguri = FXCollections.observableArrayList();

    @FXML
    private TableView tableViewBuguri;
    @FXML
    private TableColumn<Bug, Status> columnStatus;
    @FXML
    private TableColumn<Bug, GradUrgenta> columnGradUrgenta;
    @FXML
    private TableColumn<Bug, String> columnDescriere;
    @FXML
    private ComboBox comboBoxGradUrgenta;
    @FXML
    private TextArea textAreaDescriere;

    private Stage currentStage;

    private Stage alegereProiectStage;

    private IService srv;

    private Proiect proiect;

    @FXML
    private void initialize() {
        comboBoxGradUrgenta.setItems(FXCollections.observableArrayList("Mic", "Mediu", "Mare"));

        columnDescriere.setCellValueFactory(new PropertyValueFactory<Bug, String>("descriere"));
        columnGradUrgenta.setCellValueFactory(new PropertyValueFactory<Bug, GradUrgenta>("gradUrgenta"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<Bug, Status>("status"));

        tableViewBuguri.setItems(modelBuguri);

    }

    public void setStage(Stage alegerProiectStage, Stage adaugareStage) {
        this.currentStage = adaugareStage;
        this.alegereProiectStage = alegerProiectStage;
    }

    public void setService(IService srv) {
        this.srv = srv;
        initModelBuguri();
    }

    private void initModelBuguri() {
        Iterable<Bug> buguri = srv.getBuguriProiect(proiect.getId());
        List<Bug> bugList = StreamSupport.stream(buguri.spliterator(), false)
                .collect(Collectors.toList());
        modelBuguri.setAll(bugList);
    }

    public void handleInapoi(ActionEvent actionEvent) {
        this.currentStage.close();
        this.alegereProiectStage.show();
    }

    public void setProiect(Proiect proiect) {
        this.proiect = proiect;
    }

    public void handleAdaugare(ActionEvent actionEvent) {
        if(textAreaDescriere.getText().equals("") || comboBoxGradUrgenta.getSelectionModel().isEmpty()) {
            Message.showErrorMessage(null, "Date invalide.");
            return;
        }

        GradUrgenta gradUrgenta = GradUrgenta.valueOf(comboBoxGradUrgenta.getSelectionModel().getSelectedItem().toString());

        srv.addBug(textAreaDescriere.getText(), Status.Nerezolvat, gradUrgenta, proiect);

        initModelBuguri();

        this.textAreaDescriere.clear();
    }
}
