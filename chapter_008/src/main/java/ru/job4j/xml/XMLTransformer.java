package ru.job4j.xml;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import static com.google.common.io.Resources.getResource;

/**
 * Provides transforming entries xml file to another one
 *
 * @author Alexander Savchenko
 * @version 1.0
 * @since 2019-07-26
 */
public class XMLTransformer {
    private final Transformer transformer;

    public XMLTransformer() {
        this(getResource("xml/schema.xsl"));
    }

    public XMLTransformer(URL schema) {
        TransformerFactory factory = TransformerFactory.newInstance();
        try {
            transformer = factory.newTransformer(new StreamSource(schema.openStream()));
        } catch (TransformerConfigurationException | IOException e) {
            throw new IllegalStateException("error occurs due xml transformer initialization", e);
        }
    }

    /**
     * Transforms xml from source input stream to target output stream
     *
     * @param source input xml
     * @param target output xml
     */
    public void transform(InputStream source, OutputStream target) {
        try {
            transformer.transform(new StreamSource(source), new StreamResult(target));
        } catch (TransformerException e) {
            throw new IllegalStateException("cannot transform xml data", e);
        }
    }
}