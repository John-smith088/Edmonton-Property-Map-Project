package com.mycompany.app.controller;

import com.mycompany.app.model.PropertyAssessments;
import com.mycompany.app.view.StatisticsView;

public class StatisticsController {
    private final StatisticsView statisticsView;
    private final PropertyAssessments propertyAssessments;

    public StatisticsController(StatisticsView statisticsView, PropertyAssessments propertyAssessments) {
        this.statisticsView = statisticsView;
        this.propertyAssessments = propertyAssessments;

        updateStatistics(propertyAssessments); // Initialize with all data
    }

    public void updateStatistics(PropertyAssessments filteredAssessments) {
        if (filteredAssessments == null || filteredAssessments.getProperties().isEmpty()) {
            statisticsView.displayNoStatistics();
        } else {
            statisticsView.updateStatistics(
                    filteredAssessments.getNumberOfRecords(),
                    filteredAssessments.getMinValue(),
                    filteredAssessments.getMaxValue(),
                    filteredAssessments.getRange(),
                    filteredAssessments.getMean(),
                    filteredAssessments.getMedian()
            );
        }
    }
}
