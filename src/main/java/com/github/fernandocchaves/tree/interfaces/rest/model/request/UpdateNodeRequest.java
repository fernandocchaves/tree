package com.github.fernandocchaves.tree.interfaces.rest.model.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UpdateNodeRequest {

    @NotNull
    private Long id;
    @NotNull
    private String code;
    @NotNull
    private String description;
    @NotNull
    private String detail;

    private Long parentId;
}
