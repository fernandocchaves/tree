package com.github.fernandocchaves.tree.application.impl;

import com.github.fernandocchaves.tree.domain.command.CreateNode;
import com.github.fernandocchaves.tree.domain.command.UpdateNode;
import com.github.fernandocchaves.tree.infrasctructure.persistence.mapping.NodeTable;

public class NodeProcessManagerImplMock {

    public static NodeTable dataTable(Long id, NodeTable parent) {
        NodeTable node = NodeTable.builder()
                .id(id)
                .code("123")
                .description("Node Test")
                .detail("")
                .parentId(parent)
                .build();

        return node;
    }

    public static CreateNode createDataCommand(Long parent) {
        CreateNode command = CreateNode.commandOf(
                "123",
                "Node Test",
                parent,
                ""
        );

        return command;
    }

    public static UpdateNode updateDataCommand(Long id, Long parent) {
        UpdateNode command = UpdateNode.commandOf(
                id,
                "123",
                "Node Test",
                parent,
                ""
        );

        return command;
    }
}
