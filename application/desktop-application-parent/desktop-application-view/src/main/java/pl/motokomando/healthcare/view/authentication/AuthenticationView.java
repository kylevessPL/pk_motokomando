package pl.motokomando.healthcare.view.authentication;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import pl.motokomando.healthcare.controller.authentication.AuthenticationController;
import pl.motokomando.healthcare.model.authentication.AuthenticationModel;
import pl.motokomando.healthcare.model.authentication.utils.AuthenticationStatus;
import pl.motokomando.healthcare.view.base.BaseView;

import static javafx.scene.control.ProgressIndicator.INDETERMINATE_PROGRESS;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import static pl.motokomando.healthcare.model.authentication.utils.AuthenticationStatus.NOT_AUTHENTICATED;

public class AuthenticationView {

    private AuthenticationModel authenticationModel;

    private AuthenticationController controller;

    private Pane authenticationPane;

    private Button loginButton;
    private Label authenticationStatusLabel;
    private ProgressIndicator progressIndicator;
    private ImageView imageViewLogo;

    public AuthenticationView() {
        initModel();
        setController();
        createPane();
        createContent();
        delegateEventHandlers();
        observeModelAndUpdate();
    }

    public Parent asParent() {
        return authenticationPane;
    }

    private Stage currentStage() {
        return (Stage) authenticationPane.getScene().getWindow();
    }

    private void initModel() {
        authenticationModel = new AuthenticationModel();
    }

    private void setController() {
        controller = new AuthenticationController(authenticationModel);
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
        authenticationPane.getChildren().add(imageViewLogo);
    }

    private void createPane() {
        authenticationPane = new Pane();
        authenticationPane.setMaxHeight(USE_PREF_SIZE);
        authenticationPane.setMaxWidth(USE_PREF_SIZE);
        authenticationPane.setMinHeight(USE_PREF_SIZE);
        authenticationPane.setMinWidth(USE_PREF_SIZE);
        authenticationPane.setPrefHeight(400.0);
        authenticationPane.setPrefWidth(500.0);
    }

    private void createLoginButton() {
        loginButton = new Button();
        loginButton.setText("Zaloguj się");
        loginButton.setLayoutX(280);
        loginButton.setLayoutY(370);
        loginButton.setFont(new Font(18.0));
        authenticationPane.getChildren().add(loginButton);
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
        authenticationPane.getChildren().add(divider);
        authenticationPane.getChildren().add(authenticationStatusLabel);
    }

    private void createProgressIndicator() {
        progressIndicator = new ProgressIndicator();
        progressIndicator.setVisible(false);
        progressIndicator.setProgress(INDETERMINATE_PROGRESS);
        progressIndicator.setLayoutX(315);
        progressIndicator.setLayoutY(300);
        authenticationPane.getChildren().add(progressIndicator);
    }

    private void observeModelAndUpdate() {
        authenticationModel.authenticationStatus().addListener((obs, oldVal, newVal) -> updateAuthenticationProgress());
    }

    private void delegateEventHandlers() {
        loginButton.setOnMouseClicked(e -> controller.handleLoginButtonClicked());
    }

    private void updateAuthenticationProgress() {
        AuthenticationStatus status = authenticationModel.getAuthenticationStatus();
        switch (status) {
            case AUTHENTICATION_SUCCESS:
                Platform.runLater(() -> {
                    loginButton.setText("Zalogowano");
                    progressIndicator.setVisible(false);
                });
                openBaseScene();
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

    private void openBaseScene() {
        Scene scene = new Scene(new BaseView(authenticationModel).asParent(), 1600, 800);
        Platform.runLater(() -> {
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            Stage stage = currentStage();
            stage.setScene(scene);
            stage.setX((screenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((screenBounds.getHeight() - stage.getHeight()) / 2);
            stage.setTitle("Healthcare Management - Panel Główny");
            stage.show();
        });
    }

}
