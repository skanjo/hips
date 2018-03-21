package io.xorshift.hips;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Samer Kanjo
 * @since 0.6.0 3/20/18 2:36 PM
 */
public class HipsHttp {

  private final Config config;

  HipsHttp(Config config) {
    this.config = config;
  }

  void run() throws IOException {
    if (config.requestedHelp()) {
      config.showUsage();

    } else if (config.requestedVersion()) {
      config.showVersion();

    } else {
      launchServer();
    }
  }

  void launchServer() throws IOException {
    URL url = Resources.getResource("io/xorshift/hips/banner.txt");
    final String banner = Resources.toString(url, Charsets.UTF_8);

    final VertxOptions vopt = new VertxOptions();
    final int instanceSize = vopt.getEventLoopPoolSize() * 2;

    final People people = new InMemoryPeople();
    final List<HttpServer> instances = new ArrayList<>();
    for (int i = 0; i < instanceSize; i++) {
      instances.add(new HttpServer(config.port(), people));
    }

    final Vertx vtx = Vertx.vertx(vopt);
    final AtomicInteger deployments = new AtomicInteger(0);
    for (HttpServer hs : instances) {
      vtx.deployVerticle(hs, deploy -> {
        if (deploy.succeeded()) {
          if (deployments.incrementAndGet() == instanceSize) {
            System.out.println(banner);
            System.out.println("HipsHttp started on port " + config.port());
          }
        } else {
          System.out.println("Failed to deploy instance, shutting down...");
          vtx.close();
        }
      });
    }

    Runtime.getRuntime().addShutdownHook(new Thread(() -> vtx.close(event -> {
      if (event.succeeded()) {
        System.out.println("HipsHttp shutdown");
      } else {
        System.out.println("failed to shutdown HipsHttp gracefully");
      }
    })));
  }

  public static void main(String[] args) {
    final Config config = new Config();
    try {
      config.parse(args);

      new HipsHttp(config).run();

    } catch (Exception e) {
      config.showUsage(e);
    }
  }

}
