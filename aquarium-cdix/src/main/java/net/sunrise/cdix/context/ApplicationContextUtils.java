/**
 * 
 */
package net.sunrise.cdix.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author bqduc_2
 *
 */
public class ApplicationContextUtils implements ApplicationContextAware {
  private static ApplicationContext ctx;

  public static ApplicationContext getApplicationContext() {
      return ctx;
  }

  @Override
  public void setApplicationContext(ApplicationContext appContext) throws BeansException {
      ctx = appContext;
  }

  public static void refreshContext() {
      ((ConfigurableApplicationContext) ctx).refresh();
  }
}
