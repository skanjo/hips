package io.xorshift.hips;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.internal.Console;

/**
 * @author Samer Kanjo
 * @since 0.6.0 3/20/18 3:43 PM
 */
public class Config {

  private static final String LINE_SEPARATOR = System.getProperty("line.separator");
  private static final int DEFAULT_PORT = 8080;

  @Parameter(names = "-port", description = "Port number", order = 1)
  private int port = DEFAULT_PORT;

  @Parameter(names = "-version", description = "Display version number", help = true, order = 2)
  private boolean version = false;

  @Parameter(names = "-help", description = "Display usage", help = true, order = 3)
  private boolean help = false;

  private final JCommander jcmd;
  private final Console console;

  Config() {
    this(JCommander.getConsole());
  }

  Config(Console console) {
    this.console = console;
    jcmd = JCommander.newBuilder()
      .programName("HipsHttp")
      .addObject(this)
      .build();
  }

  void parse(String[] args) {
    jcmd.parse(args);
  }

  void showUsage() {
    final StringBuilder out = new StringBuilder();
    jcmd.usage(out);
    console.println(out.toString());
  }

  void showUsage(Exception e) {
    final StringBuilder out = new StringBuilder();
    out.append("Error: ").append(e.getMessage()).append(LINE_SEPARATOR);
    out.append(LINE_SEPARATOR);
    jcmd.usage(out);
    console.println(out.toString());
  }

  boolean requestedHelp() {
    return help;
  }

  boolean requestedVersion() {
    return version;
  }

  int port() {
    return port;
  }

  void showVersion() {
    console.println(Version.buildString());
    console.println("");
  }

}
