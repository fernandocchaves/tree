package com.github.fernandocchaves.tree.infrasctructure.persistence.repository;

import com.github.fernandocchaves.tree.domain.entity.NodeRepository;
import com.github.fernandocchaves.tree.domain.event.NodeCreated;
import com.github.fernandocchaves.tree.domain.event.NodeEvent;
import com.github.fernandocchaves.tree.domain.event.NodeUpdated;
import com.github.fernandocchaves.tree.domain.shared.CommandFailure;
import com.github.fernandocchaves.tree.domain.shared.NodeErrorCode;
import com.github.fernandocchaves.tree.infrasctructure.persistence.mapping.MappingFactory;
import com.github.fernandocchaves.tree.infrasctructure.persistence.mapping.NodeTable;
import com.github.fernandocchaves.tree.interfaces.rest.model.result.NodeParentResult;
import com.github.fernandocchaves.tree.interfaces.rest.model.result.NodeResult;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Slf4j
@Repository
public class NodeRepositoryImpl implements NodeRepository {

    private final NodeStore nodeStore;

    public NodeRepositoryImpl(NodeStore nodeStore) {
        this.nodeStore = nodeStore;
    }

    @Override
    public Either<CommandFailure, Long> store(NodeCreated nodeEvent) {
        NodeTable node = new NodeTable();

        if (Objects.nonNull(nodeEvent.getParentId())) {
            NodeTable parent = checkParent(nodeEvent.getParentId());

            if(Objects.isNull(parent)) {
                return Either.left(CommandFailure.of(NodeErrorCode.PARENT_NOT_FOUND));
            }

            node.setParentId(parent);
        }

        node.setCode(nodeEvent.getCode());
        node.setDescription(nodeEvent.getDescription());
        node.setDetail(nodeEvent.getDetail());

        log.debug("Repository event {}", node);

        NodeTable result = nodeStore.save(node);
        return Either.right(result.getId());
    }

    @Override
    public Either<CommandFailure, Long> store(NodeUpdated nodeEvent) {
        NodeTable node = checkNode(nodeEvent.getId());

        if(Objects.isNull(node)) {
            return Either.left(CommandFailure.of(NodeErrorCode.NODE_NOT_FOUND));
        }

        if (Objects.nonNull(nodeEvent.getParentId())) {

            if(nodeEvent.getParentId().equals(nodeEvent.getId())) {
                return Either.left(CommandFailure.of(NodeErrorCode.INVALID_PARENT));
            }

            NodeTable parent = checkParent(nodeEvent.getParentId());

            if(Objects.isNull(parent)) {
                return Either.left(CommandFailure.of(NodeErrorCode.PARENT_NOT_FOUND));
            }

            node.setParentId(parent);
        } else {
            node.setParentId(null);
        }

        node.setCode(nodeEvent.getCode());
        node.setDescription(nodeEvent.getDescription());
        node.setDetail(nodeEvent.getDetail());

        log.debug("Repository event {}", node);

        nodeStore.save(node);
        return Either.right(node.getId());
    }

    @Override
    public List<NodeResult> listAll() {
        List<NodeTable> nodes = nodeStore.findAllByParentId(null);
        return MappingFactory.map(nodes);
    }

    @Override
    public Either<CommandFailure, List<NodeParentResult>> listAllByParent(Long parentId) {
        NodeTable parent = nodeStore.findOne(parentId);

        if(Objects.isNull(parent)) {
            return Either.left(CommandFailure.of(NodeErrorCode.PARENT_NOT_FOUND));
        }

        List<NodeTable> nodes = nodeStore.findAllByParentId(parent);
        return Either.right(MappingFactory.mapParent(nodes));
    }

    private NodeTable checkParent(Long id) {
        NodeTable parent = nodeStore.findOne(id);
        return parent;
    }

    private NodeTable checkNode(Long id) {
        NodeTable node = nodeStore.findOne(id);
        return node;
    }
}
