package com.github.fernandocchaves.tree.infrasctructure.persistence.repository;

import com.github.fernandocchaves.tree.infrasctructure.persistence.mapping.NodeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NodeStore extends JpaRepository<NodeTable, Long> {
    List<NodeTable> findAllByParentId(NodeTable parent);
}
