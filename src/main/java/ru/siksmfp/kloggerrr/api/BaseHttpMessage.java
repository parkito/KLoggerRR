package ru.siksmfp.kloggerrr.api;

import com.sun.management.VMOption;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static java.lang.String.CASE_INSENSITIVE_ORDER;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
public interface BaseHttpMessage {

    String getProtocolVersion();

    VMOption.Origin getOrigin();

    Map<String, List<String>> getHeaders();

    String getContentType();

    Charset getCharset();

    class HeadersBuilder {

        private Map<String, List<String>> headers;

        public HeadersBuilder() {
            // package private so we can trick code coverage
            headers = new TreeMap<>(CASE_INSENSITIVE_ORDER);
        }

        public HeadersBuilder put(final String key, final String value) {
            final List<String> values = headers.get(key);
            if (values != null) {
                values.add(value);
            } else {
                final ArrayList<String> list = new ArrayList<>();
                list.add(value);
                headers.put(key, list);
            }
            return this;
        }

        public HeadersBuilder put(final String key, final Iterable<String> values) {
            for (final String value : values) {
                put(key, value);
            }
            return this;
        }

        public Map<String, List<String>> build() {
            for (final Map.Entry<String, List<String>> e : headers.entrySet()) {
                e.setValue(Collections.unmodifiableList(e.getValue()));
            }
            headers = Collections.unmodifiableMap(headers);
            return headers;
        }
    }
}
