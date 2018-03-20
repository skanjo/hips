package io.xorshift.hips;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;

import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Samer Kanjo
 * @since 0.4.0 3/20/18 9:54 AM
 */
public class HipsCliTest {

  private Config config;
  private HipsCli cli;

  @Before
  public void setUp() throws Exception {
    config = mock(Config.class);
    cli = new HipsCli(config);
  }

  @Test
  public void help() throws IOException {
    // Setup
    when(config.requestedHelp()).thenReturn(true);

    // Exercise
    cli.run();

    // Verify
    verify(config).showUsage();
  }

  @Test
  public void version() throws IOException {
    // Setup
    when(config.requestedVersion()).thenReturn(true);

    // Exercise
    cli.run();

    // Verify
    verify(config).showVersion();
  }

  /**
   * This is pretty sad. Need to inject mock to do this but decided to punt.
   */
  @Test
  public void execute() throws IOException {
    // Setup
    when(config.requestedHelp()).thenReturn(false);
    when(config.requestedVersion()).thenReturn(false);
    when(config.filePaths()).thenReturn(Collections.singletonList("file1"));

    // Exercise
    try {
      cli.run();
      fail("Expected FileNotFoundException due to mock file 'file1'");
    } catch (FileNotFoundException e) {
      // this exception is expected
    }

    // Verify
    verify(config).filePaths();
  }

}
