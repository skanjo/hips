package io.xorshift.hips;

import org.junit.Test;

import java.sql.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Samer Kanjo
 * @since 0.4.0 3/19/18 11:09 AM
 */
public class PersonTest {

  private static final String INPUT_BAD_BIRTH_DATE_RECORD = "Chip,Foose,M,MetallicOrange,2004/10/12";
  private static final String OUTPUT_CSV_RECORD = "Chip,Foose,M,MetallicOrange,10/12/2004";

  @Test
  public void createFromCsvUsingCommaDelimiter() {
    // Setup

    // Exercise
    final Person p = Person.createFromCsv(PersonFixture.CHIP_FOOSE_COMMA);

    // Verify
    assertChipFoose(p);
  }

  @Test
  public void createFromCsvUsingPipeDelimiter() {
    // Setup

    // Exercise
    final Person p = Person.createFromCsv(PersonFixture.CHIP_FOOSE_PIPE);

    // Verify
    assertChipFoose(p);
  }

  @Test
  public void createFromCsvUsingSpaceDelimiter() {
    // Setup

    // Exercise
    final Person p = Person.createFromCsv(PersonFixture.CHIP_FOOSE_SPACE);

    // Verify
    assertChipFoose(p);
  }

  @Test(expected = IllegalArgumentException.class)
  public void createWithInvalidBirthDate() {
    // Setup

    // Exercise
    final Person p = Person.createFromCsv(INPUT_BAD_BIRTH_DATE_RECORD);

    // Verify
    assertChipFoose(p);
  }

  @Test
  public void toCsv() {
    // Setup
    final Person p = Person.createFromCsv(PersonFixture.CHIP_FOOSE_PIPE);

    // Exercise
    final String csv = p.toCsv();

    // Verify
    assertThat(csv).isEqualTo(OUTPUT_CSV_RECORD);
  }

  @Test
  public void equalTo() {
    // Setup
    final Person p1 = Person.createFromCsv(PersonFixture.CHIP_FOOSE_SPACE);
    final Person p2 = Person.createFromCsv(PersonFixture.CHIP_FOOSE_SPACE);

    // Exercise
    final boolean equalsResult = p1.equals(p2);
    final boolean identityResult = p1 == p2;

    // Verify
    assertThat(identityResult).isFalse();
    assertThat(equalsResult).isTrue();
  }

  @Test
  public void sameHash() {
    // Setup
    final Person p1 = Person.createFromCsv(PersonFixture.CHIP_FOOSE_SPACE);
    final Person p2 = Person.createFromCsv(PersonFixture.CHIP_FOOSE_SPACE);

    // Exercise
    final long hashResult1 = p1.hashCode();
    final long hashResult2 = p2.hashCode();
    final boolean identityResult = p1 == p2;

    // Verify
    assertThat(identityResult).isFalse();
    assertThat(hashResult1).isEqualTo(hashResult2);
  }

  private void assertChipFoose(Person actual) {
    assertThat(actual.firstName()).isEqualTo("Chip");
    assertThat(actual.lastName()).isEqualTo("Foose");
    assertThat(actual.gender()).isEqualTo("M");
    assertThat(actual.favoriteColor()).isEqualTo("MetallicOrange");
    assertThat(actual.dateOfBirth()).isEqualTo(Date.valueOf("2004-10-12"));
  }

}
