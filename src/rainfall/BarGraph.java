package rainfall;

import java.util.List;

/** Strategy pattern - Concrete Strategy: horizontal bars. */
public class BarGraph extends AbstractGraph {
    private static final int MAX_WIDTH = 40;

    @Override
    protected String title() {
        return "Rainfall - Bar Graph (mm)";
    }

    @Override
    protected String render(List<DataPoint> points) {
        double max = max(points);
        int labelWidth = points.stream().mapToInt(p -> p.label().length()).max().orElse(0);

        StringBuilder out = new StringBuilder();
        for (DataPoint p : points) {
            int length = max == 0 ? 0 : (int) Math.round(p.value() / max * MAX_WIDTH);
            out.append(String.format("%-" + labelWidth + "s | %s %.1f%n",
                    p.label(), "#".repeat(length), p.value()));
        }
        return out.toString();
    }
}
