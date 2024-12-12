package com.mycompany.app.controller;

import com.mycompany.app.model.PropertyAssessment;
import com.mycompany.app.model.PropertyAssessments;
import com.mycompany.app.view.StatisticsView;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import java.text.NumberFormat;

public class StatisticsController {
    private final StatisticsView statisticsView;
    private final PropertyAssessments propertyAssessments;

    public StatisticsController(StatisticsView statisticsView, PropertyAssessments propertyAssessments) {
        this.statisticsView = statisticsView;
        this.propertyAssessments = propertyAssessments;

        updateStatistics(propertyAssessments); // Initialize with all data
    }

    public void updateStatistics(PropertyAssessments filteredAssessments) {
        if (filteredAssessments == null || filteredAssessments.getProperties().isEmpty()) {
            statisticsView.displayNoStatistics();
        } else {
            statisticsView.updateStatistics(
                    filteredAssessments.getNumberOfRecords(),
                    filteredAssessments.getMinValue(),
                    filteredAssessments.getMaxValue(),
                    filteredAssessments.getRange(),
                    filteredAssessments.getMean(),
                    filteredAssessments.getMedian()
            );
        }
    }

    public void displayPropertyInfo(PropertyAssessment property) {
        if (property == null) {
            statisticsView.getPropertyInfoArea().setText("No property information available.");
        } else {
            statisticsView.getPropertyInfoArea().setText(String.format(
                    "Account Number: %s%n" +
                            "Address: %s%n%n" +
                            "Garage: %s%n%n" +
                            "Assessment Value: $%s%n%n" +
                            "Neighborhood: %s%n%n" +
                            "Assessment Class: %s%n%n" +
                            "Latitude: %f%n" +
                            "Longitude: %f",
                    property.getAccountID(),
                    property.getAddress(),
                    property.getGarage(),
                    NumberFormat.getCurrencyInstance().format(property.getAssessedValue()),
                    property.getNeighborhood().getNeighborhoodName(),
                    property.getAssessmentClass(),
                    property.getLocation().getLat(),
                    property.getLocation().getLng()
            ));
        }
    }

    public void displayNoStatistics() {
        statisticsView.displayNoStatistics();
    }

    public void initializeToggleButton(Button toggleButton, StackPane rootStackPane) {
        toggleButton.setOnAction(event -> {
            if (statisticsView.getStatisticsPanel().isVisible()) {
                statisticsView.getStatisticsPanel().setVisible(false);
                StackPane.setMargin(toggleButton, new Insets(10));
                toggleButton.setText("Show Statistics");
            } else {
                statisticsView.getStatisticsPanel().setVisible(true);
                StackPane.setMargin(toggleButton, new Insets(10, 320, 0, 320));
                toggleButton.setText("Hide Statistics");
            }
        });
    }
}
