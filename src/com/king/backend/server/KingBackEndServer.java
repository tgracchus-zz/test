package com.king.backend.server;

import com.king.backend.server.http.HttpResponseSender;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public final class KingBackEndServer {

    private final HttpServer server;

    public KingBackEndServer(HttpServer server) {
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

    public static class KingBackEndServerBuilder {

        private String address;
        private int port;
        private int connectionThreads;
        private int workingThreads;
        private long keepAliveTime;

        private List<HttpHandlerMapping> httpHandlers = new ArrayList<>();

        public KingBackEndServerBuilder httpHandler(String mapping, HttpHandler httpHandler) {
            this.httpHandlers.add(new HttpHandlerMapping(mapping, httpHandler));
            return this;
        }


        public KingBackEndServerBuilder keepAliveTime(long keepAliveTime) {
            this.keepAliveTime = keepAliveTime;
            return this;
        }

        public KingBackEndServerBuilder address(String address) {
            this.address = address;
            return this;
        }

        public KingBackEndServerBuilder port(int port) {
            this.port = port;
            return this;
        }

        public KingBackEndServerBuilder connectionThreads(int connectionThreads) {
            this.connectionThreads = connectionThreads;
            return this;
        }


        public KingBackEndServerBuilder workingThreads(int workingThreads) {
            this.workingThreads = workingThreads;
            return this;
        }

        public KingBackEndServer build() throws IllegalArgumentException {
            KingBackEndServer kingBackEndServer;

            try {
                if (address != null && !address.isEmpty() && port >= 8000
                        && connectionThreads > 0 && keepAliveTime > 1
                        && workingThreads > 0) {

                    HttpServer server = HttpServer.create(
                            new InetSocketAddress(InetAddress
                                    .getByName(address), port),
                            connectionThreads);


                    HttpResponseSender httpResponseSender = new HttpResponseSender();

                    for (HttpHandlerMapping mapping : httpHandlers) {
                        server.createContext(mapping.getMapping(), mapping.getHttpHandler());
                    }

                    kingBackEndServer = new KingBackEndServer(server);

                } else {
                    throw new IllegalArgumentException();

                }
            } catch (UnknownHostException e) {
                throw new IllegalArgumentException(e);
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }

            release();
            return kingBackEndServer;

        }

        private void release() {
            address = null;
            port = -1;
            connectionThreads = -1;
            workingThreads = -1;
        }

    }

}
