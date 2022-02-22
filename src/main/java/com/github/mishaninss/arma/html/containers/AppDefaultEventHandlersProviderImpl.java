package com.github.mishaninss.arma.html.containers;

import com.github.mishaninss.arma.data.UiCommonsProperties;
import com.github.mishaninss.arma.html.containers.interfaces.IDefaultEventHandlersProvider;
import com.github.mishaninss.arma.html.listeners.IElementEventHandler;
import com.github.mishaninss.arma.html.listeners.LoggingEventHandler;
import com.github.mishaninss.arma.reporting.IReporter;
import com.github.mishaninss.arma.reporting.Reporter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AppDefaultEventHandlersProviderImpl implements IDefaultEventHandlersProvider,
    InitializingBean {

  private final List<IElementEventHandler> defaultEventHandlers = new ArrayList<>();

  @Autowired
  private ApplicationContext applicationContext;
  @Autowired
  private UiCommonsProperties properties;
  @Reporter
  private IReporter reporter;

  @Override
  public void afterPropertiesSet() {
    Set<String> eventHandlersClasses = getEventHandlersDefinitions();
    if (CollectionUtils.isNotEmpty(eventHandlersClasses)) {
      for (String eventHandlerClassName : eventHandlersClasses) {
        try {
          Class<? extends IElementEventHandler> eventHandlerClass = (Class<? extends IElementEventHandler>) Class.forName(
              eventHandlerClassName.trim());
          defaultEventHandlers.add(applicationContext.getBean(eventHandlerClass));
        } catch (ClassNotFoundException ex) {
          reporter.warn("Provided event handler class " + eventHandlerClassName + " was not found",
              ex);
        } catch (ClassCastException ex) {
          reporter.warn(
              "Provided event handler class " + eventHandlerClassName + " is not compatible with "
                  + IElementEventHandler.class.getCanonicalName(), ex);
        }

      }
    } else {
      defaultEventHandlers.add(applicationContext.getBean(LoggingEventHandler.class));
    }
  }

  private Set<String> getEventHandlersDefinitions() {
    Set<String> eventHandlersClasses = properties.framework().defaultEventHandlers;
    if (CollectionUtils.isEmpty(eventHandlersClasses)) {
      return eventHandlersClasses;
    }
    if (eventHandlersClasses.size() == 1 && StringUtils.isEmpty(
        eventHandlersClasses.iterator().next())) {
      return new HashSet<>();
    }
    return eventHandlersClasses;
  }

  @Override
  public List<IElementEventHandler> getEventHandlers() {
    return defaultEventHandlers;
  }
}
