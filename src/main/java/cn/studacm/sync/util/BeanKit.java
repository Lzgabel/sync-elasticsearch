
package cn.studacm.sync.util;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * 〈功能简述〉<br>
 * 〈spring boot bean 工具〉
 *
 * @author lizhi
 * @date 2020-03-06
 * @since 1.0.0
 */

public class BeanKit implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 注入context
     *
     * @param context
     */
    @Override
    public void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    public static void setApplication(ApplicationContext context) {
        applicationContext = context;
    }

    /**
     * 得到上下文
     *
     * @return
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 根据名称获取
     *
     * @param beanName
     * @return bean instance if exist or null
     */
    public static Object getBean(String beanName) {
        if (applicationContext.containsBean(beanName)) {
            return applicationContext.getBean(beanName);
        }
        return null;
    }

    /**
     * 根据名称和类型
     *
     * @param beanName
     * @param clz
     * @return bean instance if exist or null
     */
    public static <T> T getBean(String beanName, Class<T> clz) {
        if (applicationContext.containsBean(beanName)) {
            return applicationContext.getBean(beanName, clz);
        }
        return null;
    }

    /**
     * 根据类型获取bean
     *
     * @param clz
     * @return
     */
    public static <T> T getBean(Class<T> clz) {
        return applicationContext.getBean(clz);
    }


    /**
     * 根据类型获取beanName
     *
     * @param clz
     * @return
     */
    public static String[] getBeanNamesForType(Class<?> clz) {
        return applicationContext.getBeanNamesForType(clz);
    }

    /**
     * 根据类型获取beanMap
     *
     * @param clz
     * @return
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clz) {
        return applicationContext.getBeansOfType(clz);
    }

    /**
     * 根据bean上的注解获取bean
     *
     * @param clz
     * @return
     */
    public static Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> clz) {
        return applicationContext.getBeansWithAnnotation(clz);
    }

    /**
     * 获取beanFactory
     *
     * @return
     */
    public static DefaultListableBeanFactory getBeanFactory() {
        return (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
    }

    /**
     * 动态注册 bean
     *
     * @param beanName
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T registerDefinition(String beanName, Class<T> clz) {
        return registerDefinition(beanName, clz, false);
    }

    /**
     * 动态注册 bean
     *
     * @param beanName
     * @param clz
     * @param <T>
     * @return
     */
    public static <T> T registerDefinition(String beanName, Class<T> clz, boolean override) {
        DefaultListableBeanFactory beanFactory = getBeanFactory();
        if (override && beanFactory.containsBean(beanName)) {
            beanFactory.removeBeanDefinition(beanName);
        }
        if (!beanFactory.containsBean(beanName)) {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(clz);
            beanFactory.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
        }
        return getBean(beanName, clz);
    }

    /**
     * 手动注入 bean
     *
     * @param bean
     * @param singleton
     */
    public static void registerSingleton(String bean, Object singleton) {
        DefaultListableBeanFactory beanFactory = getBeanFactory();
        if (beanFactory.containsBean(bean)) {
            beanFactory.destroySingleton(bean);
            beanFactory.registerSingleton(bean, singleton);
            // 对应 @Autowired 注解进行依赖注入
            beanFactory.autowireBean(singleton);
        }
    }


    /**
     * 脱离 IoC 容器管理 创建的对象进行进行依赖注入
     * eg:
     * // 在 Job 中 注入 bean
     * class Example implements Job {
     *      @Autowired ExempleInject exampleInject;
     *      <p>
     * }
     * @Component class ExampleInject {
     *      // do-nothing
     * }
     * <p>
     * use:
     * Example e = new Example()
     * BeanKit.autowireBean(e)
     */
    public static void autowireBean(Object bean) {
        DefaultListableBeanFactory beanFactory = getBeanFactory();
        beanFactory.autowireBean(bean);
    }


    public static void refresh(Map<String, Object> refreshBeanMap) {
        DefaultListableBeanFactory beanFactory = getBeanFactory();
        for (Map.Entry<String, Object> entry : refreshBeanMap.entrySet()) {
            String beanName = entry.getKey();
            if (beanFactory.containsBean(beanName)) {
                // 消除原先的bean
                // bean 注入两种方式:
                // 1. registerBeanDefinition
                // (注: 不使用次方式原因是因为 Mock() 方法 mock 的对象无法正确的 bean class name)

//                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
//                beanFactory.removeBeanDefinition(beanName);
//                beanDefinition.setBeanClassName(entry.getValue().getClass().getCanonicalName());
//                beanDefinition.setPrimary(true);
//                beanFactory.registerBeanDefinition(beanName, beanDefinition);

                // 2. registerSingleton

                // 获取所有依赖当前 bean 的 bean
                String[] dependentBeans = beanFactory.getDependentBeans(beanName);

                beanFactory.destroySingleton(beanName);
                beanFactory.registerSingleton(beanName, entry.getValue());

                // 刷新 bean
                for (String bean: dependentBeans) {
                    autowireBean(bean);
                    Object b = getBean(bean);
                        // 更新 dependentMap
                    beanFactory.registerDependentBean(beanName, bean);
                }
            }
        }

        // publish context refresh event for mock consumer
        applicationContext.publishEvent(new ContextRefreshedEvent(applicationContext));
    }


    /**
     * 获取 bean 原始类型
     *
     * @param beanName
     * @return
     */
    public static Class<?> getBeanTargetClass(String beanName) {
        Object bean = getBean(beanName);
        Class<?> type;
        if (AopUtils.isAopProxy(bean)) {
            type = AopUtils.getTargetClass(bean);
        } else {
            type = getBeanFactory().getType(beanName);
        }
        return type;
    }

    /**
     * 获取 bean 原始类型
     *
     * @param clz
     * @return
     */
    public static Class<?> getBeanTargetClass(Class<?> clz) {
        Object bean = getBean(clz);
        Class<?> type;
        if (AopUtils.isAopProxy(bean)) {
            type = AopUtils.getTargetClass(bean);
        } else {
            type = getBeanFactory().getType(clz.getName());
        }
        return type;
    }


}
