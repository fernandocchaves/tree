package com.github.fernandocchaves.tree.interfaces.rest.model.response;

import com.github.fernandocchaves.tree.interfaces.rest.model.result.NodeResult;
import lombok.Data;

import java.util.List;

@Data
public class NodeListResponse {
    List<NodeResult> nodes;
}
