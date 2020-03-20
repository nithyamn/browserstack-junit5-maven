package com.browserstack.web;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.RepeatedTest;

public class LocalTest extends BrowserStackJUnitTest {

  @RepeatedTest(1)
  public void test() throws Exception {
    driver.get("http://bs-local.com:45691/check");
    //assertTrue(driver.getPageSource().contains("Up and running"));
  }
}
