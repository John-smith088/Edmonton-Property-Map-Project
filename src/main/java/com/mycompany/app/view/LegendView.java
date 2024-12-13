package com.mycompany.app.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.text.NumberFormat;
import java.util.List;

public class LegendView {
    private final VBox legendPanel;
    private final VBox legendContent;
    private final TextField legendRecenterInputField;

    public LegendView() {
        legendPanel = new VBox(10);
        legendContent = new VBox(5);
        legendRecenterInputField = new TextField();

        initializeLegendPanel();
    }

    private void initializeLegendPanel() {
        // Configure the legend label
        Label legendLabel = new Label("Legend");

        // Configure the center input field
        legendRecenterInputField.setPromptText("Enter new map center value");

        // Set panel layout and style
        legendPanel.getChildren().addAll(legendLabel, legendContent, legendRecenterInputField);
        legendPanel.setPadding(new Insets(10));
        legendPanel.setAlignment(Pos.TOP_LEFT);
        legendPanel.setPrefWidth(300);
        legendPanel.setMaxWidth(300);
        legendPanel.setMaxHeight(Region.USE_PREF_SIZE);
        legendPanel.setPrefHeight(Region.USE_COMPUTED_SIZE);
        legendLabel.getStyleClass().add("legend-label");
        legendPanel.getStyleClass().add("legend-panel");
    }

    /**
     * Refreshes the legend with dynamic content based on the provided center value and number formatter.
     *
     * @param assessedValueCenter The central assessed value for calculations.
     * @param numberFormat        Formatter for numeric values.
     */
    public void refreshLegend(long assessedValueCenter, NumberFormat numberFormat) {
        legendContent.getChildren().clear();
        legendContent.getChildren().addAll(
                createLegendItem("Zero Value: $0", Color.BLACK),
                createLegendItem(String.format("50%% Below Center: $%s", numberFormat.format(assessedValueCenter * 0.5)), Color.web("#4b2ca3")),
                createLegendItem(String.format("30%% Below Center: $%s", numberFormat.format(assessedValueCenter * 0.7)), Color.web("#0077bb")),
                createLegendItem(String.format("15%% Below Center: $%s", numberFormat.format(assessedValueCenter * 0.85)), Color.web("#00b891")),
                createLegendItem(String.format("5%% Below Center: $%s", numberFormat.format(assessedValueCenter * 0.95)), Color.web("#6ccc63")),
                createLegendItem(String.format("2%% Below Center: $%s", numberFormat.format(assessedValueCenter * 0.98)), Color.web("#d9ed4c")),
                createLegendItem(String.format("Center: $%s", numberFormat.format(assessedValueCenter)), Color.web("#ffff66")),
                createLegendItem(String.format("2%% Above Center: $%s", numberFormat.format(assessedValueCenter * 1.02)), Color.web("#ffcc33")),
                createLegendItem(String.format("5%% Above Center: $%s", numberFormat.format(assessedValueCenter * 1.05)), Color.web("#ff8c00")),
                createLegendItem(String.format("15%% Above Center: $%s", numberFormat.format(assessedValueCenter * 1.15)), Color.web("#e64a19")),
                createLegendItem(String.format("30%% Above Center: $%s", numberFormat.format(assessedValueCenter * 1.3)), Color.web("#c70039")),
                createLegendItem(String.format("50%% Above Center: $%s", numberFormat.format(assessedValueCenter * 1.5)), Color.web("#800026")),
                createLegendItem("Selected", Color.MAGENTA)
        );
    }

    /**
     * Updates the legend with a list of custom items.
     *
     * @param legendItems A list of custom legend items to display.
     */
    public void updateLegend(List<LegendItem> legendItems) {
        legendContent.getChildren().clear();
        if (legendItems != null) {
            for (LegendItem item : legendItems) {
                legendContent.getChildren().add(createLegendItem(item));
            }
        }
    }

    /**
     * Displays a message when no legend data is available.
     */
    public void displayNoLegend() {
        legendContent.getChildren().clear();
        legendContent.getChildren().add(new Label("No legend data available."));
    }

    /**
     * Creates a legend item using the provided text and color.
     *
     * @param text  The label for the legend item.
     * @param color The color associated with the legend item.
     * @return A horizontal box containing the legend item.
     */
    private HBox createLegendItem(String text, Color color) {
        HBox legendItemBox = new HBox(10);
        legendItemBox.setAlignment(Pos.CENTER_LEFT);

        Label colorBox = new Label();
        colorBox.setPrefSize(15, 15);
        colorBox.setStyle("-fx-background-color: " + toHexString(color) + "; -fx-border-color: black;");

        Label textLabel = new Label(text);

        legendItemBox.getChildren().addAll(colorBox, textLabel);
        return legendItemBox;
    }

    /**
     * Creates a legend item using a `LegendItem` record.
     *
     * @param item The `LegendItem` containing the text and color.
     * @return A horizontal box containing the legend item.
     */
    private HBox createLegendItem(LegendItem item) {
        return createLegendItem(item.text(), item.color());
    }

    /**
     * Converts a `Color` object to its hexadecimal string representation.
     *
     * @param color The `Color` object to convert.
     * @return The hexadecimal string representation of the color.
     */
    private String toHexString(Color color) {
        return String.format("#%02x%02x%02x",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255)
        );
    }

    // Getter for the legend panel
    public VBox getLegendPanel() {
        return legendPanel;
    }

    // Getter for the center input field
    public TextField getLegendRecenterInputField() {
        return legendRecenterInputField;
    }

    // Inner record class for legend items
    public record LegendItem(String text, Color color) {
    }
}
