package com.mycompany.app.controller;

import com.mycompany.app.model.PropertyAssessment;
import com.mycompany.app.model.PropertyAssessments;
import com.mycompany.app.view.FilterPanelView;
import javafx.scene.control.Alert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FilterController {
    private final FilterPanelView filterPanelView;
    private final PropertyAssessments propertyAssessments;
    private final MapController mapController;
    private final StatisticsController statisticsController;
    private final LegendController legendController;

    public FilterController(FilterPanelView filterPanelView, PropertyAssessments propertyAssessments,
                            MapController mapController, StatisticsController statisticsController,
                            LegendController legendController) {
        this.filterPanelView = filterPanelView;
        this.propertyAssessments = propertyAssessments;
        this.mapController = mapController;
        this.statisticsController = statisticsController;
        this.legendController = legendController;

        setupFilterListeners();
    }

    private void setupFilterListeners() {
        filterPanelView.getFilterDropdown().setOnAction(event -> {
            String selectedFilter = filterPanelView.getFilterDropdown().getValue();
            populateValues(selectedFilter);
        });

        filterPanelView.getApplyFilterButton().setOnAction(event -> applyFilter());

        filterPanelView.getRemoveFilterButton().setOnAction(event -> clearFilter());

        // Add listener for account search button
        filterPanelView.getAccountSearchButton().setOnAction(event -> searchByAccountNumber());
    }

    private void searchByAccountNumber() {
        String accountNumberStr = filterPanelView.getAccountSearchInput().getText().trim();

        if (accountNumberStr.isEmpty()) {
            showAlert("Invalid Input", "Please enter an account number.");
            return;
        }

        try {
            int accountNumber = Integer.parseInt(accountNumberStr);
            PropertyAssessment property = propertyAssessments.getProperties().stream()
                    .filter(p -> p.getAccountID() == accountNumber)
                    .findFirst()
                    .orElse(null);

            if (property == null) {
                showAlert("No Results", "No property found with the given account number.");
                statisticsController.displayPropertyInfo(null); // Clear property info
            } else {
                // Update map, statistics, and legend with the single property
                mapController.highlightProperty(property);
                legendController.updateLegend(new PropertyAssessments(List.of(property)));
                statisticsController.displayNoStatistics();
                statisticsController.displayPropertyInfo(property); // Display property info
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Account number must be a valid number.");
        }
    }

    private void applyFilter() {
        String selectedFilter = filterPanelView.getFilterDropdown().getValue();
        String filterValue = filterPanelView.getValueDropdown().getValue();

        if (selectedFilter == null || filterValue == null) {
            showAlert("Invalid Filter", "Please select a filter and value.");
            return;
        }

        List<PropertyAssessment> filteredProperties = propertyAssessments.getProperties().stream()
                .filter(property -> matchesFilter(property, selectedFilter, filterValue))
                .collect(Collectors.toList());

        if (filteredProperties.isEmpty()) {
            showAlert("No Results", "No properties match the selected filter.");
            statisticsController.updateStatistics(new PropertyAssessments(List.of()));
            legendController.updateLegend(new PropertyAssessments(List.of())); // Update empty legend
        } else {
            PropertyAssessments filteredAssessments = new PropertyAssessments(filteredProperties);
            mapController.displayProperties(filteredAssessments);
            statisticsController.updateStatistics(filteredAssessments);
            legendController.updateLegend(filteredAssessments); // Update legend with filtered data
        }
    }

    private boolean matchesFilter(PropertyAssessment property, String selectedFilter, String filterValue) {
        return switch (selectedFilter) {
            case "Neighborhood" -> property.getNeighborhood().getNeighborhoodName().equals(filterValue);
            case "Ward" -> property.getNeighborhood().getWard().equals(filterValue);
            case "Assessment Class" -> property.getAssessmentClass().toString().contains(filterValue);
            default -> false;
        };
    }

    private void clearFilter() {
        mapController.displayProperties(propertyAssessments);
        statisticsController.updateStatistics(propertyAssessments);
        legendController.updateLegend(propertyAssessments); // Reset legend to original data
    }

    private void populateValues(String selectedFilter) {
        filterPanelView.getValueDropdown().getItems().clear();

        switch (selectedFilter) {
            case "Neighborhood" -> filterPanelView.getValueDropdown().getItems().addAll(
                    propertyAssessments.getProperties().stream()
                            .map(p -> p.getNeighborhood().getNeighborhoodName())
                            .distinct()
                            .sorted()
                            .toList()
            );
            case "Ward" -> filterPanelView.getValueDropdown().getItems().addAll(
                    propertyAssessments.getProperties().stream()
                            .map(p -> p.getNeighborhood().getWard())
                            .distinct()
                            .sorted()
                            .toList()
            );
            case "Assessment Class" -> filterPanelView.getValueDropdown().getItems().addAll(
                    propertyAssessments.getProperties().stream()
                            .map(p -> Arrays.asList(p.getAssessmentClass().getAssessmentClass1(), p.getAssessmentClass().getAssessmentClass2()))
                            .flatMap(List::stream)
                            .sorted()
                            .distinct()
                            .toList()
            );
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
