package io.xorshift.hips;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.internal.Console;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * @author Samer Kanjo
 * @since 0.1.0 3/13/18 9:59 PM
 */
public class HipsCli {

  @Parameter(names = "-files", description = "Comma separated list of file names to process. Supported delimiters include command, pipe, and space", required = true)
  private List<String> filePaths = Collections.emptyList();

  @Parameter(names = "-help", description = "Display usage", help = true)
  private boolean help = false;

  @Parameter(names = "-version", description = "Display version number", help = true)
  private boolean version = false;

  public static void main(String[] args) {
    final HipsCli cli = new HipsCli();
    final JCommander cmd = JCommander.newBuilder()
      .programName("HipsCli")
      .addObject(cli)
      .build();

    try {
      cmd.parse(args);

      if (cli.help) {
        cmd.usage();

      } else if (cli.version) {
        final Console console = JCommander.getConsole();
        console.println(Version.buildString());
        console.println("");

      } else {
        MergeAndSortPeople.create(cli.filePaths).execute();
      }


    } catch (ParameterException | IOException e) {
      final Console console = JCommander.getConsole();
      console.println("Error: " + e.getMessage());
      console.println("");
      cmd.usage();
    }
  }

}
