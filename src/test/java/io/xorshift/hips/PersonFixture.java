package io.xorshift.hips;

/**
 * @author Samer Kanjo
 * @since 0.4.0 3/19/18 2:19 PM
 */
public class PersonFixture {

  public static final String CHIP_FOOSE_COMMA = "Chip,Foose,M,MetallicOrange,2004-10-12";
  public static final String CHIP_FOOSE_PIPE = "Chip|Foose|M|MetallicOrange|2004-10-12";
  public static final String CHIP_FOOSE_SPACE = "Chip Foose M MetallicOrange 2004-10-12";

  public static final String CHRIS_JACOBS_COMMA = "Chris,Jacobs,M,Black,2008-06-26";
  public static final String COURTNEY_HANSEN_COMMA = "Courtney,Hansen,F,Blue,2005-03-17";

  public static final Person CHIP_FOOSE = Person.createFromCsv(CHIP_FOOSE_COMMA);
  public static final Person CHRIS_JACOBS = Person.createFromCsv(CHRIS_JACOBS_COMMA);
  public static final Person COURTNEY_HANSEN = Person.createFromCsv(COURTNEY_HANSEN_COMMA);

}
