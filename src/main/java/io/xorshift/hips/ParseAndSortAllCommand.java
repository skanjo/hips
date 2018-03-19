package io.xorshift.hips;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Samer Kanjo
 * @since 0.1.0 3/14/18 11:41 PM
 */
class ParseAndSortAllCommand {

  private final List<File> files;
  private final People people;

  static ParseAndSortAllCommand create(List<File> files) {
    if (files == null || files.size() == 0) {
      throw new IllegalArgumentException("Files parameter must contain one or more elements.");
    }
    return new ParseAndSortAllCommand(new ArrayList<>(files));
  }

  private ParseAndSortAllCommand(List<File> files) {
    this.files = files;
    this.people = new InMemoryPeople();
  }

  void execute() {
    try {
      for (File f : files) {
        readInputFile(f);
      }

      writeOutputFiles();

    } catch (IOException e) {
      throw new RuntimeException("error running command", e);
    }
  }

  private void readInputFile(File f) throws IOException {
    final BufferedReader br = new BufferedReader(new FileReader(f));

    String line;
    while ((line = br.readLine()) != null) {
      people.add(Person.createFromCsv(line));
    }

    br.close();
  }

  private void writeOutputFiles() {
    System.out.println("OUTPUT 1 - SORTED BY GENDER AND THEN BY LAST NAME ASCENDING");
    for (Person p : people.sortedByGender()) {
      System.out.println(p.toCsv());
    }
    System.out.println();

    System.out.println("OUTPUT 2 - SORTED BY BIRTH DATE ASCENDING");
    for (Person p : people.sortedByBirthDate()) {
      System.out.println(p.toCsv());
    }
    System.out.println();

    System.out.println("OUTPUT 3 - SORTED BY LAST NAME DESCENDING");
    for (Person p : people.sortedByLastName()) {
      System.out.println(p.toCsv());
    }
  }

}
