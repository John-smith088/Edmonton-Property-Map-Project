package com.mycompany.app.util;

import javafx.scene.control.Alert;

public class AlertUtil {
    public static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showInformationAlert(String title, String message) {
        showAlert(Alert.AlertType.INFORMATION, title, message);
    }

    public static void showWarningAlert(String title, String message) {
        showAlert(Alert.AlertType.WARNING, title, message);
    }

    public static void showErrorAlert(String title, String message) {
        showAlert(Alert.AlertType.ERROR, title, message);
    }
}
