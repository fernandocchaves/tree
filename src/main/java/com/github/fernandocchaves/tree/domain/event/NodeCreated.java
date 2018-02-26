package com.github.fernandocchaves.tree.domain.event;

import lombok.Value;

@Value(staticConstructor = "eventOf")
public class NodeCreated implements NodeEvent {
    private String code;
    private String description;
    private Long parentId;
    private String detail;
}
