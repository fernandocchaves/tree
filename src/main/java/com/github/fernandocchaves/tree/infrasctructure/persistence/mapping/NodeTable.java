package com.github.fernandocchaves.tree.infrasctructure.persistence.mapping;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@Entity
@Table(name="node")
public class NodeTable {

    @Tolerate
    public NodeTable() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String code;
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parentId", referencedColumnName = "id")
    private NodeTable parentId;

    private String detail;

    @OneToMany(mappedBy = "parentId", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<NodeTable> children;

}
