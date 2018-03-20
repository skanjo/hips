package io.xorshift.hips;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Samer Kanjo
 * @since 0.6.0 3/20/18 4:00 PM
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
  VersionTest.class,
  ConfigTest.class,
  HipsHttpTest.class
})
public class HttpTestSuite {

}
