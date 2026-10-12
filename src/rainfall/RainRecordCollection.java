package rainfall;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Composite pattern - Composite, and Iterator pattern (via Iterable).
 * The container holding all rainfall data. Its XML representation is built
 * from the XML of every element it holds, so it can hold single records or
 * other collections (e.g. one collection per station) transparently.
 */
public class RainRecordCollection implements RainData, Iterable<RainData> {
    private final String name;
    private final List<RainData> children = new ArrayList<>();

    public RainRecordCollection(String name) {
        this.name = name;
    }

    public void add(RainData data) {
        children.add(data);
    }

    public boolean remove(RainData data) {
        return children.remove(data);
    }

    public int size() {
        return children.size();
    }

    @Override
    public Iterator<RainData> iterator() {
        return Collections.unmodifiableList(children).iterator();
    }

    @Override
    public String toXML() {
        StringBuilder xml = new StringBuilder();
        xml.append("<rainRecords name=\"").append(XmlUtil.escape(name)).append("\">");
        for (RainData child : children) {
            xml.append(child.toXML());
        }
        xml.append("</rainRecords>");
        return xml.toString();
    }
}
