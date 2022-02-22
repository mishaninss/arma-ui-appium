package com.github.mishaninss.arma.uidriver.android.app;

import com.github.mishaninss.arma.data.WebDriverProperties;
import com.github.mishaninss.arma.reporting.IReporter;
import com.github.mishaninss.arma.reporting.Reporter;
import com.github.mishaninss.arma.uidriver.interfaces.IFrame;
import com.github.mishaninss.arma.uidriver.interfaces.ILocatable;
import com.github.mishaninss.arma.uidriver.webdriver.IWebDriverFactory;
import com.github.mishaninss.arma.uidriver.webdriver.LocatorConverter;
import com.github.mishaninss.arma.uidriver.webdriver.WebElementProvider;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class AndroidAppWebElementProvider extends WebElementProvider {

  @Autowired
  private IWebDriverFactory webDriverFactory;
  @Autowired
  protected WebDriverProperties properties;
  @Reporter
  private IReporter reporter;
  @Autowired
  private LocatorConverter locatorConverter;

  /**
   * WebElements cache
   */
  private final Map<ILocatable, WebElement> elements = new HashMap<>();

  public void clearCache() {
    elements.clear();
  }

  @Override
  public WebElement findElement(ILocatable element, long timeoutInMilliseconds) {
    webDriverFactory.setWaitingTimeout((int) timeoutInMilliseconds);
    try {
      return findElement(element);
    } finally {
      webDriverFactory.restoreWaitingTimeout();
    }
  }

  @Override
  public @NonNull
  WebElement findElement(ILocatable element) {
    if (!element.useContextLookup()) {
      WebElement webElement = cacheLookup(element);
      if (webElement != null) {
        return webElement;
      } else {
        webElement = checkIndexAndFindElement(null, element.getLocator());
        elements.put(element, webElement);
        return webElement;
      }
    } else {
      Deque<ILocatable> elementsStack = element.getRealLocatableObjectDeque();
      reporter.trace("Locatable deque: %s", elementsStack);
      WebElement contextElement = null;
      WebElement webElement = null;
      while (!elementsStack.isEmpty()) {
        ILocatable nextElement = elementsStack.pop();
        if (!elementsStack.isEmpty() && nextElement instanceof IFrame) {
          reporter.trace("Switch to frame element %s", nextElement.getLocator());
          WebElement frameElement = cacheLookup(nextElement);
          if (frameElement == null) {
            frameElement = findElement(contextElement, nextElement.getLocator());
          }
          webDriverFactory.getDriver().switchTo().frame(frameElement);
          elements.put(nextElement, webElement);
          webElement = null;
        } else {
          webElement = cacheLookup(nextElement);
          if (webElement == null) {
            webElement = checkIndexAndFindElement(contextElement, nextElement.getLocator());
            elements.put(nextElement, webElement);
          }
        }
        contextElement = webElement;
      }
      if (webElement == null) {
        throw new NoSuchElementException(
            "Cannot find element " + StringUtils.join(element.getLocatorDeque(), " -> "));
      }
      return webElement;
    }
  }

  private WebElement checkIndexAndFindElement(WebElement context, String locator) {
    Object[] indexCheck = locatorConverter.checkForIndex(locator);
    return ArrayUtils.isEmpty(indexCheck) ?
        findElement(context, locator) :
        findElement(context, indexCheck[1].toString(), (Integer) indexCheck[0]);
  }

  private @Nullable
  WebElement cacheLookup(ILocatable element) {
    return elements.get(element);
  }

  private @NonNull
  WebElement findElement(@Nullable WebElement context, @NonNull String locator) {
    AndroidDriver<AndroidElement> driver = (AndroidDriver<AndroidElement>) webDriverFactory.getDriver();
    if (context == null) {
      reporter.trace("find element %s [%d]", locator, properties.driver().timeoutsElement);
      return driver.findElement(locatorConverter.toBy(locator));
    } else {
      reporter.trace("find element %s %s", context, locator);
      return context.findElement(locatorConverter.toBy(locator));
    }
  }

  private @NonNull
  WebElement findElement(@Nullable WebElement context, @NonNull String locator, int index) {
    AndroidDriver<MobileElement> driver = (AndroidDriver<MobileElement>) webDriverFactory.getDriver();
    List<MobileElement> webElements;
    if (context == null) {
      reporter.trace("find element %s [%d]", locator, index);
      webElements = driver.findElements(locatorConverter.toBy(locator));
    } else {
      reporter.trace("find element %s %s [%d]", context, locator, index);
      webElements = context.findElements(locatorConverter.toBy(locator));

    }
    if (webElements.size() < index) {
      throw new NoSuchElementException(
          "Cannot find element [" + locator + "] with index [" + index + "]");
    }
    return webElements.get(index - 1);
  }
}