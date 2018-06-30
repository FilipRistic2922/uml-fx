package fr97.umlfx.command;


import fr97.umlfx.api.UmlDiagram;
import fr97.umlfx.api.node.UmlNode;

public class NewNodeCommand implements Command{

    private final UmlNode node;
    private final UmlDiagram diagram;
    public NewNodeCommand(final UmlNode node, final UmlDiagram diagram){
        this.node = node;
        this.diagram = diagram;
    }


    @Override
    public void execute() {
        diagram.addNode(node);
    }

    @Override
    public void undo() {
        diagram.removeNode(node);
    }
}
