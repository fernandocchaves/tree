package com.github.fernandocchaves.tree.application;

import com.github.fernandocchaves.tree.domain.command.CreateNode;
import com.github.fernandocchaves.tree.domain.command.UpdateNode;
import com.github.fernandocchaves.tree.domain.shared.CommandFailure;
import com.github.fernandocchaves.tree.interfaces.rest.model.result.NodeParentResult;
import com.github.fernandocchaves.tree.interfaces.rest.model.result.NodeResult;
import io.vavr.control.Either;

import java.util.List;

public interface NodeProcessManager {
    Either<CommandFailure, Long> create(CreateNode command);
    Either<CommandFailure, Long> update(UpdateNode command);
    List<NodeResult> listAll();
    Either<CommandFailure, List<NodeParentResult>> listAllByParent(Long parentId);
}
