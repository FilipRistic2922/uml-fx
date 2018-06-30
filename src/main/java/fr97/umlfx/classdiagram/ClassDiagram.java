package fr97.umlfx.classdiagram;


import fr97.umlfx.api.tool.UmlTool;
import fr97.umlfx.app.Localization;
import fr97.umlfx.classdiagram.edge.aggregation.AggregationEdge;
import fr97.umlfx.classdiagram.edge.association.AssociationEdge;
import fr97.umlfx.classdiagram.edge.composition.CompositionEdge;
import fr97.umlfx.classdiagram.edge.dependancy.DependancyEdge;
import fr97.umlfx.classdiagram.edge.inheritance.InheritanceEdge;
import fr97.umlfx.classdiagram.edge.realization.RealizationEdge;
import fr97.umlfx.classdiagram.node.classnode.ClassNode;
import fr97.umlfx.classdiagram.node.interfacenode.InterfaceNode;
import fr97.umlfx.common.AbstractDiagram;
import fr97.umlfx.common.edge.comment.CommentEdge;
import fr97.umlfx.common.node.comment.CommentNode;
import fr97.umlfx.common.tools.EdgeCreationTool;
import fr97.umlfx.common.tools.MoveTool;
import fr97.umlfx.common.tools.NodeCreationTool;
import fr97.umlfx.common.tools.SelectionTool;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * ClassDiagram implementation of {@link fr97.umlfx.api.UmlDiagram}
 */
public class ClassDiagram extends AbstractDiagram {

    private static final List<UmlTool> tools = new ArrayList<>(Arrays.asList(
            new SelectionTool(),
            new MoveTool(),
            new NodeCreationTool(ClassNode::new,
                    Localization.createStringBinding("classdiagram.tool.name.class"),
                    Localization.createStringBinding("classdiagram.tool.tooltip.class")),
            new NodeCreationTool(InterfaceNode::new,
                    Localization.createStringBinding("classdiagram.tool.name.interface"),
                    Localization.createStringBinding("classdiagram.tool.tooltip.interface")),
            new EdgeCreationTool(DependancyEdge::new,
                    Localization.createStringBinding("classdiagram.tool.name.dependency"),
                    Localization.createStringBinding("classdiagram.tool.tooltip.dependency")),
            new EdgeCreationTool(AssociationEdge::new,
                    Localization.createStringBinding("classdiagram.tool.name.association"),
                    Localization.createStringBinding("classdiagram.tool.tooltip.association")),
            new EdgeCreationTool(AggregationEdge::new,
                    Localization.createStringBinding("classdiagram.tool.name.aggregation"),
                    Localization.createStringBinding("classdiagram.tool.tooltip.aggregation")),
            new EdgeCreationTool(CompositionEdge::new,
                    Localization.createStringBinding("classdiagram.tool.name.composition"),
                    Localization.createStringBinding("classdiagram.tool.tooltip.composition")),
            new EdgeCreationTool(InheritanceEdge::new,
                    Localization.createStringBinding("classdiagram.tool.name.inheritance"),
                    Localization.createStringBinding("classdiagram.tool.tooltip.inheritance")),
            new EdgeCreationTool(RealizationEdge::new,
                    Localization.createStringBinding("classdiagram.tool.name.realization"),
                    Localization.createStringBinding("classdiagram.tool.tooltip.realization")),
            new NodeCreationTool(CommentNode::new,
                    Localization.createStringBinding("common.tool.name.commentnode"),
                    Localization.createStringBinding("common.tool.tooltip.commentnode")),
            new EdgeCreationTool(CommentEdge::new,
                    Localization.createStringBinding("common.tool.name.commentedge"),
                    Localization.createStringBinding("common.tool.tooltip.commentedge"))
            ));

    /**
     * Constructor with name
     * @param name name of this diagram
     */
    public ClassDiagram(String name) {
        super(name);
    }

    /**
     * Default Constructor
     */
    public ClassDiagram(){

    }


    /**
     *
     * {@inheritDoc}
     */
    @Override
    public List<UmlTool> getSupportedTools() {
        return tools;
    }
}
