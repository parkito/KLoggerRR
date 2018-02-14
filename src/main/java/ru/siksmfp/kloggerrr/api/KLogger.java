package ru.siksmfp.kloggerrr.api;

import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Artem Karnov @date 2/14/2018.
 * @email artem.karnov@t-systems.com
 */
public class KLogger {
    public KLogger() {
    }

    public static class KLoggerBuilder {
        private List<RequestFilter> requestFilters;
        private List<ResponseFilter> responseFilters;
        private List<QueryFilter> queryFilters;
        private List<HeaderFilter> headerFilters;
        private List<BodyFilter> bodyFilters;
        private List<LogWriter> writers;
        private List<RequestReceiver> receiver;
        private LogFormatter formatter;
        public boolean isWithoutServlets;

        public KLoggerBuilder() {
            requestFilters = new ArrayList<>();
            responseFilters = new ArrayList<>();
            queryFilters = new ArrayList<>();
            headerFilters = new ArrayList<>();
            bodyFilters = new ArrayList<>();
            writers = new ArrayList<>();
            receiver = new ArrayList<>();
        }

        public KLoggerBuilder addRequestFilter(RequestFilter requestFilter) {
            this.requestFilters.add(requestFilter);
            return this;
        }

        public KLoggerBuilder withResponseFilter(ResponseFilter responseFilter) {
            this.responseFilters.add(responseFilter);
            return this;
        }

        public KLoggerBuilder addQueryFilter(QueryFilter queryFilter) {
            this.queryFilters.add(queryFilter);
            return this;
        }

        public KLoggerBuilder addHeaderFilter(HeaderFilter headerFilter) {
            this.headerFilters.add(headerFilter);
            return this;
        }

        public KLoggerBuilder addBodyFilter(BodyFilter bodyFilter) {
            this.bodyFilters.add(bodyFilter);
            return this;
        }

        public KLoggerBuilder addWriter(LogWriter logWriter) {
            this.writers.add(logWriter);
            return this;
        }

        public KLoggerBuilder addReceiver(RequestReceiver receiverWriter) {
            this.receiver.add(receiverWriter);
            return this;
        }

        public KLoggerBuilder setLogFormatter(LogFormatter logFormatter) {
            this.formatter = logFormatter;
            return this;
        }

        public KLoggerBuilder withoutServletsLogging(boolean isWithoutServlets){
            this.isWithoutServlets = isWithoutServlets;
            return this;
        }

        public KLogger build() {
            return null;
        }

    }

    private KLoggerBuilder kLoggerBuilder = new KLoggerBuilder();

    public  RestTemplate makeRestTemplate() {
        return null;
    }

}
