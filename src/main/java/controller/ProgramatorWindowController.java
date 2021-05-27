package controller;

import domain.Bug;
import domain.GradUrgenta;
import domain.Proiect;
import domain.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.IService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ProgramatorWindowController {
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
    private TextField textFieldStatus;
    @FXML
    private TextField textFieldGradUrgenta;
    @FXML
    private TextField textFieldProgramator;
    @FXML
    private TextArea textAreaDescriere;
    @FXML
    private Button buttonIncepe;
    @FXML
    private Button buttonAbandoneaza;
    @FXML
    private Button buttonFinalizeaza;

    private Stage alegereProiectStage;

    private Stage currentStage;

    private IService srv;

    private Proiect proiect;

    private String programator;

    @FXML
    private void initialize() {
        columnDescriere.setCellValueFactory(new PropertyValueFactory<Bug, String>("descriere"));
        columnGradUrgenta.setCellValueFactory(new PropertyValueFactory<Bug, GradUrgenta>("gradUrgenta"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<Bug, Status>("status"));

        tableViewBuguri.setItems(modelBuguri);
        disableButtons();

    }

    public void setProiect(Proiect proiect) {
        this.proiect = proiect;
    }

    public void setService(IService srv) {
        this.srv = srv;
        initModelBuguri();
    }

    public void setStage(Stage alegereProiectStage, Stage cuurentStage) {
        this.alegereProiectStage = alegereProiectStage;
        this.currentStage = cuurentStage;
    }

    private void initModelBuguri() {
        Iterable<Bug> buguri = srv.getBuguriProiect(proiect.getId());
        List<Bug> bugList = StreamSupport.stream(buguri.spliterator(), false)
                .collect(Collectors.toList());
        modelBuguri.setAll(bugList);
    }

    public void clearTextFileds() {
        this.textFieldStatus.clear();
        this.textFieldProgramator.clear();
        this.textAreaDescriere.clear();
        this.textFieldGradUrgenta.clear();
    }

    public void disableButtons() {
        this.buttonIncepe.setDisable(true);
        this.buttonAbandoneaza.setDisable(true);
        this.buttonFinalizeaza.setDisable(true);
    }

    public void handleBugClicked() {
        Bug bug = (Bug)tableViewBuguri.getSelectionModel().getSelectedItem();
        if(bug != null) {
            this.textFieldStatus.setText(bug.getStatus().toString());
            this.textFieldGradUrgenta.setText(bug.getGradUrgenta().toString());
            this.textFieldProgramator.setText(bug.getNumeProgramator());
            this.textAreaDescriere.setText(bug.getDescriere());
        }
        if(bug.getNumeProgramator() != null && bug.getNumeProgramator().equals(programator)) {
            if(bug.getStatus() == Status.Rezolvat) {
                disableButtons();
            }
            else {
                this.buttonAbandoneaza.setDisable(false);
                this.buttonFinalizeaza.setDisable(false);
                this.buttonIncepe.setDisable(true);
            }
        }
        else {
            if(bug.getNumeProgramator() == null) {
                this.buttonAbandoneaza.setDisable(true);
                this.buttonFinalizeaza.setDisable(true);
                this.buttonIncepe.setDisable(false);
            }
            else {
                this.buttonAbandoneaza.setDisable(false);
                this.buttonFinalizeaza.setDisable(false);
                this.buttonIncepe.setDisable(false);
            }
        }
    }

    public void handleIncepeRezolvarea(ActionEvent actionEvent) {
        Bug bug = (Bug)tableViewBuguri.getSelectionModel().getSelectedItem();
        if(bug != null) {
            if(this.textFieldProgramator.getText() == null)
                Message.showErrorMessage(null, "Trebuie sa introduceti numele de utilizator!");
            else {
                bug.setStatus(Status.InAsteptare);
                bug.setNumeProgramator(this.programator);
                srv.updateBug(bug);
                initModelBuguri();
                clearTextFileds();
                disableButtons();
            }
        }
        else
            Message.showErrorMessage(null, "Trebuie sa selectati un bug!");
    }

    public void handleAbandoneazaRezolvarea(ActionEvent actionEvent) {
        Bug bug = (Bug)tableViewBuguri.getSelectionModel().getSelectedItem();
        if(bug != null) {
                bug.setStatus(Status.Nerezolvat);
                bug.setNumeProgramator(null);
                srv.updateBug(bug);
                initModelBuguri();
                this.textFieldProgramator.clear();
                this.textAreaDescriere.clear();
                this.textFieldStatus.clear();
                this.textFieldGradUrgenta.clear();
                initModelBuguri();
                clearTextFileds();
                disableButtons();
        }
        else
            Message.showErrorMessage(null, "Trebuie sa selectati un bug!");
    }

    public void setProgramator(String programator) {
        this.programator = programator;
    }

    public void handleInapoi(ActionEvent actionEvent) {
        this.alegereProiectStage.show();
        this.currentStage.close();
    }

    public void handleFinalizeazaRezolvarea(ActionEvent actionEvent) {
        Bug bug = (Bug)tableViewBuguri.getSelectionModel().getSelectedItem();
        if(bug != null) {
            bug.setStatus(Status.InCursDeValidare);
            srv.updateBug(bug);
            initModelBuguri();
            this.textFieldProgramator.clear();
            this.textAreaDescriere.clear();
            this.textFieldStatus.clear();
            this.textFieldGradUrgenta.clear();
            initModelBuguri();
            clearTextFileds();
            disableButtons();
        }
        else
            Message.showErrorMessage(null, "Trebuie sa selectati un bug!");
    }
}
