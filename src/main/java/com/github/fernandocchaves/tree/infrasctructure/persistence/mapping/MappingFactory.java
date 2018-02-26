package com.github.fernandocchaves.tree.infrasctructure.persistence.mapping;

import com.github.fernandocchaves.tree.interfaces.rest.model.result.NodeParentResult;
import com.github.fernandocchaves.tree.interfaces.rest.model.result.NodeResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public final class MappingFactory {

    public static List<NodeResult> map(List<NodeTable> records) {
        List<NodeResult> nodes = records.stream()
                .map(singleTable -> mapSingle(singleTable))
                .collect(Collectors.toList());

        return nodes;
    }

    public static List<NodeParentResult> mapParent(List<NodeTable> records) {
        List<NodeParentResult> nodes = records.stream()
                .map(singleTable -> mapSingleParent(singleTable))
                .collect(Collectors.toList());

        return nodes;
    }

    private static NodeResult mapSingle(NodeTable singleTable) {

        Long parentId = null;
        if(Objects.nonNull(singleTable.getParentId())) {
            parentId = singleTable.getParentId().getId();
        }

        NodeResult nodeResult = NodeResult.builder()
                .id(singleTable.getId())
                .code(singleTable.getCode())
                .description(singleTable.getDescription())
                .parentId(parentId)
                .detail(singleTable.getDetail())
                .children(map(singleTable.getChildren()))
                .build();

        return nodeResult;
    }

    private static NodeParentResult mapSingleParent(NodeTable singleTable) {

        Long parentId = null;
        Boolean hasChildren = false;

        if(Objects.nonNull(singleTable.getParentId())) {
            parentId = singleTable.getParentId().getId();
        }

        if(singleTable.getChildren().size() > 0) {
            hasChildren = true;
        }

        NodeParentResult nodeResult = NodeParentResult.builder()
                .id(singleTable.getId())
                .code(singleTable.getCode())
                .description(singleTable.getDescription())
                .parentId(parentId)
                .detail(singleTable.getDetail())
                .hasChildren(hasChildren)
                .build();

        return nodeResult;
    }

}
