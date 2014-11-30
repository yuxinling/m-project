package com.zcloud.logger.clean.common;/**
 * User: buhe
 * Date: 3/13/13
 * Time: 9:03 AM
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;

public class SunYaml {
    private final static Logger LOG = LoggerFactory.getLogger(SunYaml.class);

    public <T> T load(Class<T> type, InputStream inputStream) {

        Yaml yaml = new Yaml(new UnSafe());
        Iterable iterable = yaml.loadAll(inputStream);
        Iterator iterator = iterable.iterator();
        while (iterator.hasNext()) {
            Object object = null;
            try {
                object = iterator.next();
            } catch (Exception ignore) {
            }

            if (type.isInstance(object)) {
                return type.cast(object);
            }


        }
        return null;
    }

    static class UnSafe extends Constructor {
        protected Class<?> getClassForNode(Node node) {
            String name = node.getTag().getValue().replace("!", "");
            Class<?> cl;
            try {
                cl = getClassForName(name);
            } catch (ClassNotFoundException e) {
                return Map.class;
            }
            return cl;
        }
    }
}
