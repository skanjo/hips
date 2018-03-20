package io.xorshift.hips;

import com.beust.jcommander.internal.Console;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Samer Kanjo
 * @since 0.6.0 3/20/18 3:49 PM
 */
public class ConfigTest {

  private Console console;
  private Config config;

  @Before
  public void setUp() {
    console = new RedirectedConsole();
    config = new Config(console);
  }

  @Test
  public void requestedHelp() {
    // Setup
    final String[] args = new String[]{"-help"};

    // Exercise
    config.parse(args);

    // Verify
    assertThat(config.requestedHelp()).isTrue();
  }

  @Test
  public void requestedVersion() {
    // Setup
    final String[] args = new String[]{"-version"};

    // Exercise
    config.parse(args);

    // Verify
    assertThat(config.requestedVersion()).isTrue();
  }

  @Test
  public void port() {
    // Setup
    final String[] args = new String[]{"-port", "2018"};

    // Exercise
    config.parse(args);

    // Verify
    assertThat(config.port()).isEqualTo(2018);
  }

  @Test
  public void defaultPort() {
    // Setup
    final String[] args = new String[0];

    // Exercise
    config.parse(args);

    // Verify
    assertThat(config.port()).isEqualTo(8080);
  }

  @Test
  public void showUsage() throws IOException {
    // Setup
    URL url = Resources.getResource("io/xorshift/hips/config_usage.txt");
    final String expected = Resources.toString(url, Charsets.UTF_8);

    // Exercise
    config.showUsage();

    // Verify
    assertThat(console.toString()).isEqualTo(expected);
  }

  @Test
  public void showUsageWithError() throws IOException {
    // Setup
    URL url = Resources.getResource("io/xorshift/hips/config_usage_error.txt");
    final String expected = Resources.toString(url, Charsets.UTF_8);
    final RuntimeException e = new RuntimeException("EXCEPTION MESSAGE");

    // Exercise
    config.showUsage(e);

    // Verify
    assertThat(console.toString()).isEqualTo(expected);
  }

  @Test
  public void showVersion() {
    // Setup

    // Exercise
    config.showVersion();

    // Verify
    assertThat(console.toString()).containsPattern("^HipsHttp \\d+.\\d+.\\d+");
  }

  static class RedirectedConsole implements Console {

    private static final String LINE_SEPARATOR = "\n";

    private final StringBuilder out;

    RedirectedConsole() {
      out = new StringBuilder();
    }

    @Override
    public String toString() {
      return out.toString();
    }

    @Override
    public void print(String msg) {
      out.append(msg);
    }

    @Override
    public void println(String msg) {
      out.append(msg).append(LINE_SEPARATOR);
    }

    @Override
    public char[] readPassword(boolean echoInput) {
      throw new UnsupportedOperationException();
    }

  }

}
