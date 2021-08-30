package com.ly.rep;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.TextNode;

import java.util.Iterator;
import java.util.Map;


public class JacksonUtils {


    public static void jsonLeaf(JsonNode node) {
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> it = node.fields();
            while (it.hasNext()) {
                Map.Entry<String, JsonNode> entry = it.next();
                if (entry.getValue() instanceof TextNode
                        && entry.getValue().isValueNode()) {
                    TextNode t = (TextNode) entry.getValue();
                    entry.setValue(new TextNode(t.asText().replaceAll(">","&gt;").replaceAll("<","&lt;")));
                }

                jsonLeaf(entry.getValue());
            }
        }

        if (node.isArray()) {
            Iterator<JsonNode> it = node.iterator();
            while (it.hasNext()) {
                jsonLeaf(it.next());
            }
        }
    }
}