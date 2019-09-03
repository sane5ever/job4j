<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet
         version="1.0">
    <xsl:output method="xml" version="1.0" encoding="utf-8" indent="yes"/>


        <entries>
            <xsl:for-each select="entries/entry">
                <entry>
                    <xsl:attribute name="field">
                        <xsl:value-of select="field"/>
                    </xsl:attribute>
                </entry>
            </xsl:for-each>
        </entries>

</xsl:stylesheet>