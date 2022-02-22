package com.github.mishaninss.arma.uidriver.ios;

import com.github.mishaninss.arma.uidriver.LocatorType;
import com.github.mishaninss.arma.uidriver.webdriver.LocatorConverter;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @author Sergey Mishanin Class for locators handling.
 */
@Component
@Profile("ios")
public final class IosLocatorConverter extends LocatorConverter {

  public static final String LOCATOR_TYPE_IOS_CLASS_CHAIN = "iosclasschain";
  public static final String LOCATOR_TYPE_IOS_NS_PREDICATE_STRING = "iosnspredicatestring";
  public static final String LOCATOR_ACCESSIBILITY_ID = "accessibilityid";

  public IosLocatorConverter() {
    addConverter(LOCATOR_TYPE_IOS_CLASS_CHAIN, this::byIosClassChain);
    addConverter(LOCATOR_TYPE_IOS_NS_PREDICATE_STRING, this::byIosNsPredicateString);
    addConverter(LOCATOR_ACCESSIBILITY_ID, this::byAccessibilityId);
    addConverter(LocatorType.ID, this::byAccessibilityId);
  }

  /**
   * Converter for type "iosClassChain"
   */
  public By byIosClassChain(final String locator) {
    return MobileBy.iOSClassChain(locator);
  }

  /**
   * Converter for type "iOSNsPredicateString"
   */
  public By byIosNsPredicateString(final String locator) {
    return MobileBy.iOSNsPredicateString(locator);
  }

  /**
   * Converter for type "accessibilityId"
   */
  public By byAccessibilityId(final String locator) {
    return MobileBy.AccessibilityId(locator);
  }

}