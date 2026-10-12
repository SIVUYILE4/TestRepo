# Rainfall Tracker

A small Java application that records rainfall data, converts it to XML and
draws it as a bar graph or a column graph, using appropriate design patterns.

## Run

```bash
javac -d out src/rainfall/*.java
java -cp out rainfall.RainfallClient bar      # or: column
java -cp out rainfall.RainfallClient          # prompts for a choice
```

## Requirements → design

| Requirement | Class(es) | Pattern |
|---|---|---|
| Rain data = station, date, amount (mm) | `RainRecord` | Immutable value object (Composite **Leaf**) |
| A container for all the data | `RainRecordCollection` | **Composite** (Composite node) + **Iterator** (`Iterable`) |
| An XML representation of a rain record / the container | `RainData.toXML()` | **Composite** – leaf and container share one interface, so the container's XML is built from its children's XML |
| Bar graph and column graph that take XML | `Graph`, `BarGraph`, `ColumnGraph` | **Strategy** – interchangeable graph algorithms |
| Shared "parse XML, then draw" algorithm | `AbstractGraph` | **Template Method** – subclasses supply only `render()` |
| The user's choice of graph | `GraphFactory` | **Factory** – client never names a concrete graph class |
| Client holds the data, gets the XML, passes it to the graph | `RainfallClient` | Client |

## Class diagram

```
            «interface»                       «interface»
             RainData  <------------------+     Graph
           + toXML()                      |   + draw(xml)
              ^     ^                     |       ^
              |     |                     |       |
     RainRecord     RainRecordCollection -+  AbstractGraph   (template method)
     - station      - children: List<RainData>  + draw(xml)  final
     - date         + add / remove / iterator   # render(points)
     - amountMm     + toXML()                    ^        ^
     + toXML()                                   |        |
                                             BarGraph  ColumnGraph

  RainfallClient --holds--> RainRecordCollection
  RainfallClient --uses---> GraphFactory --creates--> Graph
```

## Extending

* **New graph type** (e.g. line graph): subclass `AbstractGraph`, add a
  constant to `GraphFactory.GraphType` and a case in `GraphFactory.create`.
  Nothing else changes (Open/Closed principle).
* **Grouping** (e.g. one collection per station): because
  `RainRecordCollection` is itself a `RainData`, collections can be nested and
  the XML still works.
