package com.github.fernandocchaves.tree.domain.shared;

import lombok.Value;

@Value(staticConstructor = "of")
public class CommandFailure {
    private NodeErrorCode error;
}
