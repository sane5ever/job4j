package ru.job4j.xml;

import org.xml.sax.SAXException;
import ru.job4j.xml.schema.Entries;
import ru.job4j.xml.schema.EntryType;
import ru.job4j.xml.schema.ObjectFactory;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static com.google.common.io.Resources.getResource;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Provides storing entries to xml file
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-07-26
 */
public class XMLStore {

    private final JAXBContext jaxbContext;

    private Marshaller marshaller;
    private Schema schema;


    public XMLStore() {
        try {
            jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
        } catch (JAXBException e) {
            throw new IllegalStateException("error occurs due JAXB context initialization", e);
        }
    }

    public XMLStore initMarshaller() {
        try {
            marshaller = jaxbContext.createMarshaller();
            setProperties();
            setSchema();
        } catch (JAXBException | SAXException e) {
            throw new IllegalStateException("error occurs due marshaller initialization", e);
        }
        return this;
    }

    public Schema xmlSchema() {
        return schema;
    }

    public void saveToXmlFile(List<EntryType> entries, String path) {
        try {
            String xml = generateXml(entries);
            Path file = Paths.get(path);
            Files.writeString(file, xml);
        } catch (IOException e) {
            throw new IllegalStateException("cannot save xml data to file", e);
        }
    }

    String generateXml(List<EntryType> entries) {
        Entries entriesShell = new Entries();
        entriesShell.setEntry(entries);
        StringWriter buffer = new StringWriter();
        try {
            marshaller.marshal(entriesShell, buffer);
        } catch (JAXBException e) {
            throw new IllegalStateException("cannot create xml data", e);
        }
        return buffer.toString();
    }

    private void setProperties() throws PropertyException {
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.setProperty(Marshaller.JAXB_ENCODING, UTF_8.name());
        marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
        marshaller.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://job4j.ru input.xsd");
    }

    private void setSchema() throws SAXException {
        schema = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(
                getResource("xml/input.xsd"));
        marshaller.setSchema(schema);
    }
}
