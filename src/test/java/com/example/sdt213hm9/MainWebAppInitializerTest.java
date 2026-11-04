package com.example.sdt213hm9;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;

import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class MainWebAppInitializerTest {

    @Test
    void whenDeployedToServletContainer_thenInitializerServesJsp(@TempDir Path baseDir) throws Exception {
        Tomcat tomcat = new Tomcat();
        tomcat.setBaseDir(baseDir.toString());
        tomcat.setPort(0);
        tomcat.getConnector();
        tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());
        tomcat.start();

        try (HttpClient client = HttpClient.newHttpClient()) {
            int port = tomcat.getConnector().getLocalPort();
            HttpRequest request = HttpRequest
                    .newBuilder(URI.create("http://localhost:" + port + "/sample"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertThat(response.statusCode()).isEqualTo(200);
            assertThat(response.body()).contains("This is the body of the sample view");
        } finally {
            tomcat.stop();
            tomcat.destroy();
        }
    }
}
