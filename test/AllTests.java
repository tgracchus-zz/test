package com.king.backend.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ PathMappingTest.class,LoginEndpointTest.class,CheckLoginEndpointTest.class,ScoreEndpointTest.class,HighScoreEndpointTest.class,AllCommandsTest.class})
public class AllTests {

}
