package io.xorshift.hips;

import com.google.common.base.Splitter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Samer Kanjo
 * @since 0.2.0 3/16/18 10:35 PM
 */
class Person {

  private static final char COMMA_DELIMITER = ',';
  private static final Pattern DELIMITER_PATTERN = Pattern.compile("[,| ]");
  private static final Splitter FIELD_SPLITTER = Splitter.on(DELIMITER_PATTERN);
  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("M/dd/yyyy");
  private static final SimpleDateFormat DATE_PARSE = new SimpleDateFormat("yyyy-MM-dd");

  static Person createFromCsv(String record) {
    final List<String> fields = FIELD_SPLITTER.splitToList(record);

    final Person p = new Person();
    p.firstName = fields.get(0);
    p.lastName = fields.get(1);
    p.gender = fields.get(2);
    p.favoriteColor = fields.get(3);
    try {
      p.dateOfBirth = DATE_PARSE.parse(fields.get(4));
    } catch (ParseException e) {
      throw new IllegalArgumentException(e);
    }

    return p;
  }

  private String firstName;
  private String lastName;
  private String gender;
  private String favoriteColor;
  private Date dateOfBirth;

  String firstName() {
    return firstName;
  }

  String lastName() {
    return lastName;
  }

  String gender() {
    return gender;
  }

  String favoriteColor() {
    return favoriteColor;
  }

  Date dateOfBirth() {
    return dateOfBirth;
  }

  String toCsv() {
    return new StringBuffer()
      .append(firstName()).append(COMMA_DELIMITER)
      .append(lastName()).append(COMMA_DELIMITER)
      .append(gender()).append(COMMA_DELIMITER)
      .append(favoriteColor()).append(COMMA_DELIMITER)
      .append(DATE_FORMAT.format(dateOfBirth()))
      .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Person person = (Person) o;

    if (!firstName.equals(person.firstName)) return false;
    if (!lastName.equals(person.lastName)) return false;
    if (!gender.equals(person.gender)) return false;
    if (!favoriteColor.equals(person.favoriteColor)) return false;
    return dateOfBirth.equals(person.dateOfBirth);
  }

  @Override
  public int hashCode() {
    int result = firstName.hashCode();
    result = 31 * result + lastName.hashCode();
    result = 31 * result + gender.hashCode();
    result = 31 * result + favoriteColor.hashCode();
    result = 31 * result + dateOfBirth.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Person{" +
      "firstName='" + firstName + '\'' +
      ", lastName='" + lastName + '\'' +
      ", gender='" + gender + '\'' +
      ", favoriteColor='" + favoriteColor + '\'' +
      ", dateOfBirth=" + dateOfBirth +
      '}';
  }

}
