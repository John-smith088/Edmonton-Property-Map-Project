package com.mycompany.app.controller;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.mycompany.app.util.LoadingUtil;
import com.mycompany.app.view.MapViewManager;
import com.mycompany.app.model.PropertyAssessment;
import com.mycompany.app.model.PropertyAssessments;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class MapController {
    private final MapViewManager mapViewManager;
    private long assessedValueCenter;

    public MapController(MapViewManager mapViewManager) {
        this.mapViewManager = mapViewManager;
    }

    /**
     * Sets the assessed value center for color calculations.
     *
     * @param assessedValueCenter The center value to use for color determination.
     */
    public void setAssessedValueCenter(long assessedValueCenter) {
        this.assessedValueCenter = assessedValueCenter;
    }

    /**
     * Displays property markers on the map based on the provided data.
     *
     * @param assessments The collection of property assessments to display.
     */
    public void displayProperties(PropertyAssessments assessments) {
        Task<List<Graphic>> task = new Task<>() {
            @Override
            protected List<Graphic> call() throws Exception {
                List<PropertyAssessment> properties = assessments.getProperties();
                int totalProperties = properties.size();
                final int[] processedCount = {0};

                // Use a dedicated thread pool for parallel processing
                ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                try {
                    return properties.stream()
                            .map(property -> executorService.submit(() -> {
                                if (isCancelled()) {
                                    throw new InterruptedException("Task was cancelled");
                                }
                                // Generate color and symbol
                                Color color = determineColor(property.getAssessedValue());
                                SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, color, 15);

                                // Create the graphic
                                Point point = new Point(property.getLocation().getLng(), property.getLocation().getLat(), SpatialReferences.getWgs84());
                                Graphic graphic = new Graphic(point, symbol);

                                // Add attributes to the graphic
                                graphic.getAttributes().put("accountID", property.getAccountID());

                                // Update progress safely on the UI thread
                                Platform.runLater(() -> updateProgress(++processedCount[0], totalProperties));

                                return graphic;
                            }))
                            .map(future -> {
                                try {
                                    return future.get();
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            })
                            .collect(Collectors.toCollection(ArrayList::new));
                } finally {
                    executorService.shutdown();
                }
            }
        };

        // Loading UI
        VBox loadingContainer = LoadingUtil.createLoadingContainer("Loading Properties...", task);
        Platform.runLater(() -> ((StackPane) mapViewManager.getMapView().getScene().getRoot()).getChildren().add(loadingContainer));

        // Task handlers
        task.setOnSucceeded(e -> {
            List<Graphic> graphics = task.getValue();
            mapViewManager.getGraphicsOverlay().getGraphics().clear();
            mapViewManager.getGraphicsOverlay().getGraphics().addAll(graphics);
            Platform.runLater(() -> ((StackPane) mapViewManager.getMapView().getScene().getRoot()).getChildren().remove(loadingContainer));
        });

        task.setOnFailed(e -> {
            Platform.runLater(() -> ((StackPane) mapViewManager.getMapView().getScene().getRoot()).getChildren().remove(loadingContainer));
            e.getSource().getException().printStackTrace();
        });

        // Start the task
        new Thread(task).start();
    }


    /**
     * Clears all graphics from the map.
     */
    public void clearGraphics() {
        mapViewManager.getGraphicsOverlay().getGraphics().clear();
    }

    /**
     * Determines the color for a marker based on the assessed value.
     *
     * @param assessedValue The assessed value of the property.
     * @return The color representing the assessed value.
     */
    private Color determineColor(long assessedValue) {
        if (assessedValue == 0) {
            return Color.BLACK;
        } else if (assessedValue <= assessedValueCenter * 0.5) {
            return Color.web("#4b2ca3"); // Royal Blue
        } else if (assessedValue <= assessedValueCenter * 0.70) {
            return Color.web("#0077bb"); // Bright Azure
        } else if (assessedValue <= assessedValueCenter * 0.85) {
            return Color.web("#00b891"); // Vivid Turquoise
        } else if (assessedValue <= assessedValueCenter * 0.95) {
            return Color.web("#6ccc63"); // Spring Green
        } else if (assessedValue <= assessedValueCenter * 0.98) {
            return Color.web("#d9ed4c"); // Bright Lime
        } else if (assessedValue == assessedValueCenter) {
            return Color.web("#ffff66"); // Pure Yellow
        } else if (assessedValue <= assessedValueCenter * 1.02) {
            return Color.web("#ffcc33"); // Bright Amber
        } else if (assessedValue <= assessedValueCenter * 1.05) {
            return Color.web("#ff8c00"); // Vivid Orange
        } else if (assessedValue <= assessedValueCenter * 1.15) {
            return Color.web("#e64a19"); // Deep Coral
        } else if (assessedValue <= assessedValueCenter * 1.30) {
            return Color.web("#c70039"); // Crimson
        } else {
            return Color.web("#800026"); // Dark Burgundy
        }
    }

    public void highlightProperty(PropertyAssessment property) {
        mapViewManager.getGraphicsOverlay().getGraphics().clear(); // Clear existing graphics

        // Highlight the selected property
        Point propertyPoint = new Point(property.getLocation().getLng(), property.getLocation().getLat(), SpatialReferences.getWgs84());
        SimpleMarkerSymbol highlightSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.MAGENTA, 20);
        Graphic highlightGraphic = new Graphic(propertyPoint, highlightSymbol);

        mapViewManager.getGraphicsOverlay().getGraphics().add(highlightGraphic);
        mapViewManager.getMapView().setViewpointCenterAsync(propertyPoint, 3000); // Center map on the property
    }
}
