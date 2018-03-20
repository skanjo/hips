package io.xorshift.hips;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Samer Kanjo
 * @since 0.4.0 3/19/18 11:12 AM
 */
public class MergeAndSortPeopleTest {

  private ByteArrayOutputStream out;
  private People people;
  private MergeAndSortPeople cmd;

  @Before
  public void setUp() {
    out = new ByteArrayOutputStream();
    final PrintWriter writer = new UnixNewlinePrintWriter(out);

    people = mock(People.class);
    when(people.sortedByGender()).thenReturn(PersonFixture.createListSortedByGender());
    when(people.sortedByBirthDate()).thenReturn(PersonFixture.createListSortedByBirthDate());
    when(people.sortedByLastName()).thenReturn(PersonFixture.createListSortedByLastName());

    cmd = new MergeAndSortPeople(PersonFixture.createInputReaders(), writer, people);
  }

  @Test
  public void execute() throws IOException {
    // Setup
    URL url = Resources.getResource("io/xorshift/hips/merge_sort_output.txt");
    final String expected = Resources.toString(url, Charsets.UTF_8);

    // Exercise
    cmd.execute();

    // Verify
    verify(people, times(3)).add(any(Person.class));
    assertThat(new String(out.toByteArray(), StandardCharsets.UTF_8)).isEqualTo(expected);
  }

  /**
   * PrintWriter to help with verifying test results. Basically forces use of Unix style line separator.
   */
  static class UnixNewlinePrintWriter extends PrintWriter {

    public UnixNewlinePrintWriter(OutputStream out) {
      super(out, true);
    }

    /**
     * Force use of Unix style line separator "\n" rather than use of Windows line separator "\r\n". This was only done
     * to make it easier to compare the output of this stream with a test resource on different platforms without
     * breaking tests. There may be a better way but this is my way here.
     */
    @Override
    public void println() {
      try {
        synchronized (lock) {
          if (out == null) {
            throw new IOException("Stream closed");
          }

          out.write('\n');
          out.flush();
        }
      } catch (InterruptedIOException x) {
        Thread.currentThread().interrupt();
      } catch (IOException x) {
        setError();
      }
    }
  }
}
