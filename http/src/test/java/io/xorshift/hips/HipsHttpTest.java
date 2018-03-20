package io.xorshift.hips;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * @author Samer Kanjo
 * @since 0.6.0 3/20/18 4:11 PM
 */
public class HipsHttpTest {
  private Config config;
  private HipsHttp cli;

  @Before
  public void setUp() {
    config = mock(Config.class);
    cli = new HipsHttp(config);
  }

  @Test
  public void help() {
    // Setup
    when(config.requestedHelp()).thenReturn(true);

    // Exercise
    cli.run();

    // Verify
    verify(config).showUsage();
  }

  @Test
  public void version() {
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
  public void execute() {
    // Setup
    when(config.requestedHelp()).thenReturn(false);
    when(config.requestedVersion()).thenReturn(false);
    when(config.port()).thenReturn(8080);

    // Exercise
    cli.run();

    // Verify
    verify(config).port();
  }

}
