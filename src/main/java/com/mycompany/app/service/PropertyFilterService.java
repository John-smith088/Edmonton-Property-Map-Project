package com.mycompany.app.service;

import com.mycompany.app.model.PropertyAssessment;
import com.mycompany.app.model.PropertyAssessments;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PropertyFilterService {

    /**
     * Filters properties based on the given criteria.
     *
     * @param assessments   The full list of property assessments.
     * @param filterType    The type of filter (e.g., "Neighborhood", "Ward").
     * @param filterValue   The value to filter by (e.g., "Downtown", "Ward 1").
     * @return A list of filtered properties.
     */
    public PropertyAssessments filterByCriteria(PropertyAssessments assessments, String filterType, String filterValue) {
        List<PropertyAssessment> filteredProperties = assessments.getProperties().stream()
                .filter(property -> matchesFilter(property, filterType, filterValue))
                .collect(Collectors.toList());

        return new PropertyAssessments(filteredProperties);
    }

    public PropertyAssessments filterWithAllCriteria(PropertyAssessments assessments, String filterType, String filterValue, String garageFilter) {
        PropertyAssessments filteredByCriteria = filterByCriteria(assessments, filterType, filterValue);
        return filterByGarage(filteredByCriteria, garageFilter);
    }

    private boolean matchesFilter(PropertyAssessment property, String filterType, String filterValue) {
        return switch (filterType) {
            case "Neighborhood" -> property.getNeighborhood().getNeighborhoodName().equals(filterValue);
            case "Ward" -> property.getNeighborhood().getWard().equals(filterValue);
            case "Assessment Class" -> property.getAssessmentClass().toString().contains(filterValue);
            default -> false;
        };
    }

    /**
     * Retrieves distinct values for a given filter type.
     *
     * @param assessments   The full list of property assessments.
     * @param filterType    The type of filter (e.g., "Neighborhood", "Ward").
     * @return A sorted list of distinct values for the specified filter type.
     */
    public List<String> getDistinctValues(PropertyAssessments assessments, String filterType) {
        return switch (filterType) {
            case "Neighborhood" -> assessments.getProperties().stream()
                    .map(p -> p.getNeighborhood().getNeighborhoodName())
                    .distinct()
                    .sorted()
                    .toList();
            case "Ward" -> assessments.getProperties().stream()
                    .map(p -> p.getNeighborhood().getWard())
                    .distinct()
                    .sorted()
                    .toList();
            case "Assessment Class" -> assessments.getProperties().stream()
                    .flatMap(p -> Arrays.stream(new String[]{
                            p.getAssessmentClass().getAssessmentClass1(),
                            p.getAssessmentClass().getAssessmentClass2(),
                            p.getAssessmentClass().getAssessmentClass3()
                    }))
                    .filter(Objects::nonNull)
                    .distinct()
                    .sorted()
                    .toList();
            default -> List.of();
        };
    }

    public PropertyAssessments filterByGarage(PropertyAssessments assessments, String garageFilter) {
        if (garageFilter.equals("All")) {
            return assessments; // No filtering applied
        }

        List<PropertyAssessment> filteredProperties = assessments.getProperties().stream()
                .filter(property -> garageFilter.equals(property.getGarage()))
                .collect(Collectors.toList());

        return new PropertyAssessments(filteredProperties);
    }

    public PropertyAssessments filterByPrice(PropertyAssessments assessments, String comparison, Long price) {
        if (price == null || comparison == null || comparison.isEmpty()) {
            return assessments; // No filtering applied if inputs are invalid
        }

        List<PropertyAssessment> filteredProperties = assessments.getProperties().stream()
                .filter(property -> {
                    return switch (comparison) {
                        case "Under" -> property.getAssessedValue() < price;
                        case "Equal" -> property.getAssessedValue() == price;
                        case "Above" -> property.getAssessedValue() > price;
                        default -> true;
                    };
                })
                .collect(Collectors.toList());

        return new PropertyAssessments(filteredProperties);
    }
}