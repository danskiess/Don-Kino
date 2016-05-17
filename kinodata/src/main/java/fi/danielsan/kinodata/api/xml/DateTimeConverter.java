package fi.danielsan.kinodata.api.xml;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.simpleframework.xml.convert.Converter;
import org.simpleframework.xml.stream.InputNode;
import org.simpleframework.xml.stream.OutputNode;

class DateTimeConverter implements Converter<DateTime> {

    @Override
    public DateTime read(InputNode inputNode) throws Exception {
        return ISODateTimeFormat.dateHourMinuteSecond().parseDateTime(inputNode.getValue());
    }

    @Override
    public void write(OutputNode outputNode, DateTime dateTime) throws Exception {
        throw new UnsupportedOperationException();
    }
}