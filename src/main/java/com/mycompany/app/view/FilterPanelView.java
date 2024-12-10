package com.mycompany.app.view;

import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class FilterPanelView {
    private final Accordion filterPanel;
    private final ComboBox<String> filterDropdown;
    private final ComboBox<String> valueDropdown;
    private final Button applyFilterButton;
    private final Button removeFilterButton;
    private final TextField accountSearchInput;
    private final Button accountSearchButton;

    public FilterPanelView() {
        // Initialize the UI components
        filterPanel = new Accordion();
        filterDropdown = new ComboBox<>();
        valueDropdown = new ComboBox<>();
        applyFilterButton = new Button("Apply Filter");
        removeFilterButton = new Button("Remove Filters");
        accountSearchInput = new TextField();
        accountSearchButton = new Button("Search");

        initializeFilterPanel();
    }

    private void initializeFilterPanel() {
        // Create the group filter pane
        TitledPane groupFilterPane = configureGroupPane();

        // Create the account Search pane
        TitledPane accountFilterPane = configureAccountSearchPane();

        // Add the filter pane to the accordion
        filterPanel.getPanes().addAll(groupFilterPane, accountFilterPane);

        // Set accordion preferences
        filterPanel.setPrefWidth(250);
        filterPanel.setMinWidth(200);
        filterPanel.setMaxWidth(300);
        filterPanel.setMaxHeight(Region.USE_PREF_SIZE);
        filterPanel.setPrefHeight(Region.USE_COMPUTED_SIZE);
        applyStyling();
    }

    private void applyStyling() {
        filterPanel.getStyleClass().add("property-group-pane");
        applyFilterButton.getStyleClass().add("filter-button");
        removeFilterButton.getStyleClass().add("remove-filter-button");
        accountSearchButton.getStyleClass().add("account-search-button");
    }

    private TitledPane configureGroupPane() {
        // Configure group filter dropdown
        filterDropdown.setPromptText("Select a filter");
        filterDropdown.getItems().addAll("Neighborhood", "Assessment Class", "Ward");

        // Configure value dropdown
        valueDropdown.setPromptText("Select a value");

        // Create content for filter pane
        VBox filterContent = new VBox(
                10,
                filterDropdown,
                valueDropdown,
                applyFilterButton,
                removeFilterButton
        );

        return new TitledPane("Filter Properties", filterContent);
    }

    private TitledPane configureAccountSearchPane() {
        Label accountSearchLabel = new Label("Search by Account Number:");
        accountSearchInput.setPromptText("Enter the account number");

        VBox accountGroupContent = new VBox(10, accountSearchLabel, accountSearchInput, accountSearchButton);

        return new TitledPane("Search by Account Number", accountGroupContent);
    }

    // Getter for the filter panel
    public Accordion getFilterPanel() {
        return filterPanel;
    }

    // Getters for interactive components
    public ComboBox<String> getFilterDropdown() {
        return filterDropdown;
    }

    public ComboBox<String> getValueDropdown() {
        return valueDropdown;
    }

    public Button getApplyFilterButton() {
        return applyFilterButton;
    }

    public Button getRemoveFilterButton() {
        return removeFilterButton;
    }

    public TextField getAccountSearchInput() {
        return accountSearchInput;
    }

    public Button getAccountSearchButton() {
        return accountSearchButton;
    }
}
