
package ru.job4j.xml.schema;

import javax.xml.bind.annotation.*;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="entry" type="{http://job4j.ru}entryType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "entry"
})
@XmlRootElement(name = "entries", namespace = "http://job4j.ru")
public class Entries {

    public Entries() {
    }

    @XmlElement(namespace = "http://job4j.ru")
    protected List<EntryType> entry;

    public void setEntry(List<EntryType> entry) {
        this.entry = entry;
    }
}
