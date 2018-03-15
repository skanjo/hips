package io.xorshift.hips;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.beust.jcommander.converters.FileConverter;
import com.beust.jcommander.internal.Console;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * @author Samer Kanjo
 * @since 0.1.0 3/13/18 9:59 PM
 */
public class HipsCli {

  @Parameter(names = "-files", description = "Comma separated list of file names to process. Header may be included or omitted. Supported delimiters include command, pipe, and space", required = true, converter = FileConverter.class)
  private List<File> files = Collections.emptyList();

  @Parameter(names = "-help", help = true)
  private boolean help = false;

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

      } else {
        ParseAndSortAllCommand.create(cli.files).execute();
      }


    } catch (ParameterException e) {
      final Console console = JCommander.getConsole();
      console.println("Error: " + e.getMessage());
      console.println("");
      cmd.usage();
    }
  }

}
