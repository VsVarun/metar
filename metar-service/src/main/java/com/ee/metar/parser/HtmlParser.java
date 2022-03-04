/*
 * *
 *  * Project: ${PROJECT_NAME}
 *  * Package: ${PACKAGE_NAME}
 *  * Desc:
 *  * User: Varun Chandresekar
 *  * Date: ${DATE}
 *  * Time: ${TIME}
 *  * Copyright (c) 2022-2022.
 *
 *
 */

package com.ee.metar.parser;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Project: metar
 * Package: com.ee.metar
 * Desc: Parser to parse html document
 * User: Varun Chandresekar
 * Date: 03-03-2022
 * Time: 12:14
 */
@Component
public class HtmlParser {

    /***
     * Method to Parse table in jsoup document format
     * @param doc {@link Document}
     * @return List of Map of Headers
     */
    public List<Map<String, String>> parseTable(Document doc) {
        Element table = doc.select("table").get(0);
        Elements rows = table.select("tr");
        Elements first = rows.get(0).select("th,td");

        List<String> headers = new ArrayList<>();
        for (Element header : first)
            headers.add(header.text());

        List<Map<String, String>> listMap = new ArrayList<>();
        for (int row = 1; row < rows.size(); row++) {
            Elements colVals = rows.get(row).select("th,td");
            //check column size here

            int colCount = 0;
            Map<String, String> tuple = new HashMap<>();
            for (Element colVal : colVals)
                tuple.put(headers.get(colCount++), colVal.text());
            listMap.add(tuple);
        }
        return listMap;
    }
}
