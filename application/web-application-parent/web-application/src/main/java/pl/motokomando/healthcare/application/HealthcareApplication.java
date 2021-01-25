package pl.motokomando.healthcare.application;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import pl.motokomando.healthcare.controller.base.BaseController;
import pl.motokomando.healthcare.model.base.BaseModel;
import pl.motokomando.healthcare.view.base.BaseView;

@SpringBootApplication
@ComponentScan("pl.motokomando.healthcare")
@EntityScan("pl.motokomando.healthcare")
@OpenAPIDefinition(
        info = @Info(
                title = "Healthcare Management OpenAPI",
                description = "OpenAPI Documentation for Healthcare Management",
                version = "1.0")
)
public class HealthcareApplication extends Application {

    private ConfigurableApplicationContext springContext;

    @Override
    public void init() {
        springContext = SpringApplication.run(HealthcareApplication.class);
    }

    @Override
    public void start(Stage primaryStage) {
        BaseView view = new BaseView(new BaseController(new BaseModel()));
        Scene scene = new Scene(view.asParent(), 1600, 800);
        primaryStage.setScene(scene);
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
