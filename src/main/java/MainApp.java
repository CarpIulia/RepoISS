import controller.LogInController;
import domain.Bug;
import domain.GradUrgenta;
import domain.Proiect;
import domain.Status;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.*;
import service.IService;
import service.ServiceAppBuguri;

import java.io.IOException;

public class MainApp extends Application {
    private IService service;
    private SessionFactory sessionFactory;

    public void initialize() {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure() // configures settings from hibernate.cfg.xml
                .build();
        try {
            this.sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            System.err.println("Exception " + e);
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        initialize();

        IRepoUtilizatori repoUtilizatori = new RepoUtilizatori(sessionFactory);
        IRepoBuguri repoBuguri = new RepoBuguri(sessionFactory);
        IRepoProiecte repoProiecte = new RepoProiecte(sessionFactory);

        service = new ServiceAppBuguri(repoBuguri, repoProiecte, repoUtilizatori);

        initView(primaryStage);
        primaryStage.show();
    }

    private void initView(Stage primaryStage) throws IOException {
        FXMLLoader logInLoader = new FXMLLoader();
        logInLoader.setLocation(getClass().getResource("/views/autentificareView.fxml"));
        AnchorPane logInLayout = logInLoader.load();
        primaryStage.setScene(new Scene(logInLayout));

        LogInController logInController = logInLoader.getController();
        logInController.setService(service);
        logInController.setStage(primaryStage);
    }
}
