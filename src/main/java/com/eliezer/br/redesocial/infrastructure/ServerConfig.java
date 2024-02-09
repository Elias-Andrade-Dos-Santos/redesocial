package com.eliezer.br.redesocial.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
@Configuration
public class ServerConfig  {
    @Value("${server.host}")
    private String serverHost;

    @Value("${server.port}")
    private String serverPort;

    public String getServerHost() {
        return serverHost;
    }

    public String getServerPort() {
        return serverPort;
    }
}
