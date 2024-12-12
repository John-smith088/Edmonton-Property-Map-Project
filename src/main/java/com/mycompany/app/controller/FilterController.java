package com.mycompany.app.controller;

import com.mycompany.app.model.PropertyAssessment;
import com.mycompany.app.model.PropertyAssessments;
import com.mycompany.app.service.PropertyFilterService;
import com.mycompany.app.view.FilterPanelView;
import javafx.scene.control.Alert;

import java.util.List;

public class FilterController {
    private final FilterPanelView filterPanelView;
    private final PropertyAssessments propertyAssessments;
    private final MapController mapController;
    private final StatisticsController statisticsController;
    private final LegendController legendController;
    private final PropertyFilterService propertyFilterService;

    public FilterController(FilterPanelView filterPanelView, PropertyAssessments propertyAssessments,
                            MapController mapController, StatisticsController statisticsController,
                            LegendController legendController, PropertyFilterService propertyFilterService) {
        this.filterPanelView = filterPanelView;
        this.propertyAssessments = propertyAssessments;
        this.mapController = mapController;
        this.statisticsController = statisticsController;
        this.legendController = legendController;
        this.propertyFilterService = propertyFilterService;

        setupFilterListeners();
    }

    private void setupFilterListeners() {
        filterPanelView.getFilterDropdown().setOnAction(event -> {
            String selectedFilter = filterPanelView.getFilterDropdown().getValue();
            populateValues(selectedFilter);
        });

        filterPanelView.getApplyFilterButton().setOnAction(event -> applyFilter());

        filterPanelView.getRemoveFilterButton().setOnAction(event -> clearFilter());

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
                statisticsController.displayPropertyInfo(null);
            } else {
                mapController.highlightProperty(property);
                legendController.updateLegend(new PropertyAssessments(List.of(property)));
                statisticsController.displayNoStatistics();
                statisticsController.displayPropertyInfo(property);
            }
        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Account number must be a valid number.");
        }
    }

    private void applyFilter() {
        String selectedFilter = filterPanelView.getFilterDropdown().getValue();
        String filterValue = filterPanelView.getValueDropdown().getValue();
        String garageFilter = filterPanelView.getSelectedGarageFilter();
        String priceInput = filterPanelView.getPriceInput();
        String priceComparison = filterPanelView.getPriceComparison();

        Long priceValue = null;
        if (!priceInput.isEmpty()) {
            try {
                priceValue = Long.parseLong(priceInput);
            } catch (NumberFormatException e) {
                showAlert("Invalid Input", "Price must be a valid number.");
                return;
            }
        }

        if ((selectedFilter == null || filterValue == null) && garageFilter == null && (priceValue == null || priceComparison == null)) {
            showAlert("Invalid Filter", "Please select at least one filter.");
            return;
        }

        PropertyAssessments filteredAssessments = propertyAssessments;

        if (selectedFilter != null && filterValue != null) {
            filteredAssessments = propertyFilterService.filterByCriteria(filteredAssessments, selectedFilter, filterValue);
        }

        if (!"All".equals(garageFilter)) {
            filteredAssessments = propertyFilterService.filterByGarage(filteredAssessments, garageFilter);
        }

        if (priceValue != null && priceComparison != null) {
            filteredAssessments = propertyFilterService.filterByPrice(filteredAssessments, priceComparison, priceValue);
        }

        if (filteredAssessments.getProperties().isEmpty()) {
            showAlert("No Results", "No properties match the selected filters.");
            statisticsController.updateStatistics(new PropertyAssessments(List.of()));
            legendController.updateLegend(new PropertyAssessments(List.of()));
        } else {
            mapController.displayProperties(filteredAssessments);
            statisticsController.updateStatistics(filteredAssessments);
            legendController.updateLegend(filteredAssessments);
        }
    }

    private void clearFilter() {
        mapController.displayProperties(propertyAssessments);
        statisticsController.updateStatistics(propertyAssessments);
        legendController.updateLegend(propertyAssessments);
    }

    private void populateValues(String selectedFilter) {
        List<String> values = propertyFilterService.getDistinctValues(propertyAssessments, selectedFilter);
        filterPanelView.getValueDropdown().getItems().clear();
        filterPanelView.getValueDropdown().getItems().addAll(values);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
