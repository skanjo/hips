package io.xorshift.hips;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Samer Kanjo
 * @since 0.7.0 3/21/18 12:19 AM
 */
public class HipsHttpIT {

  private CloseableHttpClient client;
  private Gson gson;

  @BeforeClass
  public static void setUpOnce() throws Exception {
    final List<String> records = Arrays.asList(
      PersonFixture.CHIP_FOOSE_COMMA,
      PersonFixture.CHRIS_JACOBS_COMMA,
      PersonFixture.COURTNEY_HANSEN_COMMA
    );

    final CloseableHttpClient client = HttpClientBuilder.create().build();
    for (String r : records) {
      final HttpPost post = new HttpPost("http://localhost:8080/records");
      post.setEntity(new StringEntity(r));
      final CloseableHttpResponse httpResp = client.execute(post);
      assertThat(httpResp.getStatusLine().getStatusCode()).isEqualTo(204);
    }
    client.close();
  }

  @Before
  public void setUp() {
    client = HttpClientBuilder.create().build();
    gson = new GsonBuilder().setPrettyPrinting().setDateFormat("M/d/yyyy").create();
  }

  @After
  public void tearDown() throws Exception {
    client.close();
  }

  @Test
  public void peopleSortedByGender() throws IOException {
    // Setup

    // Exercise
    final CloseableHttpResponse httpResp = client.execute(new HttpGet("http://localhost:8080/records/gender"));
    final String actual = EntityUtils.toString(httpResp.getEntity());
    final String expected = gson.toJson(PersonFixture.createListSortedByGender());

    // Verify
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void peopleSortedByBirthDate() throws IOException {
    // Setup

    // Exercise
    final CloseableHttpResponse httpResp = client.execute(new HttpGet("http://localhost:8080/records/birthdate"));
    final String actual = EntityUtils.toString(httpResp.getEntity());
    final String expected = gson.toJson(PersonFixture.createListSortedByBirthDate());

    // Verify
    assertThat(actual).isEqualTo(expected);
  }

  @Test
  public void peopleSortedByLastName() throws IOException {
    // Setup

    // Exercise
    final CloseableHttpResponse httpResp = client.execute(new HttpGet("http://localhost:8080/records/name"));
    final String actual = EntityUtils.toString(httpResp.getEntity());
    final String expected = gson.toJson(PersonFixture.createListSortedByLastName());

    // Verify
    assertThat(actual).isEqualTo(expected);
  }

}
