package rainfall;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Composite pattern - Leaf.
 * A single rainfall measurement: station, date and amount in mm.
 * Immutable value object.
 */
public final class RainRecord implements RainData {
    private final String station;
    private final LocalDate date;
    private final double amountMm;

    public RainRecord(String station, LocalDate date, double amountMm) {
        if (station == null || station.isBlank()) {
            throw new IllegalArgumentException("Station is required");
        }
        if (amountMm < 0) {
            throw new IllegalArgumentException("Rainfall cannot be negative");
        }
        this.station = station;
        this.date = Objects.requireNonNull(date, "Date is required");
        this.amountMm = amountMm;
    }

    public String getStation() { return station; }
    public LocalDate getDate() { return date; }
    public double getAmountMm() { return amountMm; }

    @Override
    public String toXML() {
        return "<rainRecord>"
                + "<station>" + XmlUtil.escape(station) + "</station>"
                + "<date>" + date + "</date>"
                + "<amount unit=\"mm\">" + amountMm + "</amount>"
                + "</rainRecord>";
    }

    @Override
    public String toString() {
        return station + " " + date + " " + amountMm + "mm";
    }
}
