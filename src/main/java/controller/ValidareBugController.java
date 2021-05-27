package controller;

import domain.Bug;
import domain.GradUrgenta;
import domain.Proiect;
import domain.Status;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.IService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class ValidareBugController {
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
    private CheckBox checkValidare;
    @FXML
    private CheckBox checkInvalidare;

    private Stage currentStage;

    private Stage alegereProiectStage;

    private IService srv;

    private Proiect proiect;

    @FXML
    private void initialize() {
        columnDescriere.setCellValueFactory(new PropertyValueFactory<Bug, String>("descriere"));
        columnGradUrgenta.setCellValueFactory(new PropertyValueFactory<Bug, GradUrgenta>("gradUrgenta"));
        columnStatus.setCellValueFactory(new PropertyValueFactory<Bug, Status>("status"));

        tableViewBuguri.setItems(modelBuguri);

    }

    public void setStage(Stage alegereProiectStage, Stage currentStage) {
        this.currentStage = currentStage;
        this.alegereProiectStage = alegereProiectStage;
    }

    public void setService(IService srv) {
        this.srv = srv;
        initModelBuguri();
    }

    public void setProiect(Proiect proiect) {
        this.proiect = proiect;
    }

    private void initModelBuguri() {
        Iterable<Bug> buguri = srv.getBuguriProiect(proiect.getId());
        List<Bug> bugList = StreamSupport.stream(buguri.spliterator(), false)
                .filter(b->b.getStatus() == Status.InCursDeValidare)
                .collect(Collectors.toList());
        modelBuguri.setAll(bugList);
    }

    public void handleBugClicked(MouseEvent mouseEvent) {
        Bug bug = (Bug)tableViewBuguri.getSelectionModel().getSelectedItem();
        if(checkInvalidare.isSelected() != false || checkValidare.isSelected() != false) {
            if(checkValidare.isSelected() == true)
                bug.setStatus(Status.Rezolvat);
            else
                bug.setStatus(Status.Invalid);
            srv.updateBug(bug);
            initModelBuguri();
        }
        else
            Message.showErrorMessage(null, "Trebuie sa selectati o optiune!");
    }

    public void handleInapoi(ActionEvent actionEvent) {
        this.currentStage.close();
        this.alegereProiectStage.show();
    }
}
