package io.xorshift.hips;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Samer Kanjo
 * @since 0.1.0 3/14/18 11:41 PM
 */
class ParseAndSortAllCommand {

  private final List<File> files;

  static ParseAndSortAllCommand create(List<File> files) {
    if (files == null || files.size() == 0) {
      throw new IllegalArgumentException("Files parameter must contain one or more elements.");
    }
    return new ParseAndSortAllCommand(new ArrayList<>(files));
  }

  private ParseAndSortAllCommand(List<File> files) {
    this.files = files;
  }

  void execute() {
    System.out.println("ParseAndSortAllCommand NOOP");
  }

}
