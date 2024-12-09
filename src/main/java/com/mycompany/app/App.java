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
import com.mycompany.app.view.FilterPanelView;
import com.mycompany.app.view.LegendView;
import com.mycompany.app.view.MapViewManager;
import com.mycompany.app.view.StatisticsView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;

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

        // Initialize controllers
        mapController = new MapController(mapViewManager);
        statisticsController = new StatisticsController(statisticsView, propertyAssessments);
        legendController = new LegendController(legendView, propertyAssessments);
        filterController = new FilterController(filterPanelView, propertyAssessments, mapController, statisticsController, legendController);

        // Use MapController to display all properties initially
        mapController.setAssessedValueCenter(propertyAssessments.getMedian());
        mapController.displayProperties(propertyAssessments);

        // Add all components to the StackPane in the correct order
        setupStackPane();

        // Create the scene, apply the styling and show it on the screen
        Scene scene = new Scene(rootStackPane);
        stage.setScene(scene);
        stage.show();

    }

    private void initializeArcGISRuntime() {
        // Set API key for ArcGIS
        String yourApiKey = "AAPTxy8BH1VEsoebNVZXo8HurFJ2xCDXvFC-uJSDrIEtVkCMkq-W26QCtQCgZQipt1lUwR3Pm3yRRKbeYBv6kCefEqVXMAsnQ4rVMkNdiFC5DLtXmhx_Daydix9ND6gKSfXvNdbEQeQwWhhHluGF5DXHa496Q77CndgVM7EY_nadJd-0J9bw5HiOrqsb4as3xU5lBVtBAp2G1FB5WYInTpK_0C6_6_reJIkqqnHUoC6Ez_o.AT1_V1QXZfZZ";
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
    }

//    private void centerInputFieldFunctionality(){
//        centerInputField.setOnKeyPressed(new EventHandler<KeyEvent>() {
//            @Override
//            public void handle(KeyEvent keyEvent) {
//                if(keyEvent.getCode().equals(KeyCode.ENTER)){
//
//                    //Get value from input field -> need to alert upon invalid entry
//
//                    String centerString = centerInputField.getText().trim();
//
//
//                    final Long newCenter; // Declare priceValue as final
//                    if (centerString.isEmpty()) {
//                        try {
//                            newCenter = Long.parseLong(centerString);
//                        } catch (NumberFormatException e) {
//                            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid price value. Please enter a valid number.", ButtonType.OK);
//                            alert.showAndWait();
//                            return;
//                        }
//                    }else {
//                        newCenter = null;
//                    }
//
//                    assessedValueCenter = Long.parseLong(centerString);
//
//                    // Create a background task to simulate progress
//                    Task<Void> task = new Task<>() {
//                        @Override
//                        protected Void call() throws Exception {
//                            // Simulate progress
//                            for (int i = 0; i <= 10; i++) {
//                                updateProgress(i, 10);
//                                Thread.sleep(50); // Simulated delay
//                            }
//                            return null;
//                        }
//                    };
//
//                    // Create a loading container with a progress bar and label
//                    VBox loadingContainer = createLoadingContainer("recentering", task);
//
//                    // Add the loading container to the root stack pane
//                    Platform.runLater(() -> rootStackPane.getChildren().add(loadingContainer));
//
//                    // When the task succeeds, clear the filters and reset the map
//                    task.setOnSucceeded(e -> {
//                        Platform.runLater(() -> rootStackPane.getChildren().remove(loadingContainer));
//                        graphicsOverlay.getGraphics().clear(); // Clear all graphics
//                        addPropertiesToMap(propertiesClass.getProperties()); // Re-add all properties
//                        //Redraw legend
//                        refreshLegend();
//
//                    });
//
//                    // Handle task failure
//                    task.setOnFailed(e -> {
//                        Platform.runLater(() -> rootStackPane.getChildren().remove(loadingContainer));
//                        e.getSource().getException().printStackTrace();
//                    });
//
//                    // Run the task in a background thread
//                    new Thread(task).start();
//
//
//                }
//
//            }
//        });
//    }

//    private void accountSearchButtonFunctionality() {
//        // Add functionality to Account Search button
//        accountSearchButton.setOnAction(event -> {
//            String accountNumberStr = accountSearchInput.getText().trim();
//            if (accountNumberStr.isEmpty()) {
//                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter an account number.", ButtonType.OK);
//                alert.showAndWait();
//                return;
//            }
//
//            try {
//                int accountNumber = Integer.parseInt(accountNumberStr);
//                PropertyAssessment property = propertiesClass.getPropertyByAccountID(accountNumber);
//
//                if (property == null) {
//                    Alert alert = new Alert(Alert.AlertType.WARNING, "No property found with the given account number.", ButtonType.OK);
//                    alert.showAndWait();
//                } else {
//                    displayPropertyInfo(property);
//                    displayPieChart(property);
//                    highlightSelectedProperty(property);
//                }
//            } catch (NumberFormatException e) {
//                Alert alert = new Alert(Alert.AlertType.ERROR, "Account number must be a valid number", ButtonType.OK);
//            }
//        });
//    }

//
//    // Display property information
//    private void displayPropertyInfo(PropertyAssessment property) {
//        if (property == null) {
//            propertyInfoArea.setText("No property information available.");
//        } else {
//
//            propertyInfoArea.setText(String.format(
//                            "Account Number: %s%n" +
//                            "Address: %s%n%n" +
//                            "Garage: %s%n%n"  +
//                            "Assessment Value: $%s %n%n" +
//                            "Neighborhood: %s%n%n" +
//                            "Assessment Class: %s%n%n" +
//                            "Latitude: %f%n" +
//                            "Longitude %f%n",
//                    property.getAccountID(),
//                    property.getAddress(),
//                    property.getGarage(),
//                    numberFormat.format(property.getAssessedValue()),
//                    property.getNeighborhood().getNeighborhoodName(),
//                    property.getAssessmentClass(),
//                    property.getLocation().getLat(),
//                    property.getLocation().getLng()
//            ));
//        }
//    }

//
//    // Display pie chart of property assessment classes
//    private void displayPieChart(PropertyAssessment property) {
//        classesPieChart.getData().clear();
//
//        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
//                new PieChart.Data(property.getAssessmentClass().getAssessmentClass1(), property.getAssessmentClass().getAssessmentPercentage1()),
//                new PieChart.Data(property.getAssessmentClass().getAssessmentClass2(), property.getAssessmentClass().getAssessmentPercentage2()),
//                new PieChart.Data(property.getAssessmentClass().getAssessmentClass3(), property.getAssessmentClass().getAssessmentPercentage3())
//        );
//        // Only add valid entries
//        pieChartData.removeIf(data -> data.getPieValue() == 0 || data.getName().isEmpty() || data.getName() == null);
//
//        classesPieChart.setData(pieChartData);
//
//        classesPieChart.setClockwise(true);
//        classesPieChart.setStartAngle(180);
//        classesPieChart.setVisible(true);
//        classesPieChart.setManaged(true);
//        classesPieChart.setTitle("Assessment Classes");
//        classesPieChart.setLabelLineLength(8);
//        classesPieChart.setMaxHeight(200);
//        classesPieChart.setStyle("-fx-padding: 0");
//
//        setupPieChartValues();
//    }

//    // Highlight selected property
//    private void highlightSelectedProperty(PropertyAssessment property) {
//
//        assessedValueCenter = property.getAssessedValue();
//        refreshLegend();
//        // Background task for preparing graphics
//        Task<List<Graphic>> task = new Task<>() {
//            @Override
//            protected List<Graphic> call() {
//                List<Graphic> fadedGraphics = new ArrayList<>();
//                List<PropertyAssessment> properties = propertiesClass.getProperties();
//
//                for (int i = 0; i < properties.size(); i++) {
//                    PropertyAssessment otherProperty = properties.get(i);
//
//                    if (otherProperty != property) { // Exclude the selected property
//                        Color fadedColor = getAssesmentColor(otherProperty.getAssessedValue()).deriveColor(0, 1, 1, 0.3);
//                        SimpleMarkerSymbol fadedSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, fadedColor, 15);
//                        Point fadedPoint = new Point(otherProperty.getLocation().getLng(), otherProperty.getLocation().getLat(), SpatialReferences.getWgs84());
//                        Graphic fadedGraphic = new Graphic(fadedPoint, fadedSymbol);
//                        fadedGraphics.add(fadedGraphic);
//                    }
//
//                    // Update progress
//                    updateProgress(i + 1, properties.size());
//                }
//
//                // Prepare the highlighted graphic
//                Point highlightedPoint = new Point(property.getLocation().getLng(), property.getLocation().getLat(), SpatialReferences.getWgs84());
//                SimpleMarkerSymbol highlightedSymbol = new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.CIRCLE, Color.MAGENTA, 20);
//                fadedGraphics.add(new Graphic(highlightedPoint, highlightedSymbol));
//
//                return fadedGraphics;
//            }
//        };
//
//        VBox loadingContainer = createLoadingContainer("Loading Data", task);
//
//        // Add the loading container to the StackPane
//        Platform.runLater(() -> rootStackPane.getChildren().add(loadingContainer)); // rootStackPane is the root of your Scene
//
//        task.setOnSucceeded(e -> {
//            // Remove the loading container
//            Platform.runLater(() -> rootStackPane.getChildren().remove(loadingContainer));
//
//            // Update graphics overlay and map viewpoint
//            graphicsOverlay.getGraphics().clear();
//            graphicsOverlay.getGraphics().addAll(task.getValue()); // Add all graphics in one batch
//
//            // Center the map on the selected property
//            Point centerPoint = new Point(property.getLocation().getLng(), property.getLocation().getLat(), SpatialReferences.getWgs84());
//            mapView.setViewpointCenterAsync(centerPoint, 3000);
//        });
//
//        task.setOnFailed(e -> {
//            // Remove the progress bar in case of failure
//            Platform.runLater(() -> rootStackPane.getChildren().remove(loadingContainer));
//            e.getSource().getException().printStackTrace();
//        });
//
//        // Start the task in a background thread
//        new Thread(task).start();
//    }
//
//    private VBox createLoadingContainer(String loadingMessage, Task<?> task) {
//        // Create ProgressBar and Loading Label
//        ProgressBar progressBar = new ProgressBar();
//        Label loadingLabel = new Label(loadingMessage);
//
//        // Apply CSS class to the loading label
//        loadingLabel.getStyleClass().add("loading-label");
//
//        // Create a container for the loading UI
//        VBox loadingContainer = new VBox(10, loadingLabel, progressBar);
//
//        // Apply CSS class to the loading container
//        loadingContainer.getStyleClass().add("loading-container");
//        loadingContainer.setAlignment(Pos.CENTER);
//
//        // Bind the task progress to the ProgressBar
//        progressBar.progressProperty().bind(task.progressProperty());
//
//        return loadingContainer;
//    }
//
//    private void setupClickHandler() {
//        mapView.setOnMouseClicked(event -> {
//            if (event.isStillSincePress()) { // Ensure it's not a drag
//                Point2D screenPoint = new Point2D(event.getX(), event.getY()); // Screen coordinates where the user clicked
//
//                // Perform a hit test on the GraphicsOverlay
//                ListenableFuture<IdentifyGraphicsOverlayResult> future = mapView.identifyGraphicsOverlayAsync(graphicsOverlay, screenPoint, 10, false, 1);
//
//                // Add a listener to process the result once it's available
//                future.addDoneListener(() -> {
//                    try {
//                        // Get the result of the identify operation
//                        IdentifyGraphicsOverlayResult result = future.get();
//
//                        // Retrieve the list of identified graphics
//                        List<Graphic> graphics = result.getGraphics();
//
//                        if (!graphics.isEmpty()) {
//                            // Get the first graphic that was clicked
//                            Graphic clickedGraphic = graphics.get(0);
//
//                            // Retrieve the accountID attribute
//                            Integer accountID = (Integer) clickedGraphic.getAttributes().get("accountID");
//
//                            if (accountID != null) {
//                                // Use the accountID to find the PropertyAssessment object
//                                PropertyAssessment property = propertiesClass.getPropertyByAccountID(accountID);
//
//                                // Display the property info in the info area
//                                if (property != null) {
//                                    displayPropertyInfo(property);
//                                    displayPieChart(property);
//                                    highlightSelectedProperty(property);
//                                }
//                            }
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace(); // Handle exceptions such as InterruptedException or ExecutionException
//                    }
//                });
//            }
//        });
//    }

    @Override
    public void stop() {
        if (mapView != null) {
            mapView.dispose();
        }
    }
}