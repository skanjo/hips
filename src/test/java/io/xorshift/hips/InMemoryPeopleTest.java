package io.xorshift.hips;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Samer Kanjo
 * @since 0.4.0 3/19/18 11:10 AM
 */
public class InMemoryPeopleTest {

  private InMemoryPeople people;

  @Before
  public void setUp() throws Exception {
    people = new InMemoryPeople();
  }

  @Test
  public void add() {
    // Setup
    final Person p = PersonFixture.CHIP_FOOSE;

    // Exercise
    people.add(p);

    // Verify
    final List<Person> actual = people.all();
    assertThat(actual.size()).isEqualTo(1);
    assertThat(actual.get(0)).isEqualTo(p);
  }

  @Test
  public void sortedByGender() {
    // Setup
    people.add(PersonFixture.CHRIS_JACOBS);
    people.add(PersonFixture.COURTNEY_HANSEN);
    people.add(PersonFixture.CHIP_FOOSE);

    // Exercise
    final List<Person> actual = people.sortedByGender();

    // Verify
    assertThat(actual).isNotNull();
    assertThat(actual.size()).isEqualTo(3);
    assertThat(actual.get(0)).isEqualTo(PersonFixture.COURTNEY_HANSEN);
    assertThat(actual.get(1)).isEqualTo(PersonFixture.CHIP_FOOSE);
    assertThat(actual.get(2)).isEqualTo(PersonFixture.CHRIS_JACOBS);
  }

  @Test
  public void sortedByBirthDate() {
    // Setup
    people.add(PersonFixture.CHRIS_JACOBS);
    people.add(PersonFixture.CHIP_FOOSE);
    people.add(PersonFixture.COURTNEY_HANSEN);

    // Exercise
    final List<Person> actual = people.sortedByBirthDate();

    // Verify
    assertThat(actual).isNotNull();
    assertThat(actual.size()).isEqualTo(3);
    assertThat(actual.get(0)).isEqualTo(PersonFixture.CHIP_FOOSE);
    assertThat(actual.get(1)).isEqualTo(PersonFixture.COURTNEY_HANSEN);
    assertThat(actual.get(2)).isEqualTo(PersonFixture.CHRIS_JACOBS);
  }

  @Test
  public void sortedByLastName() {
    // Setup
    people.add(PersonFixture.CHIP_FOOSE);
    people.add(PersonFixture.CHRIS_JACOBS);
    people.add(PersonFixture.COURTNEY_HANSEN);

    // Exercise
    final List<Person> actual = people.sortedByLastName();

    // Verify
    assertThat(actual).isNotNull();
    assertThat(actual.size()).isEqualTo(3);
    assertThat(actual.get(0)).isEqualTo(PersonFixture.CHRIS_JACOBS);
    assertThat(actual.get(1)).isEqualTo(PersonFixture.COURTNEY_HANSEN);
    assertThat(actual.get(2)).isEqualTo(PersonFixture.CHIP_FOOSE);
  }
}

