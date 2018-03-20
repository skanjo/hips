package io.xorshift.hips;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * @author Samer Kanjo
 * @since 0.4.0 3/19/18 1:07 PM
 */
public class VersionTest {

  private Version ver;

  @Before
  public void setUp() throws Exception {
    ver = spy(new Version());
    when(ver.name()).thenReturn("cli");
    when(ver.revision()).thenReturn("2018-03-19");
  }

  @Test
  public void buildStringSnapshot() {
    // Setup
    when(ver.version()).thenReturn("1.0.0-SNAPSHOT");

    // Exercise
    final String v = ver.toString();

    // Verify
    assertThat(v).isEqualTo("cli 1.0.0-SNAPSHOT 2018-03-19");
  }

  @Test
  public void buildStringRelease() {
    // Setup
    when(ver.version()).thenReturn("1.0.0");

    // Exercise
    final String v = ver.toString();

    // Verify
    assertThat(v).isEqualTo("cli 1.0.0");
  }

}
