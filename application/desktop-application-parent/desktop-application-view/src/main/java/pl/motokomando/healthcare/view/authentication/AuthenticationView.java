package pl.motokomando.healthcare.view.authentication;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import pl.motokomando.healthcare.controller.authentication.AuthenticationController;
import pl.motokomando.healthcare.controller.base.BaseController;
import pl.motokomando.healthcare.model.authentication.AuthenticationModel;
import pl.motokomando.healthcare.model.authentication.utils.AuthenticationStatus;
import pl.motokomando.healthcare.model.base.BaseModel;
import pl.motokomando.healthcare.view.base.BaseView;

import static javafx.scene.control.ProgressIndicator.INDETERMINATE_PROGRESS;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import static pl.motokomando.healthcare.model.authentication.utils.AuthenticationStatus.NOT_AUTHENTICATED;

public class AuthenticationView {

    private final AuthenticationModel model;
    private final AuthenticationController controller;

    private Pane loginPane;

    private Button loginButton;
    private Label authenticationStatusLabel;
    private ProgressIndicator progressIndicator;
    private ImageView imageViewLogo;

    public AuthenticationView(AuthenticationModel model, AuthenticationController controller) {
        this.model = model;
        this.controller = controller;
        createPane();
        createContent();
        delegateEventHandlers();
        observeModelAndUpdate();
    }

    public Parent asParent() {
        return loginPane;
    }

    private Stage currentStage() {
        return (Stage) loginPane.getScene().getWindow();
    }

    private void createContent() {
        createLoginButton();
        createAuthenticationStatusLabel();
        createLogoImage();
        createProgressIndicator();
    }

    private void createLogoImage() {
        Image logoImage = new Image(this.getClass().getResourceAsStream("/images/logo.png"));
        imageViewLogo = new ImageView(logoImage);
        imageViewLogo.setLayoutX(10);
        imageViewLogo.setLayoutY(50);
        imageViewLogo.setFitHeight(160);
        imageViewLogo.setFitWidth(680);
        loginPane.getChildren().add(imageViewLogo);
    }

    private void createPane() {
        loginPane = new Pane();
        loginPane.setMaxHeight(USE_PREF_SIZE);
        loginPane.setMaxWidth(USE_PREF_SIZE);
        loginPane.setMinHeight(USE_PREF_SIZE);
        loginPane.setMinWidth(USE_PREF_SIZE);
        loginPane.setPrefHeight(400.0);
        loginPane.setPrefWidth(500.0);
    }

    private void createLoginButton() {
        loginButton = new Button();
        loginButton.setText("Zaloguj się");
        loginButton.setLayoutX(280);
        loginButton.setLayoutY(370);
        loginButton.setFont(new Font(18.0));
        loginPane.getChildren().add(loginButton);
    }

    private void createAuthenticationStatusLabel() {
        authenticationStatusLabel = new Label();
        Label divider = new Label();
        authenticationStatusLabel.setText(NOT_AUTHENTICATED.getDescription());
        authenticationStatusLabel.setLayoutX(190);
        authenticationStatusLabel.setLayoutY(250);
        authenticationStatusLabel.setPrefWidth(300);
        authenticationStatusLabel.setFont(new Font(18.0));
        divider.setLayoutX(190);
        divider.setLayoutY(260);
        divider.setPrefWidth(300);
        divider.setText(StringUtils.repeat(".", 110));
        loginPane.getChildren().add(divider);
        loginPane.getChildren().add(authenticationStatusLabel);
    }

    private void createProgressIndicator() {
        progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);
        progressIndicator.setProgress(INDETERMINATE_PROGRESS);
        progressIndicator.setLayoutX(315);
        progressIndicator.setLayoutY(300);
        loginPane.getChildren().add(progressIndicator);
    }

    private void observeModelAndUpdate() {
        model.authenticationStatus().addListener((obs, oldVal, newVal) -> updateAuthenticationProgress());
    }

    private void delegateEventHandlers() {
        loginButton.setOnMouseClicked(e -> controller.handleLoginButtonClicked());
    }

    private void updateAuthenticationProgress() {
        AuthenticationStatus status = model.getAuthenticationStatus();
        switch (status) {
            case AUTHENTICATION_SUCCESS:
                Platform.runLater(() -> {
                    loginButton.setText("Zalogowano");
                    progressIndicator.setVisible(false);
                });
                openBaseStage();
                break;
            case AUTHENTICATION_FAILURE: case NOT_AUTHENTICATED:
                Platform.runLater(() -> {
                    loginButton.setText("Zaloguj się");
                    loginButton.setDisable(false);
                    progressIndicator.setVisible(false);
                });
                break;
            case AUTHENTICATION_STARTED:
                Platform.runLater(() -> {
                    loginButton.setText("Logowanie...");
                    loginButton.setDisable(true);
                });
                break;
            case USER_IDENTIFICATION:
                Platform.runLater(() -> progressIndicator.setVisible(true));
                break;
        }
        Platform.runLater(() -> authenticationStatusLabel.setText(status.getDescription()));
    }

    private void openBaseStage() {
        BaseModel baseModel = new BaseModel();
        BaseController baseController = new BaseController(baseModel);
        BaseView baseView = new BaseView(baseModel, baseController);
        Platform.runLater(() -> {
            Scene basicScene = new Scene(baseView.asParent(), 1600, 800);
            Stage stage = currentStage();
            stage.setScene(basicScene);
            stage.setTitle("Healthcare Management - Panel Główny");
            stage.show();
        });
    }

}
