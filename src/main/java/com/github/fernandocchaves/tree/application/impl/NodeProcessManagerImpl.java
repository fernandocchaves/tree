package com.github.fernandocchaves.tree.application.impl;

import com.github.fernandocchaves.tree.application.NodeProcessManager;
import com.github.fernandocchaves.tree.domain.command.CreateNode;
import com.github.fernandocchaves.tree.domain.command.UpdateNode;
import com.github.fernandocchaves.tree.domain.event.NodeCreated;
import com.github.fernandocchaves.tree.domain.event.NodeUpdated;
import com.github.fernandocchaves.tree.domain.shared.CommandFailure;
import com.github.fernandocchaves.tree.infrasctructure.persistence.repository.NodeRepositoryImpl;
import com.github.fernandocchaves.tree.interfaces.rest.model.result.NodeParentResult;
import com.github.fernandocchaves.tree.interfaces.rest.model.result.NodeResult;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NodeProcessManagerImpl implements NodeProcessManager {

    private final NodeRepositoryImpl nodeRepository;

    public NodeProcessManagerImpl(NodeRepositoryImpl nodeRepository) {
        this.nodeRepository = nodeRepository;
    }

    @Override
    public Either<CommandFailure, Long> create(CreateNode command) {

        NodeCreated event = NodeCreated.eventOf(
                command.getCode(),
                command.getDescription(),
                command.getParentId(),
                command.getDetail()
        );

        log.debug("Service event {}", event);

        return nodeRepository.store(event);
    }

    @Override
    public Either<CommandFailure, Long> update(UpdateNode command) {
        NodeUpdated event = NodeUpdated.eventOf(
                command.getId(),
                command.getCode(),
                command.getDescription(),
                command.getParentId(),
                command.getDetail()
        );

        log.debug("Service event {}", event);

        return nodeRepository.store(event);
    }


    @Override
    public List<NodeResult> listAll() {
        List<NodeResult> nodes = nodeRepository.listAll();
        return nodes;
    }

    @Override
    public Either<CommandFailure, List<NodeParentResult>> listAllByParent(Long parentId) {
        Either<CommandFailure, List<NodeParentResult>> nodes = nodeRepository.listAllByParent(parentId);
        return nodes;
    }
}
