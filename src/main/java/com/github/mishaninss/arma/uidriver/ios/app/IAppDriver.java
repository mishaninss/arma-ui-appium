package com.github.mishaninss.arma.uidriver.ios.app;

import java.util.Set;

public interface IAppDriver {

  String getContext();

  Set<String> getContextHandles();

  void setContext(String contextName);
}
