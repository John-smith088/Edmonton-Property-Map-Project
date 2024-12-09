package com.mycompany.app.view;

import com.esri.arcgisruntime.mapping.ArcGISMap;
import com.esri.arcgisruntime.mapping.BasemapStyle;
import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.mapping.view.MapView;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;

public class MapViewManager {
    private final MapView mapView;
    private final GraphicsOverlay graphicsOverlay;

    public MapViewManager() {
        mapView = new MapView();
        graphicsOverlay = new GraphicsOverlay();
        initializeMap();
    }

    private void initializeMap() {
        ArcGISMap map = new ArcGISMap(BasemapStyle.ARCGIS_DARK_GRAY);
        map.setReferenceScale(10000);
        mapView.setMap(map);

        // Center the map on Edmonton
        Point edmontonViewPoint = new Point(-113.4938, 53.5461, SpatialReferences.getWgs84());
        mapView.setViewpointCenterAsync(edmontonViewPoint, 15000);

        graphicsOverlay.setScaleSymbols(true);
        mapView.getGraphicsOverlays().add(graphicsOverlay);
    }

    public MapView getMapView() {
        return mapView;
    }

    public GraphicsOverlay getGraphicsOverlay() {
        return graphicsOverlay;
    }
}
