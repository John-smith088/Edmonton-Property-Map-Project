package com.mycompany.app.util;

import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

public class LoadingUtil {

    /**
     * Creates a loading container with a progress bar and message.
     *
     * @param message The loading message to display.
     * @param task    The background task to bind the progress bar.
     * @return A VBox containing the loading UI.
     */
    public static VBox createLoadingContainer(String message, Task<?> task) {
        // Create ProgressBar and Label
        ProgressBar progressBar = new ProgressBar();
        Label loadingLabel = new Label(message);

        // Style the label and container
        loadingLabel.getStyleClass().add("loading-label");
        VBox loadingContainer = new VBox(10, loadingLabel, progressBar);
        loadingContainer.getStyleClass().add("loading-container");
        loadingContainer.setAlignment(Pos.CENTER);

        // Bind task progress to the ProgressBar
        progressBar.progressProperty().bind(task.progressProperty());

        return loadingContainer;
    }
}