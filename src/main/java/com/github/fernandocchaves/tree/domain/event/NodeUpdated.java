package com.github.fernandocchaves.tree.domain.event;

import lombok.Value;

@Value(staticConstructor = "eventOf")
public class NodeUpdated implements NodeEvent {
    private Long id;
    private String code;
    private String description;
    private Long parentId;
    private String detail;
}
