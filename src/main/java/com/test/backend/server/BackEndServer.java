package com.test.backend.server;

import com.test.backend.server.http.HttpResponseSender;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public final class BackEndServer {

    private final HttpServer server;

    public BackEndServer(HttpServer server) {
        this.server = server;
    }

    public void start() {
        this.server.start();
    }

    public void stop(int stopTimeout) {
        this.server.stop(stopTimeout);
    }


    private static class HttpHandlerMapping {
        private final HttpHandler httpHandler;
        private final String mapping;

        public HttpHandlerMapping(String mapping, HttpHandler httpHandler) {
            this.httpHandler = httpHandler;
            this.mapping = mapping;
        }

        public String getMapping() {
            return mapping;
        }

        public HttpHandler getHttpHandler() {
            return httpHandler;
        }
    }

    public static class BackEndServerBuilder {

        private String address;
        private int port;
        private int connectionThreads;
        private long keepAliveTime;
        private ThreadPoolExecutor threadPoolExecutor;

        private List<HttpHandlerMapping> httpHandlers = new ArrayList<>();

        public BackEndServerBuilder httpHandler(String mapping, HttpHandler httpHandler) {
            this.httpHandlers.add(new HttpHandlerMapping(mapping, httpHandler));
            return this;
        }


        public BackEndServerBuilder threadPoolExecutor(ThreadPoolExecutor threadPoolExecutor){
            this.threadPoolExecutor = threadPoolExecutor;
            return this;
        }

        public BackEndServerBuilder keepAliveTime(long keepAliveTime) {
            this.keepAliveTime = keepAliveTime;
            return this;
        }

        public BackEndServerBuilder address(String address) {
            this.address = address;
            return this;
        }

        public BackEndServerBuilder port(int port) {
            this.port = port;
            return this;
        }

        public BackEndServerBuilder connectionThreads(int connectionThreads) {
            this.connectionThreads = connectionThreads;
            return this;
        }


        public BackEndServer build() throws IllegalArgumentException {
            BackEndServer backEndServer;

            try {
                if (address != null && !address.isEmpty()
                        && port >= 8000
                        && !httpHandlers.isEmpty()
                        && connectionThreads > 0
                        && keepAliveTime > 1
                        && threadPoolExecutor!=null) {

                    HttpServer server = HttpServer.create(
                            new InetSocketAddress(InetAddress
                                    .getByName(address), port),
                            connectionThreads);


                    for (HttpHandlerMapping mapping : httpHandlers) {
                        server.createContext(mapping.getMapping(), mapping.getHttpHandler());
                    }

                    backEndServer = new BackEndServer(server);

                } else {
                    throw new IllegalArgumentException();

                }
            } catch (UnknownHostException e) {
                throw new IllegalArgumentException(e);
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }

            release();
            return backEndServer;

        }

        private void release() {
            address = null;
            port = -1;
            connectionThreads = -1;
        }

    }

}
