package com.github.fernandocchaves.tree.domain.shared;

public class NodeErrorCode extends ErrorCode {
    public final static NodeErrorCode NODE_NOT_FOUND = new NodeErrorCode("ERROR-001", "Node not found");
    public final static NodeErrorCode PARENT_NOT_FOUND = new NodeErrorCode("ERROR-002", "Parent not found");
    public final static NodeErrorCode INVALID_PARENT = new NodeErrorCode("ERROR-003", "Invalid parent");

    protected NodeErrorCode(String code, String message) {
        super(code, message);
    }
}
