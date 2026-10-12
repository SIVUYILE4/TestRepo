package rainfall;

/**
 * Strategy pattern - Strategy interface.
 * Every graph accepts the XML representation of the rainfall container
 * and returns the rendered graph.
 */
public interface Graph {
    String draw(String xml);
}
