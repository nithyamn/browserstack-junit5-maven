package com.browserstack.web;

import org.junit.jupiter.api.RepeatedTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SingleTest extends BrowserStackJUnitTest {

  @RepeatedTest(1)
  public void test_google_BrowserStack() throws Exception {
    driver.get("https://www.google.com/ncr");
    WebElement element = driver.findElement(By.name("q"));
    element.sendKeys("BrowserStack");
    element.submit();
    Thread.sleep(5000);

  }
}
