package com.github.fernandocchaves.tree.domain.command;

import lombok.Value;

@Value(staticConstructor = "commandOf")
public class CreateNode implements NodeCommand {
    private String code;
    private String description;
    private Long parentId;
    private String detail;
}
