package ru.job4j.xml;

import one.util.streamex.IntStreamEx;
import org.junit.Test;
import org.xml.sax.SAXException;
import ru.job4j.xml.schema.EntryType;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.Assert.fail;

public class XMLStoreTest {
    @Test
    public void test() throws Exception {
        XMLStore store = new XMLStore().initMarshaller();
        String xml = store.generateXml(generateEntries(10));

        try {
            validate(store, xml);
        } catch (SAXException e) {
            fail("validation failed: " + e.getMessage());
        }
    }

    @Test
    public void testWrong() throws Exception {
        XMLStore store = new XMLStore().initMarshaller();
        String wrongXml = store
                .generateXml(generateEntries(20))
                .replace("field", "fild");
        try {
            validate(store, wrongXml);
            fail("not supposed to pass here");
        } catch (SAXException e) {
            // must come here
        }
    }

    @Test
    public void testWriteToFile() throws Exception {
        XMLStore store = new XMLStore().initMarshaller();
        Path tmpXml = Files.createTempFile(null, null);
        store.saveToXmlFile(generateEntries(10), tmpXml.toString());
        try {
            validate(store, tmpXml);
        } catch (SAXException e) {
            fail("validation failed: " + e.getMessage());
        }
    }

    @Test(expected = IllegalStateException.class)
    public void testWriteToFileWithError() {
        XMLStore store = new XMLStore().initMarshaller();
        store.saveToXmlFile(generateEntries(10), ".");
    }

    private List<EntryType> generateEntries(int size) {
        return IntStreamEx.constant(5, size).mapToObj(EntryType::new).toList();
    }

    private void validate(XMLStore store, String xml) throws IOException, SAXException {
        validate(store, () -> new StringReader(xml));
    }

    private void validate(XMLStore store, Path file) throws IOException, SAXException {
        validate(store, () -> Files.newBufferedReader(file));
    }


    private void validate(XMLStore store, ReaderCreator readerCreator) throws IOException, SAXException {
        try (Reader reader = readerCreator.create()) {
            store.xmlSchema().newValidator().validate(new StreamSource(reader));
        }
    }

    private interface ReaderCreator {
        Reader create() throws IOException;
    }
}
