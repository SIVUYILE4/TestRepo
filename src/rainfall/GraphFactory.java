package rainfall;

/**
 * Factory pattern.
 * Creates the graph the user chooses, so the client never depends on
 * concrete graph classes. Adding a new graph type means adding an enum
 * constant and a case here - the client is unchanged.
 */
public final class GraphFactory {

    public enum GraphType { BAR, COLUMN }

    private GraphFactory() { }

    public static Graph create(GraphType type) {
        return switch (type) {
            case BAR -> new BarGraph();
            case COLUMN -> new ColumnGraph();
        };
    }
}
