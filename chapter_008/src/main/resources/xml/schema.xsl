<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
        xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
        xmlns:p="http://job4j.ru" exclude-result-prefixes="p">
    <xsl:output method="xml" version="1.0" encoding="utf-8" indent="yes"/>
    <xsl:template match="/">
        <xsl:text>&#xa;</xsl:text><!-- put in the newline -->
        <entries xmlns="http://job4j.ru"
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="http://job4j.ru output.xsd">
            <xsl:for-each select="p:entries/p:entry">
                <entry>
                    <xsl:attribute name="field">
                        <xsl:value-of select="p:field"/>
                    </xsl:attribute>
                </entry>
            </xsl:for-each>
        </entries>
    </xsl:template>
</xsl:stylesheet>