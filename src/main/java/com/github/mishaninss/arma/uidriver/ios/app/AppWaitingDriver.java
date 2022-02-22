package com.github.mishaninss.arma.uidriver.ios.app;

import com.github.mishaninss.arma.data.WebDriverProperties;
import com.github.mishaninss.arma.reporting.IReporter;
import com.github.mishaninss.arma.reporting.Reporter;
import com.github.mishaninss.arma.uidriver.Arma;
import com.github.mishaninss.arma.uidriver.annotations.ElementDriver;
import com.github.mishaninss.arma.uidriver.interfaces.IElementDriver;
import com.github.mishaninss.arma.uidriver.interfaces.ILocatable;
import com.github.mishaninss.arma.uidriver.interfaces.IWaitingDriver;
import com.github.mishaninss.arma.uidriver.webdriver.IWebDriverFactory;
import com.github.mishaninss.arma.uidriver.webdriver.WdWaitingDriver;
import com.github.mishaninss.arma.uidriver.webdriver.WebElementProvider;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AppWaitingDriver implements IWaitingDriver {

  @Autowired
  private Arma arma;
  @Autowired
  protected IWebDriverFactory webDriverFactory;
  @Autowired
  private WebElementProvider webElementProvider;
  @ElementDriver
  private IElementDriver elementDriver;
  @Autowired
  private WebDriverProperties properties;
  @Reporter
  private IReporter reporter;

  /**
   * Use this method to specify Java Script to check if page is updated
   *
   * @param script - Java Script must return true, if page is updated or false otherwise
   * @see WdWaitingDriver#JQUERY_COMPLETE
   * @see WdWaitingDriver#ANGULAR_HTTP_COMPLETE
   * @see WdWaitingDriver#DOC_READY_STATE_COMPLETE
   */
  @Override
  public void setWaitForPageUpdateScript(String script) {
    reporter.trace("setWaitForPageUpdateScript NOOP");
  }

  @Override
  public void setWaitForPageUpdateMethod(BiConsumer<Long, TemporalUnit> method) {
    reporter.trace("setWaitForPageUpdateMethod NOOP");
  }

  @Override
  public void waitForElementIsVisible(ILocatable element) {
    waitForElementIsVisible(element, properties.driver().timeoutsElement, ChronoUnit.MILLIS);
  }

  @Override
  public void waitForElementIsVisible(ILocatable element, long timeoutInSeconds) {
    waitForElementIsVisible(element, timeoutInSeconds, ChronoUnit.SECONDS);
  }

  @Override
  public void waitForElementIsVisible(ILocatable element, long timeout, TemporalUnit unit) {
    WebElement webElement = webElementProvider.findElement(element,
        Duration.of(timeout, unit).toMillis());
    performWait(ExpectedConditions.visibilityOf(webElement), timeout, unit);
  }

  @Override
  public void waitForElementIsNotVisible(ILocatable element) {
    waitForElementIsNotVisible(element, properties.driver().timeoutsElement, ChronoUnit.MILLIS);
  }

  @Override
  public void waitForElementIsNotVisible(ILocatable element, long timeoutInSeconds) {
    waitForElementIsNotVisible(element, timeoutInSeconds, ChronoUnit.SECONDS);
  }

  @Override
  public void waitForElementIsNotVisible(ILocatable element, long timeout, TemporalUnit unit) {
    if (!elementDriver.isElementDisplayed(element, false)) {
      return;
    }
    WebElement webElement = webElementProvider.findElement(element);
    performWait(ExpectedConditions.invisibilityOf(webElement), timeout, unit);
  }

  @Override
  public void waitForElementIsClickable(ILocatable element) {
    waitForElementIsClickable(element, properties.driver().timeoutsElement, ChronoUnit.MILLIS);
  }

  @Override
  public void waitForElementIsClickable(ILocatable element, long timeoutInSeconds) {
    waitForElementIsClickable(element, timeoutInSeconds, ChronoUnit.SECONDS);
  }

  @Override
  public void waitForElementIsClickable(ILocatable element, long timeout, TemporalUnit unit) {
    WebElement webElement = webElementProvider.findElement(element);
    performWait(ExpectedConditions.elementToBeClickable(webElement), timeout, unit);
  }

  @Override
  public void waitForElementToBeSelected(ILocatable element) {
    waitForElementToBeSelected(element, properties.driver().timeoutsElement, ChronoUnit.MILLIS);
  }

  @Override
  public void waitForElementToBeSelected(ILocatable element, long timeoutInSeconds) {
    waitForElementToBeSelected(element, timeoutInSeconds, ChronoUnit.SECONDS);
  }

  @Override
  public void waitForElementToBeSelected(ILocatable element, long timeout, TemporalUnit unit) {
    WebElement webElement = webElementProvider.findElement(element);
    performWait(ExpectedConditions.elementToBeSelected(webElement), timeout, unit);
  }

  @Override
  public void waitForElementToBeNotSelected(ILocatable element) {
    waitForElementToBeNotSelected(element, properties.driver().timeoutsElement, ChronoUnit.MILLIS);
  }

  @Override
  public void waitForElementToBeNotSelected(ILocatable element, long timeoutInSeconds) {
    waitForElementToBeNotSelected(element, timeoutInSeconds, ChronoUnit.SECONDS);
  }

  @Override
  public void waitForElementToBeNotSelected(ILocatable element, long timeout, TemporalUnit unit) {
    WebElement webElement = webElementProvider.findElement(element);
    performWait(ExpectedConditions.elementSelectionStateToBe(webElement, false), timeout, unit);
  }

  @Override
  public void waitForElementAttributeToBeNotEmpty(ILocatable element, String attribute) {
    WebElement webElement = webElementProvider.findElement(element);
    performWait(ExpectedConditions.attributeToBeNotEmpty(webElement, attribute),
        properties.driver().timeoutsElement, ChronoUnit.MILLIS);
  }

  @Override
  public void waitForElementAttributeToBeNotEmpty(ILocatable element, String attribute,
      long timeoutInSeconds) {
    WebElement webElement = webElementProvider.findElement(element);
    performWait(ExpectedConditions.attributeToBeNotEmpty(webElement, attribute), timeoutInSeconds,
        ChronoUnit.SECONDS);
  }

  @Override
  public void waitForElementAttributeToBeNotEmpty(ILocatable element, String attribute,
      long timeout, TemporalUnit unit) {
    WebElement webElement = webElementProvider.findElement(element);
    performWait(ExpectedConditions.attributeToBeNotEmpty(webElement, attribute), timeout, unit);
  }

  @Override
  public void waitForUrlToBe(String url) {
    waitForUrlToBe(url, properties.driver().timeoutsElement, ChronoUnit.MILLIS);
  }

  @Override
  public void waitForUrlToBe(String url, long timeoutInSeconds) {
    waitForUrlToBe(url, timeoutInSeconds, ChronoUnit.SECONDS);
  }

  @Override
  public void waitForUrlToBe(String url, long timeout, TemporalUnit unit) {
    performWait(ExpectedConditions.urlToBe(url), timeout, unit);
  }

  @Override
  public void waitForAlertIsPresent() {
    waitForAlertIsPresent(properties.driver().timeoutsElement, ChronoUnit.MILLIS);
  }

  @Override
  public void waitForAlertIsPresent(long timeoutInSeconds) {
    waitForAlertIsPresent(timeoutInSeconds, ChronoUnit.SECONDS);
  }

  @Override
  public void waitForAlertIsPresent(long timeout, TemporalUnit unit) {
    performWait(ExpectedConditions.alertIsPresent(), timeout, unit);
  }

  @Override
  public void waitForElementAttributeToBe(ILocatable element, String attribute, String value) {
    waitForElementAttributeToBe(element, attribute, value, properties.driver().timeoutsElement,
        ChronoUnit.MILLIS);
  }

  @Override
  public void waitForElementAttributeToBe(ILocatable element, String attribute, String value,
      long timeoutInSeconds) {
    waitForElementAttributeToBe(element, attribute, value, timeoutInSeconds, ChronoUnit.SECONDS);
  }

  @Override
  public void waitForElementAttributeToBe(ILocatable element, String attribute, String value,
      long timeout, TemporalUnit unit) {
    WebElement webElement = webElementProvider.findElement(element);
    performWait(ExpectedConditions.attributeToBe(webElement, attribute, value), timeout, unit);
  }

  @Override
  public void waitForElementAttributeContains(ILocatable element, String attribute, String value) {
    waitForElementAttributeContains(element, attribute, value, properties.driver().timeoutsElement,
        ChronoUnit.MILLIS);
  }

  @Override
  public void waitForElementAttributeContains(ILocatable element, String attribute, String value,
      long timeoutInSeconds) {
    waitForElementAttributeContains(element, attribute, value, timeoutInSeconds,
        ChronoUnit.SECONDS);
  }

  @Override
  public void waitForElementAttributeContains(ILocatable element, String attribute, String value,
      long timeout, TemporalUnit unit) {
    WebElement webElement = webElementProvider.findElement(element);
    performWait(ExpectedConditions.attributeContains(webElement, attribute, value), timeout, unit);
  }

  @Override
  public <T> T waitForCondition(Supplier<T> condition) {
    return waitForCondition(condition, null);
  }

  @Override
  public <T, R> R waitForCondition(Function<T, R> condition, T arg) {
    return waitForCondition(condition, arg, null);
  }

  @Override
  public <T> T waitForCondition(Supplier<T> condition, String message) {
    return waitForCondition(condition, properties.driver().timeoutsElement, ChronoUnit.MILLIS,
        message);
  }

  @Override
  public <T, R> R waitForCondition(Function<T, R> condition, T arg, String message) {
    return waitForCondition(condition, arg, properties.driver().timeoutsElement, ChronoUnit.MILLIS,
        message);
  }

  @Override
  public <T> T waitForCondition(Supplier<T> condition, long timeoutInSeconds) {
    return waitForCondition(condition, timeoutInSeconds, ChronoUnit.SECONDS, null);
  }

  @Override
  public <T, R> R waitForCondition(Function<T, R> condition, T arg, long timeoutInSeconds) {
    return waitForCondition(condition, arg, timeoutInSeconds, ChronoUnit.SECONDS, null);
  }

  @Override
  public <T> T waitForCondition(Supplier<T> condition, long timeoutInSeconds, String message) {
    return waitForCondition(condition, timeoutInSeconds, ChronoUnit.SECONDS, message);
  }

  @Override
  public <T, R> R waitForCondition(Function<T, R> condition, T arg, long timeoutInSeconds,
      String message) {
    return waitForCondition(condition, arg, timeoutInSeconds, ChronoUnit.SECONDS, message);
  }

  @Override
  public <T> T waitForCondition(Supplier<T> condition, long timeout, TemporalUnit unit) {
    return waitForCondition(condition, timeout, unit, null);
  }

  @Override
  public <T, R> R waitForCondition(Function<T, R> condition, T arg, long timeout,
      TemporalUnit unit) {
    return waitForCondition(condition, arg, timeout, unit, null);
  }

  @Override
  public <T> T waitForCondition(Supplier<T> condition, long timeout, TemporalUnit unit,
      String message) {
    ExpectedCondition<T> ec = (WebDriver webdriver) -> condition.get();
    return performWait((ec), timeout, unit, message);
  }

  @Override
  public <T, R> R waitForCondition(Function<T, R> condition, T arg, long timeout, TemporalUnit unit,
      String message) {
    ExpectedCondition<R> ec = (WebDriver webdriver) -> condition.apply(arg);
    return performWait((ec), timeout, unit, message);
  }

  @Override
  public void waitForPageUpdate() {
    waitForPageUpdate(properties.driver().timeoutsPageLoad, ChronoUnit.MILLIS);
  }

  @Override
  public void waitForPageUpdate(long timeoutInSeconds) {
    waitForPageUpdate(timeoutInSeconds, ChronoUnit.SECONDS);
  }

  @Override
  public void waitForPageUpdate(long timeout, TemporalUnit unit) {
    reporter.trace("waitForPageUpdate NOOP");
  }

  @Override
  public <T> T executeWithoutWaiting(Supplier<T> supplier) {
    webDriverFactory.setWaitingTimeout(0);
    try {
      return supplier.get();
    } finally {
      webDriverFactory.restoreWaitingTimeout();
    }
  }

  @Override
  public void executeWithoutWaiting(Runnable runnable) {
    webDriverFactory.setWaitingTimeout(0);
    try {
      runnable.run();
    } finally {
      webDriverFactory.restoreWaitingTimeout();
    }
  }

  protected <T> T performWait(ExpectedCondition<T> condition, long timeout, TemporalUnit unit) {
    return performWait(condition, timeout, unit, null);
  }

  protected <T> T performWait(ExpectedCondition<T> condition, long timeout, TemporalUnit unit,
      String message) {
    FluentWait<WebDriver> wait = new FluentWait<>(webDriverFactory.getDriver());
    Duration duration = Duration.of(timeout, unit);
    wait.withTimeout(duration);
    if (StringUtils.isNotBlank(message)) {
      wait.withMessage(message);
    }
    return wait.until(condition);
  }

  @Override
  public void waitForElementExists(ILocatable element) {
    waitForElementExists(element, properties.driver().timeoutsElement, ChronoUnit.MILLIS);
  }

  @Override
  public void waitForElementExists(ILocatable element, long timeoutInSeconds) {
    waitForElementExists(element, timeoutInSeconds, ChronoUnit.SECONDS);
  }

  @Override
  public void waitForElementExists(ILocatable element, long timeout, TemporalUnit unit) {
    webElementProvider.findElement(element, Duration.of(timeout, unit).toMillis());
  }
}
