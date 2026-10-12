package rainfall;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Template Method pattern.
 * draw() fixes the algorithm: parse the XML into data points, then render.
 * Subclasses only supply the rendering step.
 */
public abstract class AbstractGraph implements Graph {

    /** A label/value pair extracted from the XML. */
    protected record DataPoint(String label, double value) { }

    @Override
    public final String draw(String xml) {
        List<DataPoint> points = parse(xml);
        if (points.isEmpty()) {
            return title() + "\n(no data)\n";
        }
        return title() + "\n" + render(points);
    }

    protected abstract String title();

    protected abstract String render(List<DataPoint> points);

    private List<DataPoint> parse(String xml) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));

            List<DataPoint> points = new ArrayList<>();
            NodeList records = doc.getElementsByTagName("rainRecord");
            for (int i = 0; i < records.getLength(); i++) {
                Element record = (Element) records.item(i);
                String station = text(record, "station");
                String date = text(record, "date");
                double amount = Double.parseDouble(text(record, "amount"));
                points.add(new DataPoint(station + " " + date, amount));
            }
            return points;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid rainfall XML", e);
        }
    }

    private static String text(Element parent, String tag) {
        return parent.getElementsByTagName(tag).item(0).getTextContent();
    }

    protected static double max(List<DataPoint> points) {
        return points.stream().mapToDouble(DataPoint::value).max().orElse(0);
    }
}
