package com.github.fernandocchaves.tree.domain.entity;

import com.github.fernandocchaves.tree.domain.event.NodeCreated;
import com.github.fernandocchaves.tree.domain.event.NodeEvent;
import com.github.fernandocchaves.tree.domain.event.NodeUpdated;
import com.github.fernandocchaves.tree.domain.shared.CommandFailure;
import com.github.fernandocchaves.tree.interfaces.rest.model.result.NodeParentResult;
import com.github.fernandocchaves.tree.interfaces.rest.model.result.NodeResult;
import io.vavr.control.Either;

import java.util.List;

public interface NodeRepository {
    Either<CommandFailure, Long> store(NodeCreated nodeEvent);
    Either<CommandFailure, Long> store(NodeUpdated nodeEvent);
    List<NodeResult> listAll();
    Either<CommandFailure, List<NodeParentResult>> listAllByParent(Long parentId);
}
