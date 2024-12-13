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
    private final ToggleGroup garageFilterGroup;
    private final RadioButton allButton;
    private final RadioButton yesButton;
    private final RadioButton noButton;
    private TextField priceInputField;
    private ComboBox<String> priceComparisonDropdown;


    public FilterPanelView() {
        // Initialize the UI components
        filterPanel = new Accordion();
        filterDropdown = new ComboBox<>();
        valueDropdown = new ComboBox<>();
        applyFilterButton = new Button("Apply Filter");
        removeFilterButton = new Button("Remove Filters");
        accountSearchInput = new TextField();
        accountSearchButton = new Button("Search");
        garageFilterGroup = new ToggleGroup();
        allButton = new RadioButton("All");
        yesButton = new RadioButton("Yes");
        noButton = new RadioButton("No");

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

        Label garageLabel = new Label("Garage Filter:");
        allButton.setToggleGroup(garageFilterGroup);
        yesButton.setToggleGroup(garageFilterGroup);
        noButton.setToggleGroup(garageFilterGroup);

        allButton.setSelected(true); // Default selection

        // Create the price filter input and dropdown
        Label priceFilterLabel = new Label("Price Filter:");
        priceInputField = new TextField();
        priceInputField.setPromptText("Enter price (e.g., 100000)");

        priceComparisonDropdown = new ComboBox<>();
        priceComparisonDropdown.getItems().addAll("Under", "Equal", "Above");
        priceComparisonDropdown.setPromptText("Select comparison");

        // Create content for filter pane
        VBox filterContent = new VBox(
                10,
                filterDropdown,
                valueDropdown,
                garageLabel,
                allButton,
                yesButton,
                noButton,
                priceFilterLabel,
                priceInputField,
                priceComparisonDropdown,
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

    public String getSelectedGarageFilter() {
        if (yesButton.isSelected()) return "Y";
        if (noButton.isSelected()) return "N";
        return "All";
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

    public String getPriceInput() { return priceInputField.getText().trim(); }

    public TextField getPriceInputField() { return priceInputField; }

    public String getPriceComparison() { return priceComparisonDropdown.getValue(); }

    public RadioButton getAllButton() { return allButton; }

    public ComboBox<String> getComparisonDropdown() { return priceComparisonDropdown; }

}