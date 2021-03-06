package com.stackleader.kubefx.kubernetes.api.model;

import com.google.common.base.Joiner;
import io.fabric8.kubernetes.api.model.NodeCondition;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import static java.util.stream.Collectors.toList;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author dcnorris
 */
public class Node {

    private StringProperty name;
    private StringProperty startTime;
    private StringProperty nodeIp;
    io.fabric8.kubernetes.api.model.Node node;

    public Node(io.fabric8.kubernetes.api.model.Node node) {
        this.node = node;
        name = new SimpleStringProperty(node.getMetadata().getName());
        startTime = new SimpleStringProperty(node.getMetadata().getCreationTimestamp());
    }

    public String getName() {
        return name.get();
    }

    public ReadOnlyStringProperty nameProperty() {
        return name;
    }

    public String getStartTime() {
        return startTime.get();
    }

    public String getLabelString() {
        return node.getMetadata().getLabels().toString();
    }

    public String getAge() {
        LocalDate dt = LocalDate.parse(startTime.get(), DateTimeFormatter.ISO_ZONED_DATE_TIME);
        LocalDate now = LocalDate.now();
        int ageInDays = Period.between(dt, now).getDays();
        return ageInDays + "d";
    }

    public String getStatus() {
        final List<NodeCondition> conditions = node.getStatus().getConditions();
        if (!conditions.isEmpty()) {
            final List<String> conditionTypes = conditions.stream().map(condition -> condition.getType()).collect(toList());
            if (conditionTypes.contains("Ready")) {
                return "Ready";
            } else {
                return Joiner.on(",").join(conditionTypes);
            }
        }
        return "?";
    }

    public StringProperty getNodeIpProperty() {
        return nodeIp;
    }

    public ReadOnlyStringProperty startTimeProperty() {
        return startTime;
    }

    @Override
    public String toString() {
        return name.get();
    }

}
