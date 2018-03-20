package io.xorshift.hips;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Samer Kanjo
 * @since 0.1.0 3/14/18 11:41 PM
 */
class MergeAndSortPeople {

  private final List<Reader> readers;
  private final PrintWriter writer;
  private final People people;

  static MergeAndSortPeople create(List<String> filePaths) throws FileNotFoundException {
    return create(filePaths, new InMemoryPeople());
  }

  static MergeAndSortPeople create(List<String> filePaths, People people) throws FileNotFoundException {
    if (filePaths == null || filePaths.size() == 0) {
      throw new IllegalArgumentException("Files parameter must contain one or more elements.");
    }

    final List<Reader> readers = new ArrayList<>();
    for (String f : filePaths) {
      readers.add(new FileReader(f));
    }

    return new MergeAndSortPeople(readers, new PrintWriter(System.out), people);
  }

  MergeAndSortPeople(List<Reader> readers, PrintWriter writer, People people) {
    this.readers = readers;
    this.writer = writer;
    this.people = people;
  }

  void execute() throws IOException {
    for (Reader r : readers) {
      readInputFile(r);
    }

    writeOutputFiles();
  }

  private void readInputFile(Reader r) throws IOException {
    final BufferedReader br = new BufferedReader(r);

    String line;
    while ((line = br.readLine()) != null) {
      people.add(Person.createFromCsv(line));
    }

    br.close();
  }

  private void writeOutputFiles() {
    writer.println("OUTPUT 1 - SORTED BY GENDER AND THEN BY LAST NAME ASCENDING");
    for (Person p : people.sortedByGender()) {
      writer.println(p.toCsv());
    }
    writer.println();

    writer.println("OUTPUT 2 - SORTED BY BIRTH DATE ASCENDING");
    for (Person p : people.sortedByBirthDate()) {
      writer.println(p.toCsv());
    }
    writer.println();

    writer.println("OUTPUT 3 - SORTED BY LAST NAME DESCENDING");
    for (Person p : people.sortedByLastName()) {
      writer.println(p.toCsv());
    }
  }

}
