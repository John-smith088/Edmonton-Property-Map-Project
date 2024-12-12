package com.mycompany.app.controller;

import com.mycompany.app.view.LegendView;
import com.mycompany.app.model.PropertyAssessments;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.text.NumberFormat;

public class LegendController {
    private final LegendView legendView;
    private final PropertyAssessments propertyAssessments;
    private final Long propertyMedian;

    public LegendController(LegendView legendView, PropertyAssessments propertyAssessments) {
        this.legendView = legendView;
        this.propertyAssessments = propertyAssessments;
        this.propertyMedian = propertyAssessments.getMedian();

        initializeLegend();
    }

    private void initializeLegend() {
        // Populate the initial legend content
        updateLegend(propertyAssessments);
    }

    public void updateLegend(PropertyAssessments assessments) {
        if (assessments == null || assessments.getProperties().isEmpty()) {
            legendView.displayNoLegend();
        } else {
            // Refresh legend dynamically based on assessed value center
            long centerValue = assessments.getMedian();
            NumberFormat numberFormat = NumberFormat.getNumberInstance(); // Formatter for numeric values
            legendView.refreshLegend(centerValue, numberFormat);
        }
    }

    public void legendRecenterInputFieldFunctionality(MapController mapController) {
        TextField legendRecenterInputField = legendView.getLegendRecenterInputField();
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        legendRecenterInputField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                    // Get the value from the input field
                    String inputString = legendRecenterInputField.getText().trim();

                    // Error check the value
                    long newLegendRecenterValue;
                    if (!inputString.isEmpty()) {
                        try {
                            newLegendRecenterValue = Long.parseLong(inputString);
                            legendView.refreshLegend(newLegendRecenterValue, numberFormat);
                            mapController.setAssessedValueCenter(newLegendRecenterValue);
                            mapController.displayProperties(propertyAssessments);
                        } catch (NumberFormatException e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid price value. Please enter a valid number.", ButtonType.OK);
                            alert.showAndWait();
                        }
                    } else {
                        newLegendRecenterValue = propertyMedian;
                        legendView.refreshLegend(newLegendRecenterValue, numberFormat);
                        mapController.setAssessedValueCenter(newLegendRecenterValue);
                        mapController.displayProperties(propertyAssessments);
                    }
                }
            }
        });
    }

    public void initializeToggleButton(Button toggleButton, StackPane rootStackPane) {
        toggleButton.setOnAction(event -> {
            if (legendView.getLegendPanel().isVisible()) {
                legendView.getLegendPanel().setVisible(false);
                StackPane.setMargin(toggleButton, new Insets(10));
                toggleButton.setText("Show Legend");
            } else {
                legendView.getLegendPanel().setVisible(true);
                StackPane.setMargin(toggleButton, new Insets(10, 320, 20, 320));
                toggleButton.setText("Hide Legend");
            }
        });
    }
}
