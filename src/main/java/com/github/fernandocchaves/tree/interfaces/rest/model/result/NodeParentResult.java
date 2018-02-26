package com.github.fernandocchaves.tree.interfaces.rest.model.result;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import java.util.List;

@Data
@Builder
public class NodeParentResult {

    @Tolerate
    public NodeParentResult() {
    }

    private Long id;
    private String code;
    private String description;
    private Long parentId;
    private String detail;
    private Boolean hasChildren;
}
