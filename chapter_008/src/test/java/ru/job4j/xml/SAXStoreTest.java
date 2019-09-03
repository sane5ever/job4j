package ru.job4j.xml;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public class SAXStoreTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testCount() throws Exception {
        SAXStore store = new SAXStore();
        String xml = "<entries>\n"
                + "    <entry field = \"1\"/>\n"
                + "    <entry field = \"1\"/>\n"
                + "    <entry field = \"1\"/>\n"
                + "    <entry field = \"1\"/>\n"
                + "    <entry field = \"1\"/>\n"
                + "</entries>";
        try (InputStream in = new ByteArrayInputStream(xml.getBytes())) {
            long result = store.count(in);
            assertEquals(5, result);
        }
    }

    @Test
    public void testCountWithError() throws Exception {
        SAXStore store = new SAXStore();
        String xml = "<entries>\n"
                + "    <entry field = 1/>\n"
                + "</entries>";
        try (InputStream in = new ByteArrayInputStream(xml.getBytes())) {
            thrown.expect(IllegalStateException.class);
            thrown.expectMessage("cannot parse file");
            store.count(in);
        }
    }

    @Test
    public void testCountTwice() throws Exception {
        SAXStore store = new SAXStore();
        String firstXml = "<entries>\n"
                + "    <entry field = \"1\"/>\n"
                + "    <entry field = \"1\"/>\n"
                + "</entries>";

        String secondXml = "<entries>\n"
                + "    <entry field = \"8\"/>\n"
                + "    <entry field = \"8\"/>\n"
                + "</entries>";

        try (InputStream in = new ByteArrayInputStream(firstXml.getBytes())) {
            long result = store.count(in);
            assertEquals(2, result);
        }

        try (InputStream in = new ByteArrayInputStream(secondXml.getBytes())) {
            long result = store.count(in);
            assertEquals(16, result);
        }
    }
}