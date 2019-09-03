package ru.job4j.xml;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.util.StringJoiner;

import static com.google.common.io.Resources.getResource;
import static java.lang.System.lineSeparator;
import static org.junit.Assert.assertEquals;

public class XMLTransformerTest {
    private static final String LS = lineSeparator();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testTransform() throws Exception {
        String expected = new StringJoiner(LS)
                .add("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
                .add("<entries xsi:schemaLocation=\"http://job4j.ru output.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://job4j.ru\">")
                .add("    <entry field=\"1\"/>")
                .add("    <entry field=\"2\"/>")
                .add("    <entry field=\"3\"/>")
                .add("    <entry field=\"4\"/>")
                .add("    <entry field=\"5\"/>")
                .add("</entries>")
                .add("")
                .toString();
        XMLTransformer transformer = new XMLTransformer();
        try (InputStream in = getResource("xml/first.xml").openStream();
             OutputStream out = new ByteArrayOutputStream()) {
            transformer.transform(in, out);
            System.out.println(out.toString());
            validateXmlBySchema(out.toString());

            assertEquals(expected, out.toString());
        }
    }

    @Test
    public void testWrongTransform() throws Exception {
        String xml = new StringJoiner(LS)
                .add("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
                .add("<entries>")
                .add("  <entry>")
                .add("    <field>1<field>")
                .add("  </entry>")
                .add("</entries>")
                .toString();
        XMLTransformer transformer = new XMLTransformer();
        try (InputStream in = new ByteArrayInputStream(xml.getBytes());
             OutputStream out = new ByteArrayOutputStream()) {
            thrown.expect(IllegalStateException.class);
            thrown.expectMessage("cannot transform xml data");
            transformer.transform(in, out);
        }
    }

    @Test
    public void testTransformWrongSchema() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("error occurs due xml transformer initialization");
        new XMLTransformer(getResource("xml/brokenSchema.xsl"));
    }

    private void validateXmlBySchema(String xml) throws IOException, SAXException {
        Schema schema = getOutputSchema();
        try (StringReader reader = new StringReader(xml)) {
            schema.newValidator().validate(new StreamSource(reader));
        }
    }

    private Schema getOutputSchema() throws SAXException {
        return SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(
                getResource("xml/output.xsd"));
    }
}