package rainfall;

/** Small helper for escaping text placed inside XML. */
final class XmlUtil {
    private XmlUtil() { }

    static String escape(String text) {
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&apos;");
    }
}
