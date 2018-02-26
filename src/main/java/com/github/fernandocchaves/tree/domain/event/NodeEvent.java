package com.github.fernandocchaves.tree.domain.event;

import com.github.fernandocchaves.tree.domain.shared.Event;

public interface NodeEvent extends Event {
    Long getParentId();
    String getCode();
    String getDescription();
    String getDetail();
}
