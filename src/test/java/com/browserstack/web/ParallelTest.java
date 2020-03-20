package com.browserstack.web;


import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


@Execution(ExecutionMode.CONCURRENT)
public class ParallelTest extends BrowserStackJUnitTest{

    //count will decide the number of environments your tests should run on.
    //For instance, there are 4 environments but the count is 2 it will run the tests on the first 2 environments. If count is 4 it will run it on all 4
    final int count = 4;
    @RepeatedTest(count)
    public void repeatedTestMethod1(RepetitionInfo repetitionInfo) throws InterruptedException{
        driver.get("https://www.google.com/ncr");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("BrowserStack");
        element.submit();
        Thread.sleep(5000);


    }
    @RepeatedTest(count)
    public void repeatedTestMethod2(RepetitionInfo repetitionInfo) throws InterruptedException {
        driver.get("https://www.google.com/ncr");
        WebElement element = driver.findElement(By.name("q"));
        element.sendKeys("BrowserStack");
        element.submit();
        Thread.sleep(5000);
    }
}