package rainfall;

/**
 * Composite pattern - Component.
 * Anything that holds rain data (a single record or a container of records)
 * can produce an XML representation of itself.
 */
public interface RainData {
    String toXML();
}
