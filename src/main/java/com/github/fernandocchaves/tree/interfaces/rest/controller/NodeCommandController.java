package com.github.fernandocchaves.tree.interfaces.rest.controller;

import com.github.fernandocchaves.tree.application.NodeProcessManager;
import com.github.fernandocchaves.tree.domain.command.CreateNode;
import com.github.fernandocchaves.tree.domain.command.UpdateNode;
import com.github.fernandocchaves.tree.domain.shared.CommandFailure;
import com.github.fernandocchaves.tree.interfaces.rest.model.request.CreateNodeRequest;
import com.github.fernandocchaves.tree.interfaces.rest.model.request.UpdateNodeRequest;
import io.vavr.control.Either;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
public class NodeCommandController {

    private final NodeProcessManager nodeProcessManager;

    public NodeCommandController(NodeProcessManager nodeProcessManager) {
        this.nodeProcessManager = nodeProcessManager;
    }

    @PostMapping(path = "/node", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> create(@Valid @RequestBody CreateNodeRequest request) {

        CreateNode command = CreateNode.commandOf(
                request.getCode(),
                request.getDescription(),
                request.getParentId(),
                request.getDetail()
        );

        log.debug("Controller command {}", command);

        Either<CommandFailure, Long> response = nodeProcessManager.create(command);

        if(response.isLeft()) {
            return new ResponseEntity<>(response.left().get(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>(response.right().get(), HttpStatus.OK);

    }

    @PutMapping(path = "/node", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> update(@Valid @RequestBody UpdateNodeRequest request) {

        UpdateNode command = UpdateNode.commandOf(
                request.getId(),
                request.getCode(),
                request.getDescription(),
                request.getParentId(),
                request.getDetail()
        );

        log.debug("Controller command {}", command);

        Either<CommandFailure, Long> response = nodeProcessManager.update(command);

        if(response.isLeft()) {
            return new ResponseEntity<>(response.left().get(), HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return new ResponseEntity<>(response.right().get(), HttpStatus.OK);

    }

}
