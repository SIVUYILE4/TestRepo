package rainfall;

import java.util.List;

/** Strategy pattern - Concrete Strategy: vertical columns. */
public class ColumnGraph extends AbstractGraph {
    private static final int MAX_HEIGHT = 10;
    private static final int COLUMN_WIDTH = 6;

    @Override
    protected String title() {
        return "Rainfall - Column Graph (mm)";
    }

    @Override
    protected String render(List<DataPoint> points) {
        double max = max(points);
        int[] heights = points.stream()
                .mapToInt(p -> max == 0 ? 0 : (int) Math.round(p.value() / max * MAX_HEIGHT))
                .toArray();

        StringBuilder out = new StringBuilder();
        for (int row = MAX_HEIGHT; row >= 1; row--) {
            for (int h : heights) {
                out.append(center(h >= row ? "##" : "", COLUMN_WIDTH));
            }
            out.append('\n');
        }
        out.append("-".repeat(COLUMN_WIDTH * points.size())).append('\n');
        for (DataPoint p : points) {
            out.append(center(String.format("%.1f", p.value()), COLUMN_WIDTH));
        }
        out.append("\n\nColumns (left to right):\n");
        for (int i = 0; i < points.size(); i++) {
            out.append(String.format("  %d. %s%n", i + 1, points.get(i).label()));
        }
        return out.toString();
    }

    private static String center(String text, int width) {
        int left = Math.max(0, (width - text.length()) / 2);
        int right = Math.max(0, width - text.length() - left);
        return " ".repeat(left) + text + " ".repeat(right);
    }
}
