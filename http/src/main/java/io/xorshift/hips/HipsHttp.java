package io.xorshift.hips;

import com.beust.jcommander.ParameterException;

/**
 * @author Samer Kanjo
 * @since 0.6.0 3/20/18 2:36 PM
 */
public class HipsHttp {

  private final Config config;

  HipsHttp(Config config) {
    this.config = config;
  }

  void run() {
    if (config.requestedHelp()) {
      config.showUsage();

    } else if (config.requestedVersion()) {
      config.showVersion();

    } else {
      System.out.println("Running on port " + config.port());
    }
  }

  public static void main(String[] args) {
    final Config config = new Config();
    try {
      config.parse(args);

      new HipsHttp(config).run();

    } catch (ParameterException e) {
      config.showUsage(e);
    }
  }
}
