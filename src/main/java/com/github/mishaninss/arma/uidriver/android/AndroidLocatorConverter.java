package com.github.mishaninss.arma.uidriver.android;

import com.github.mishaninss.arma.uidriver.webdriver.LocatorConverter;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * @author Sergey Mishanin Class for locators handling.
 */
@Component
@Profile("android")
public final class AndroidLocatorConverter extends LocatorConverter {

  public static final String LOCATOR_TYPE_ANDROID_UI_AUTOMATOR = "androiduiautomator";
  public static final String LOCATOR_TYPE_ANDROID_VIEW_TAG = "androidviewtag";
  public static final String LOCATOR_ACCESSIBILITY_ID = "accessibilityid";


  public AndroidLocatorConverter() {
    addConverter(LOCATOR_TYPE_ANDROID_UI_AUTOMATOR, this::byAndroidUiAutomator);
    addConverter(LOCATOR_TYPE_ANDROID_VIEW_TAG, this::byAndroidViewTag);
    addConverter(LOCATOR_ACCESSIBILITY_ID, this::byAccessibilityId);
  }

  /**
   * Converter for type "androidUiAutomator"
   */
  public By byAndroidUiAutomator(final String locator) {
    return MobileBy.AndroidUIAutomator(locator);
  }

  /**
   * Converter for type "androidViewTag"
   */
  public By byAndroidViewTag(final String locator) {
    return MobileBy.AndroidViewTag(locator);
  }

  /**
   * Converter for type "accessibilityId"
   */
  public By byAccessibilityId(final String locator) {
    return MobileBy.AccessibilityId(locator);
  }

}