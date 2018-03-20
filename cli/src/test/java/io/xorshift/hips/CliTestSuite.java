package io.xorshift.hips;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Samer Kanjo
 * @since 0.4.0 3/19/18 1:49 PM
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
  VersionTest.class,
  MergeAndSortPeopleTest.class,
  ConfigTest.class,
  HipsCliTest.class
})
public class CliTestSuite {

}
