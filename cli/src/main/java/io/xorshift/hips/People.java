package io.xorshift.hips;

import java.util.List;

/**
 * @author Samer Kanjo
 * @since 0.2.0 3/16/18 10:33 PM
 */
interface People {

  void add(Person p);

  List<Person> all();

  List<Person> sortedByGender();

  List<Person> sortedByBirthDate();

  List<Person> sortedByLastName();

}
