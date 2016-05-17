package fi.danielsan.donkino.data.api.xml;

import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

class DateConverter implements Converter<LocalDateTime> {

    @Override
    public LocalDateTime read(InputNode inputNode) throws Exception {
        return LocalDateTime.from(DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(inputNode.getValue()));
    }

    @Override
    public void write(OutputNode outputNode, LocalDateTime dateTime) throws Exception {
        throw new UnsupportedOperationException();
    }
}