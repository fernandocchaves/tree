package com.github.fernandocchaves.tree.interfaces.rest.controller;

import com.github.fernandocchaves.tree.application.NodeProcessManager;
import com.github.fernandocchaves.tree.domain.shared.CommandFailure;
import com.github.fernandocchaves.tree.infrasctructure.persistence.mapping.NodeTable;
import com.github.fernandocchaves.tree.infrasctructure.persistence.repository.NodeStore;
import com.github.fernandocchaves.tree.interfaces.rest.model.result.NodeParentResult;
import com.github.fernandocchaves.tree.interfaces.rest.model.result.NodeResult;
import io.vavr.control.Either;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class NodeQueryController {

    private final NodeProcessManager nodeProcessManager;

    public NodeQueryController(NodeProcessManager nodeProcessManager) {
        this.nodeProcessManager = nodeProcessManager;
    }

    @Autowired
    NodeStore nodeRepository;

    @GetMapping(path = "/node", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> list() {
        return new ResponseEntity<>(nodeProcessManager.listAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/node/{parentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listParent(@PathVariable Long parentId) {

        Either<CommandFailure, List<NodeParentResult>> response = nodeProcessManager.listAllByParent(parentId);

        if(response.isLeft()) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(response.right().get(), HttpStatus.OK);
    }

}
