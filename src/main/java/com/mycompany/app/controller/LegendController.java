package com.mycompany.app.controller;

import com.mycompany.app.view.LegendView;
import com.mycompany.app.model.PropertyAssessments;

import java.text.NumberFormat;

public class LegendController {
    private final LegendView legendView;
    private final PropertyAssessments propertyAssessments;

    public LegendController(LegendView legendView, PropertyAssessments propertyAssessments) {
        this.legendView = legendView;
        this.propertyAssessments = propertyAssessments;

        initializeLegend();
    }

    private void initializeLegend() {
        // Populate the initial legend content
        updateLegend(propertyAssessments);
    }

    public void updateLegend(PropertyAssessments assessments) {
        if (assessments == null || assessments.getProperties().isEmpty()) {
            legendView.displayNoLegend();
        } else {
            // Refresh legend dynamically based on assessed value center
            long centerValue = assessments.getMedian();
            NumberFormat numberFormat = NumberFormat.getNumberInstance(); // Formatter for numeric values
            legendView.refreshLegend(centerValue, numberFormat);
        }
    }
}
