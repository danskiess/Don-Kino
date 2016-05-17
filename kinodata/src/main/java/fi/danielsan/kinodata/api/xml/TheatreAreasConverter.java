package fi.danielsan.kinodata.api.xml;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

import java.util.ArrayList;
import java.util.List;

import fi.danielsan.kinodata.api.models.theatres.TheatreArea;
import fi.danielsan.kinodata.api.models.theatres.TheatreAreas;

public class TheatreAreasConverter implements Converter {

    @Override
    public Object read(InputNode node) throws Exception {
        List<TheatreArea> theatreAreas = new ArrayList<>();
        InputNode childNode;
        Serializer serializer = Serializers.createSerializer();

        while((childNode = node.getNext()) != null) {
            TheatreArea theatreArea = serializer.read(TheatreArea.class, childNode);
            theatreAreas.add(theatreArea);
        }
        theatreAreas.remove(0);
        return new TheatreAreas(theatreAreas);
    }

    @Override
    public void write(OutputNode node, Object value) throws Exception {
        throw new Exception("Writing not implemented.");
    }
}
