package io.xorshift.hips;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Samer Kanjo
 * @since 0.2.0 3/16/18 10:38 PM
 */
class InMemoryPeople implements People {

  private final List<Person> data;

  InMemoryPeople() {
    data = new ArrayList<>();
  }

  @Override
  public void add(Person p) {
    data.add(p);
  }

  @Override
  public List<Person> sortedByGender() {
    return data.stream()
      .sorted(Comparator.comparing(Person::gender).thenComparing(Person::lastName))
      .collect(Collectors.toList());
  }

  @Override
  public List<Person> sortedByBirthDate() {
    return data.stream()
      .sorted(Comparator.comparing(Person::dateOfBirth))
      .collect(Collectors.toList());
  }

  @Override
  public List<Person> sortedByLastName() {
    return data.stream()
      .sorted(Comparator.comparing(Person::lastName).reversed())
      .collect(Collectors.toList());
  }

}
