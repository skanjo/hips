package io.xorshift.hips;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.io.IOException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Samer Kanjo
 * @since 0.6.0 3/20/18 9:53 PM
 */
public class HttpServerTest {

  private HttpServerResponse httpResp;
  private RoutingContext event;
  private People people;
  private HttpServer server;
  private Gson gson;

  @Before
  public void setUp() throws IOException {
    httpResp = mock(HttpServerResponse.class);

    event = mock(RoutingContext.class);
    when(event.response()).thenReturn(httpResp);

    people = mock(People.class);

    server = new HttpServer(8080, people);

    gson = new GsonBuilder().setDateFormat("M/d/yyyy").setPrettyPrinting().create();
  }

  @Test
  public void addPerson() {
    // Setup
    when(event.getBodyAsString()).thenReturn(PersonFixture.CHIP_FOOSE_COMMA);

    // Exercise
    server.addPerson(event);

    // Verify
    final ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);
    verify(people).add(personCaptor.capture());
    assertThat(personCaptor.getValue().equals(PersonFixture.CHIP_FOOSE));

    verify(httpResp).setStatusCode(204);
    verify(httpResp).end();
  }

  @Test
  public void addPersonNoBody() {
    // Setup
    when(event.getBodyAsString()).thenReturn(null);

    // Exercise
    server.addPerson(event);

    // Verify
    verify(people, never()).add(any(Person.class));
    verify(httpResp).setStatusCode(400);
    verify(httpResp).end();
  }

  @Test
  public void peopleSortedByGender() {
    // Setup
    when(people.sortedByGender()).thenReturn(PersonFixture.createListSortedByGender());

    final String expected = gson.toJson(PersonFixture.createListSortedByGender());

    // Exercise
    server.peopleSortedByGender(event);

    // Verify
    verify(httpResp).putHeader(eq(HttpHeaders.CONTENT_TYPE), eq("application/json"));
    verify(httpResp).setStatusCode(200);
    verify(httpResp).end(expected);
  }

  @Test
  public void peopleSortedByBirthDate() {
    // Setup
    when(people.sortedByBirthDate()).thenReturn(PersonFixture.createListSortedByBirthDate());

    final String expected = gson.toJson(PersonFixture.createListSortedByBirthDate());

    // Exercise
    server.peopleSortedByBirthDate(event);

    // Verify
    verify(httpResp).putHeader(eq(HttpHeaders.CONTENT_TYPE), eq("application/json"));
    verify(httpResp).setStatusCode(200);
    verify(httpResp).end(expected);

  }

  @Test
  public void peopleSortedByLastName() {
    // Setup
    when(people.sortedByLastName()).thenReturn(PersonFixture.createListSortedByLastName());

    final String expected = gson.toJson(PersonFixture.createListSortedByLastName());

    // Exercise
    server.peopleSortedByLastName(event);

    // Verify
    verify(httpResp).putHeader(eq(HttpHeaders.CONTENT_TYPE), eq("application/json"));
    verify(httpResp).setStatusCode(200);
    verify(httpResp).end(expected);
  }

  @Test
  public void failure() throws IOException {
    // Setup
    final URL url = Resources.getResource("io/xorshift/hips/fail_whale.txt");
    final String expected = Resources.toString(url, Charsets.UTF_8);

    // Exercise
    server.failure(event);

    // Verify
    verify(httpResp).putHeader(eq(HttpHeaders.CONTENT_TYPE), eq("text/plain"));
    verify(httpResp).setStatusCode(500);
    verify(httpResp).end(expected);
  }

}
