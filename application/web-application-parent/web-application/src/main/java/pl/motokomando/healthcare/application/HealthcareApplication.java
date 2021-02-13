package pl.motokomando.healthcare.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import pl.motokomando.healthcare.view.authentication.AuthenticationView;

@SpringBootApplication
@ComponentScan("pl.motokomando.healthcare")
@EntityScan("pl.motokomando.healthcare")
public class HealthcareApplication extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        springContext = SpringApplication.run(HealthcareApplication.class);
    }

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(new AuthenticationView().asParent(), 700, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Healthcare Management - Panel Logowania");
        primaryStage.getIcons().add(new Image(AuthenticationView.class
                .getResourceAsStream("/images/favicon.png")));
        primaryStage.show();
    }

    @Override
    public void stop() {
        springContext.stop();
    }

    public static void main(String[] args) {
        launch(HealthcareApplication.class, args);
    }

}
