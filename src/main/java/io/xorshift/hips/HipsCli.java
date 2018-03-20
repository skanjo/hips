package io.xorshift.hips;

import com.beust.jcommander.ParameterException;

import java.io.IOException;

/**
 * @author Samer Kanjo
 * @since 0.1.0 3/13/18 9:59 PM
 */
public class HipsCli {

  private final Config config;

  HipsCli(Config config) {
    this.config = config;
  }

  private void run() throws IOException {
    if (config.requestedHelp()) {
      config.showUsage();

    } else if (config.requestedVersion()) {
      config.showVersion();

    } else {
      MergeAndSortPeople.create(config.filePaths()).execute();
    }
  }

  public static void main(String[] args) {
    final Config config = new Config();
    try {
      config.parse(args);

      new HipsCli(config).run();

    } catch (ParameterException | IOException e) {
      config.showUsage(e);
    }
  }

}
