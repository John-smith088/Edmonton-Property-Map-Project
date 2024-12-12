/**
 * Copyright 2019 Esri
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.mycompany.app;

import com.esri.arcgisruntime.ArcGISRuntimeEnvironment;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.mycompany.app.controller.FilterController;
import com.mycompany.app.controller.LegendController;
import com.mycompany.app.controller.MapController;
import com.mycompany.app.controller.StatisticsController;
import com.mycompany.app.model.PropertyAssessments;
import com.mycompany.app.service.PropertyFilterService;
import com.mycompany.app.view.FilterPanelView;
import com.mycompany.app.view.LegendView;
import com.mycompany.app.view.MapViewManager;
import com.mycompany.app.view.StatisticsView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class App extends Application {
    // Global variables for window dimensions that might need to change
    private final Integer initialScreenWidth = 1000;
    private final Integer initialScreenHeight = 800;
    private final Integer minScreenWidth = 800;
    private final Integer minScreenHeight = 600;

    // Instance Variables
    private MapViewManager mapViewManager;
    private MapView mapView;
    private FilterPanelView filterPanelView;
    private StatisticsView statisticsView;
    private LegendView legendView;
    private PropertyAssessments propertyAssessments;

    // Controllers
    private FilterController filterController;
    private StatisticsController statisticsController;
    private LegendController legendController;
    private MapController mapController;

    // Components
    private Button toggleStatsButton;
    private Button toggleLegendButton;
    private StackPane rootStackPane;


    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
        initializeArcGISRuntime();

        initializeStage(stage);

        // Create a StackPane as the root layout
        rootStackPane = new StackPane();

        // Initialize views
        mapViewManager = new MapViewManager();
        mapView = mapViewManager.getMapView();
        filterPanelView = new FilterPanelView();
        statisticsView = new StatisticsView();
        legendView = new LegendView();

        // Load Property data
        loadPropertyData();

        // Initialize services
        PropertyFilterService propertyFilterService = new PropertyFilterService();

        // Initialize controllers
        mapController = new MapController(mapViewManager);
        statisticsController = new StatisticsController(statisticsView, propertyAssessments);
        legendController = new LegendController(legendView, propertyAssessments);
        legendController.legendRecenterInputFieldFunctionality(mapController);
        filterController = new FilterController(filterPanelView, propertyAssessments, mapController, statisticsController, legendController, propertyFilterService);

        // Use MapController to display all properties initially
        mapController.setAssessedValueCenter(propertyAssessments.getMedian());
        mapController.displayProperties(propertyAssessments);

        // Create the toggle buttons
        toggleStatsButton = new Button("Hide Statistics");
        toggleLegendButton = new Button("Hide Legend");

        // Initialize the toggle buttons
        statisticsController.initializeToggleButton(toggleStatsButton, rootStackPane);
        legendController.initializeToggleButton(toggleLegendButton, rootStackPane);


        // Add all components to the StackPane in the correct order
        setupStackPane();

        // Create the scene and show it on the screen
        Scene scene = new Scene(rootStackPane);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/styles.css")).toExternalForm());
        stage.setScene(scene);
        stage.show();

    }

    public static String readApiKey(String fileName) {
        try {
            Path filePath = Path.of(fileName);
            System.out.println("Looking for API key file at: " + filePath.toAbsolutePath());
            return Files.readString(filePath).trim();
        } catch (IOException e) {
            System.err.println("Error reading the API key: " + e.getMessage());
            return null;
        }
    }

    private void initializeArcGISRuntime() {
        // Set API key for ArcGIS
        // Please paste your key into a file named secret.txt
        String yourApiKey = readApiKey("secret.txt");
        System.out.println(yourApiKey);
        ArcGISRuntimeEnvironment.setApiKey(yourApiKey);
    }

    private void initializeStage(Stage stage) {
        // Set the title and size of the stage and show it
        stage.setTitle("Edmonton Properties Map");
        stage.setWidth(initialScreenWidth);
        stage.setHeight(initialScreenHeight);
        stage.setMinWidth(minScreenWidth);
        stage.setMinHeight(minScreenHeight);
    }

    private void loadPropertyData() {
        // Load property data
        try {
            propertyAssessments = new PropertyAssessments("Property_Assessment_Data_2024.csv");
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
            // Exit if data loading fails
        }
    }

    private void setupStackPane() {
        // Add the map layout (background layer)
        rootStackPane.getChildren().add(mapView);
        StackPane.setAlignment(mapView, Pos.CENTER);

        // Add the filter panel (top-left corner)
        rootStackPane.getChildren().add(filterPanelView.getFilterPanel());
        StackPane.setAlignment(filterPanelView.getFilterPanel(), Pos.TOP_LEFT);
        StackPane.setMargin(filterPanelView.getFilterPanel(), new Insets(10));

        // Add the statistics panel (top-right corner)
        rootStackPane.getChildren().add(statisticsView.getStatisticsPanel());
        StackPane.setAlignment(statisticsView.getStatisticsPanel(), Pos.TOP_RIGHT);
        StackPane.setMargin(statisticsView.getStatisticsPanel(), new Insets(10));

        // Add the legend panel (bottom-left corner)
        rootStackPane.getChildren().add(legendView.getLegendPanel());
        StackPane.setAlignment(legendView.getLegendPanel(), Pos.BOTTOM_LEFT);
        StackPane.setMargin(legendView.getLegendPanel(), new Insets(10));

        rootStackPane.getChildren().add(toggleStatsButton);
        StackPane.setAlignment(toggleStatsButton, Pos.TOP_RIGHT);
        StackPane.setMargin(toggleStatsButton, new Insets(10, 320, 0, 320));

        rootStackPane.getChildren().add(toggleLegendButton);
        StackPane.setAlignment(toggleLegendButton, Pos.BOTTOM_LEFT);
        StackPane.setMargin(toggleLegendButton, new Insets(10, 320, 20, 320));
    }

    @Override
    public void stop() {
        if (mapView != null) {
            mapView.dispose();
        }
    }
}