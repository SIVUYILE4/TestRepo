package rainfall;

import java.time.LocalDate;
import java.util.Scanner;

/**
 * The main client. It:
 *  1. holds the record of all the rainfall data,
 *  2. gets the XML representation of all the rainfall data,
 *  3. passes it to the user's choice of graph.
 */
public class RainfallClient {
    private final RainRecordCollection records = new RainRecordCollection("All rainfall");

    public void addRecord(String station, LocalDate date, double amountMm) {
        records.add(new RainRecord(station, date, amountMm));
    }

    public String getXML() {
        return records.toXML();
    }

    public String graph(GraphFactory.GraphType type) {
        Graph graph = GraphFactory.create(type);
        return graph.draw(getXML());
    }

    public static void main(String[] args) {
        RainfallClient client = new RainfallClient();
        client.addRecord("Cape Town", LocalDate.of(2026, 6, 1), 12.4);
        client.addRecord("Cape Town", LocalDate.of(2026, 6, 2), 3.0);
        client.addRecord("Durban", LocalDate.of(2026, 6, 1), 25.8);
        client.addRecord("Johannesburg", LocalDate.of(2026, 6, 1), 0.0);
        client.addRecord("Johannesburg", LocalDate.of(2026, 6, 2), 7.6);

        System.out.println("XML representation:");
        System.out.println(client.getXML());
        System.out.println();

        GraphFactory.GraphType choice;
        if (args.length > 0) {
            choice = GraphFactory.GraphType.valueOf(args[0].toUpperCase());
        } else {
            System.out.print("Choose a graph (bar/column): ");
            Scanner in = new Scanner(System.in);
            String input = in.hasNextLine() ? in.nextLine().trim() : "bar";
            choice = input.equalsIgnoreCase("column")
                    ? GraphFactory.GraphType.COLUMN
                    : GraphFactory.GraphType.BAR;
        }

        System.out.println(client.graph(choice));
    }
}
