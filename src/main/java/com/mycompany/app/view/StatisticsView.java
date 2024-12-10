package com.mycompany.app.view;

import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import java.text.NumberFormat;

public class StatisticsView {
    private final VBox statisticsPanel;
    private final TextArea propertyStatisticsArea;
    private final TextArea propertyInfoArea;
    private final PieChart classesPieChart;
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance();
    private static final NumberFormat INTEGER_FORMAT = NumberFormat.getIntegerInstance();

    public StatisticsView() {
        // Initialize the UI components
        statisticsPanel = new VBox(10);
        propertyStatisticsArea = new TextArea();
        propertyInfoArea = new TextArea();
        classesPieChart = new PieChart();

        initializeStatisticsPanel();
    }

    private void initializeStatisticsPanel() {
        // Create the label
        Label statisticsLabel = new Label("Property Overview");

        // Configure the property info area
        propertyInfoArea.setEditable(false);
        propertyInfoArea.setWrapText(true);
        propertyInfoArea.setPromptText("Property Info");

        // Configure property statistics area
        propertyStatisticsArea.setEditable(false);
        propertyStatisticsArea.setWrapText(true);
        propertyStatisticsArea.setPromptText("Property Group Statistics");

        // Configure the pie chart
        classesPieChart.setVisible(false);
        classesPieChart.setManaged(false);
        classesPieChart.setLegendVisible(false);
        classesPieChart.setMinHeight(0);
        classesPieChart.setMinWidth(0);

        // Add components to the panel
        statisticsPanel.getChildren().addAll(statisticsLabel, propertyInfoArea, classesPieChart, propertyStatisticsArea);
        statisticsPanel.setMaxHeight(Region.USE_PREF_SIZE);
        statisticsPanel.setPrefHeight(Region.USE_COMPUTED_SIZE);
        statisticsPanel.setMaxWidth(Region.USE_PREF_SIZE);
        statisticsPanel.setPrefWidth(Region.USE_COMPUTED_SIZE);
        statisticsPanel.setPrefWidth(300);
        statisticsPanel.setMaxWidth(300);
        statisticsLabel.getStyleClass().add("statistics-label");
        statisticsPanel.getStyleClass().add("statistics-panel");
        classesPieChart.getStyleClass().add("pie-chart");
    }

    public void updateStatistics(int numberOfRecords, long minValue, long maxValue, long range, double mean, long median) {
        propertyStatisticsArea.setText(String.format(
                "Number of Properties: %s%n" +
                        "Minimum Property Value: %s%n" +
                        "Maximum Property Value: %s%n" +
                        "Property Value Range: %s%n" +
                        "Property Value Mean: %s%n" +
                        "Property Value Median: %s",
                INTEGER_FORMAT.format(numberOfRecords),
                CURRENCY_FORMAT.format(minValue),
                CURRENCY_FORMAT.format(maxValue),
                CURRENCY_FORMAT.format(range),
                CURRENCY_FORMAT.format(mean),
                CURRENCY_FORMAT.format(median)
        ));
    }

    public void displayNoStatistics() {
        propertyStatisticsArea.setText("No statistics available for the selected filters.");
    }

    // Getter for the statistics panel
    public VBox getStatisticsPanel() {
        return statisticsPanel;
    }

    // Getter for the property info area
    public TextArea getPropertyInfoArea() {return propertyInfoArea;}

    public void updatePieChart(PieChart.Data... data) {
        classesPieChart.getData().clear();
        if (data.length > 0) {
            classesPieChart.getData().addAll(data);
            classesPieChart.setVisible(true);
            classesPieChart.setManaged(true);
            classesPieChart.setMinHeight(200);
        } else {
            classesPieChart.setVisible(false);
            classesPieChart.setManaged(false);
        }
    }
}
