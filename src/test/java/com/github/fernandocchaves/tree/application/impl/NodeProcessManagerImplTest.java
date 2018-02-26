package com.github.fernandocchaves.tree.application.impl;

import com.github.fernandocchaves.tree.application.NodeProcessManager;
import com.github.fernandocchaves.tree.domain.command.CreateNode;
import com.github.fernandocchaves.tree.domain.command.UpdateNode;
import com.github.fernandocchaves.tree.domain.shared.CommandFailure;
import com.github.fernandocchaves.tree.domain.shared.NodeErrorCode;
import com.github.fernandocchaves.tree.infrasctructure.persistence.mapping.NodeTable;
import com.github.fernandocchaves.tree.infrasctructure.persistence.repository.NodeRepositoryImpl;
import com.github.fernandocchaves.tree.infrasctructure.persistence.repository.NodeStore;
import com.github.fernandocchaves.tree.interfaces.rest.model.result.NodeParentResult;
import io.vavr.control.Either;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class NodeProcessManagerImplTest {

    NodeProcessManager nodeProcessManager;

    @Mock
    NodeRepositoryImpl nodeRepository;

    @Mock
    NodeStore nodeStore;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        nodeRepository = new NodeRepositoryImpl(nodeStore);
        nodeProcessManager = new NodeProcessManagerImpl(nodeRepository);
    }

    @Test
    public void shouldCreateNodeNoParent() {
        CreateNode command = NodeProcessManagerImplMock.createDataCommand(null);
        NodeTable nodeTable = NodeProcessManagerImplMock.dataTable(new Long(1), null);

        when(nodeStore.save(any(NodeTable.class))).thenReturn(nodeTable);
        Either<CommandFailure, Long> response = nodeProcessManager.create(command);

        assertTrue(response.isRight());
        assertTrue(response.right().get() instanceof Long);
        assertTrue(response.right().get().equals(nodeTable.getId()));
    }

    @Test
    public void shouldCreateNodeWithParent() {
        CreateNode command = NodeProcessManagerImplMock.createDataCommand(new Long(1));
        NodeTable parentTable = NodeProcessManagerImplMock.dataTable(new Long(1), null);
        NodeTable nodeTable = NodeProcessManagerImplMock.dataTable(new Long(2), parentTable);

        when(nodeStore.save(any(NodeTable.class))).thenReturn(nodeTable);
        when(nodeStore.findOne(any(Long.class))).thenReturn(parentTable);

        Either<CommandFailure, Long> response = nodeProcessManager.create(command);

        assertTrue(response.isRight());
        assertTrue(response.right().get() instanceof Long);
        assertTrue(response.right().get().equals(nodeTable.getId()));
    }

    @Test
    public void shouldCreateNodeParentNodeFound() {
        CreateNode command = NodeProcessManagerImplMock.createDataCommand(new Long(4));
        NodeTable nodeTable = NodeProcessManagerImplMock.dataTable(new Long(1), null);

        when(nodeStore.save(any(NodeTable.class))).thenReturn(nodeTable);
        Either<CommandFailure, Long> response = nodeProcessManager.create(command);

        assertTrue(response.isLeft());
        assertTrue(response.left().get() instanceof CommandFailure);
        assertTrue(response.left().get().equals(CommandFailure.of(NodeErrorCode.PARENT_NOT_FOUND)));
    }

    @Test
    public void shouldUpdateNodeNoParent() {
        UpdateNode command = NodeProcessManagerImplMock.updateDataCommand(new Long(1),null);
        NodeTable nodeTable = NodeProcessManagerImplMock.dataTable(new Long(1), null);

        when(nodeStore.save(any(NodeTable.class))).thenReturn(nodeTable);
        when(nodeStore.findOne(any(Long.class))).thenReturn(nodeTable);
        Either<CommandFailure, Long> response = nodeProcessManager.update(command);

        assertTrue(response.isRight());
        assertTrue(response.right().get() instanceof Long);
        assertTrue(response.right().get().equals(nodeTable.getId()));
    }

    @Test
    public void shouldUpdateNodeWithParent() {
        UpdateNode command = NodeProcessManagerImplMock.updateDataCommand(new Long(1),null);

        NodeTable parentTable = NodeProcessManagerImplMock.dataTable(new Long(1), null);
        NodeTable nodeTable = NodeProcessManagerImplMock.dataTable(new Long(2), parentTable);

        when(nodeStore.save(any(NodeTable.class))).thenReturn(nodeTable);
        when(nodeStore.findOne(any(Long.class))).thenReturn(nodeTable);
        Either<CommandFailure, Long> response = nodeProcessManager.update(command);

        assertTrue(response.isRight());
        assertTrue(response.right().get() instanceof Long);
        assertTrue(response.right().get().equals(nodeTable.getId()));
    }

    @Test
    public void shouldUpdateNodeNotFound() {
        UpdateNode command = NodeProcessManagerImplMock.updateDataCommand(new Long(1),new Long(1));
        NodeTable nodeTable = NodeProcessManagerImplMock.dataTable(new Long(2), null);

        when(nodeStore.save(any(NodeTable.class))).thenReturn(nodeTable);
        when(nodeStore.findOne(any(Long.class))).thenReturn(null);
        Either<CommandFailure, Long> response = nodeProcessManager.update(command);

        assertTrue(response.isLeft());
        assertTrue(response.left().get() instanceof CommandFailure);
        assertTrue(response.left().get().equals(CommandFailure.of(NodeErrorCode.NODE_NOT_FOUND)));
    }

    @Test
    public void shouldUpdateNodeParentEqualNode() {
        UpdateNode command = NodeProcessManagerImplMock.updateDataCommand(new Long(1),new Long(1));

        NodeTable parentTable = NodeProcessManagerImplMock.dataTable(new Long(1), null);
        NodeTable nodeTable = NodeProcessManagerImplMock.dataTable(new Long(2), parentTable);

        when(nodeStore.save(any(NodeTable.class))).thenReturn(nodeTable);
        when(nodeStore.findOne(any(Long.class))).thenReturn(nodeTable);
        Either<CommandFailure, Long> response = nodeProcessManager.update(command);

        assertTrue(response.isLeft());
        assertTrue(response.left().get() instanceof CommandFailure);
        assertTrue(response.left().get().equals(CommandFailure.of(NodeErrorCode.INVALID_PARENT)));
    }

}
