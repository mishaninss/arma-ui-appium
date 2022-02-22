package com.github.mishaninss.arma.uidriver.ios.app;

import com.github.mishaninss.arma.uidriver.interfaces.ILocatable;
import com.github.mishaninss.arma.uidriver.webdriver.WdElementDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.ElementOption;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import org.springframework.stereotype.Component;

@Component
public class AppElementDriver extends WdElementDriver {

  public String getElementId(ILocatable element) {
    IOSElement iosElement = (IOSElement) webElementProvider.findElement(element);
    return iosElement.getId();
  }

  public void swipeOnElement(ILocatable element, String direction) {
    HashMap<String, Object> args = new HashMap<>();
    args.put("direction", direction);
    args.put("element", getElementId(element));
    pageDriver.executeJS("mobile: scroll", args);
  }

  public void multipleTap(ILocatable element, int tapsCount) {
    new TouchAction<>((IOSDriver) webDriverFactory.getDriver())
        .tap(new TapOptions()
            .withElement(new ElementOption().withElement(webElementProvider.findElement(element)))
            .withTapsCount(tapsCount))
        .perform();
  }

  public void multipleTapWithDelay(ILocatable element, int tapsCount, int delay) {
    TouchAction actions = new TouchAction<>((IOSDriver) webDriverFactory.getDriver());
    for (int i = 0; i < tapsCount; i++) {
      actions = actions
          .tap(new TapOptions()
              .withElement(new ElementOption().withElement(webElementProvider.findElement(element)))
          )
          .waitAction(new WaitOptions().withDuration(Duration.of(delay, ChronoUnit.MILLIS)));
    }
    actions.perform();
  }

  public void swipeRightOnElement(ILocatable element) {
    swipeOnElement(element, "right");
  }

  public void swipeLeftOnElement(ILocatable element) {
    swipeOnElement(element, "left");
  }

  public void swipeUpOnElement(ILocatable element) {
    swipeOnElement(element, "up");
  }

  public void swipeDownOnElement(ILocatable element) {
    swipeOnElement(element, "down");
  }
}
