package fi.danielsan.kinodata.api.xml;

import org.joda.time.DateTime;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.Registry;
import org.simpleframework.xml.convert.RegistryStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import fi.danielsan.kinodata.api.models.theatres.TheatreAreas;

public class Serializers {

    private Serializers() {
    }

    public static Serializer createSerializer() {
        Registry registry = new Registry();
        Strategy strategy = new RegistryStrategy(registry);
        Serializer serializer = new Persister(strategy);

        try {
            registry.bind(DateTime.class, new DateTimeConverter());
            registry.bind(TheatreAreas.class, new TheatreAreasConverter());
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }

        return serializer;
    }
}