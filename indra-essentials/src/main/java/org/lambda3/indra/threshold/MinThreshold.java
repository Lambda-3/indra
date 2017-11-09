package org.lambda3.indra.threshold;

import org.lambda3.indra.threshold.Threshold;

import java.util.LinkedHashMap;

public class MinThreshold implements Threshold {
    private float min;

    public MinThreshold(float min) {
        this.min = min;
    }

    @Override
    public LinkedHashMap<String, Double> apply(LinkedHashMap<String, Double> relatedness) {
        LinkedHashMap<String, Double> results = new LinkedHashMap<>();
        relatedness.entrySet().stream().filter(entry -> (entry.getValue() >= min)).
                forEach(e -> results.put(e.getKey(), e.getValue()));
        return results;
    }

    @Override
    public String getName() {
        return "min";
    }
}