package com.github.fernandocchaves.tree.interfaces.rest.model.result;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;
import java.util.Optional;

@Data
@Builder
public class NodeResult {

    @Tolerate
    public NodeResult() {
    }

    private Long id;
    private String code;
    private String description;
    private Long parentId;
    private String detail;
    private List<NodeResult> children;
}
