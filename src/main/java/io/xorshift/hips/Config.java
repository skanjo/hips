package io.xorshift.hips;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.internal.Console;

import java.util.Collections;
import java.util.List;

/**
 * @author Samer Kanjo
 * @since 0.4.0 3/20/18 10:36 AM
 */
class Config {

  private static final String LINE_SEPARATOR = System.getProperty("line.separator");

  @Parameter(names = "-files", description = "Comma separated list of file names to process. Supported delimiters include command, pipe, and space", required = true)
  private List<String> filePaths = Collections.emptyList();

  @Parameter(names = "-help", description = "Display usage", help = true)
  private boolean help = false;

  @Parameter(names = "-version", description = "Display version number", help = true)
  private boolean version = false;

  private final JCommander jcmd;
  private final Console console;

  Config() {
    this(JCommander.getConsole());
  }

  Config(Console console) {
    this.console = console;
    jcmd = JCommander.newBuilder()
      .programName("HipsCli")
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

  List<String> filePaths() {
    return filePaths;
  }

  void showVersion() {
    console.println(Version.buildString());
    console.println("");
  }
}
