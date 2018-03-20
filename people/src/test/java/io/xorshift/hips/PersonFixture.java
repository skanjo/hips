package io.xorshift.hips;

import java.io.Reader;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

/**
 * @author Samer Kanjo
 * @since 0.4.0 3/19/18 2:19 PM
 */
public class PersonFixture {

  public static final String CHIP_FOOSE_COMMA = "Chip,Foose,M,MetallicOrange,2004-10-12";
  public static final String CHIP_FOOSE_PIPE = "Chip|Foose|M|MetallicOrange|2004-10-12";
  public static final String CHIP_FOOSE_SPACE = "Chip Foose M MetallicOrange 2004-10-12";

  public static final String CHRIS_JACOBS_COMMA = "Chris,Jacobs,M,Black,2008-06-26";
  public static final String CHRIS_JACOBS_PIPE = "Chris|Jacobs|M|Black|2008-06-26";
  public static final String CHRIS_JACOBS_SPACE = "Chris Jacobs M Black 2008-06-26";

  public static final String COURTNEY_HANSEN_COMMA = "Courtney,Hansen,F,Blue,2005-03-17";
  public static final String COURTNEY_HANSEN_PIPE = "Courtney|Hansen|F|Blue|2005-03-17";
  public static final String COURTNEY_HANSEN_SPACE = "Courtney Hansen F Blue 2005-03-17";

  public static final Person CHIP_FOOSE = Person.createFromCsv(CHIP_FOOSE_COMMA);
  public static final Person CHRIS_JACOBS = Person.createFromCsv(CHRIS_JACOBS_COMMA);
  public static final Person COURTNEY_HANSEN = Person.createFromCsv(COURTNEY_HANSEN_COMMA);

  public static List<Reader> createInputReaders() {
    return Arrays.asList(
      new StringReader(CHIP_FOOSE_COMMA),
      new StringReader(CHRIS_JACOBS_PIPE),
      new StringReader(COURTNEY_HANSEN_SPACE)
    );
  }

  public static List<Person> createListSortedByGender() {
    return Arrays.asList(
      COURTNEY_HANSEN,
      CHIP_FOOSE,
      CHRIS_JACOBS
    );
  }

  public static List<Person> createListSortedByBirthDate() {
    return Arrays.asList(
      CHIP_FOOSE,
      COURTNEY_HANSEN,
      CHRIS_JACOBS
    );
  }

  public static List<Person> createListSortedByLastName() {
    return Arrays.asList(
      CHRIS_JACOBS,
      COURTNEY_HANSEN,
      CHIP_FOOSE
    );
  }

}
