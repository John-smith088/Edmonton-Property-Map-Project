package com.mycompany.app.controller;

import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import com.mycompany.app.view.MapViewManager;
import com.mycompany.app.model.PropertyAssessment;
import com.mycompany.app.model.PropertyAssessments;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

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
        clearGraphics(); // Clear existing markers
        List<Graphic> graphics = new ArrayList<>();
        for (PropertyAssessment property : assessments.getProperties()) {
            // Generate color and symbol
            Color color = determineColor(property.getAssessedValue());
            SimpleMarkerSymbol symbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, color, 15);

            // Create the graphic
            Point point = new Point(property.getLocation().getLng(), property.getLocation().getLat(), SpatialReferences.getWgs84());
            Graphic graphic = new Graphic(point, symbol);

            graphic.getAttributes().put("accountID", property.getAccountID());

            graphics.add(graphic);
        }
        mapViewManager.getGraphicsOverlay().getGraphics().addAll(graphics);
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
}
