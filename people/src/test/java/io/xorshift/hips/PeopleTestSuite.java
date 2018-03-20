package io.xorshift.hips;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @author Samer Kanjo
 * @since 0.5.0 3/20/18 1:21 PM
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        PersonTest.class,
        InMemoryPeopleTest.class
})
public class PeopleTestSuite {

}
