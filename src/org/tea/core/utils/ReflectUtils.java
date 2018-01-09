package org.tea.core.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtils {

	public static Object invokeGet(Object obj, String filedName) {
		Object object = null;
		try {
			Class<?> beanClass = obj.getClass();
			PropertyDescriptor pd = new PropertyDescriptor(filedName, beanClass);
			Method method = pd.getReadMethod();
			if (pd!=null) {
				object = method.invoke(obj);
			}
		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	public static void invokeSet(Object obj, String filedName, Object filedValue) {
		try {
			if (filedValue!=null) {
				Class<?> beanClass = obj.getClass();
				PropertyDescriptor pd = new PropertyDescriptor(filedName, beanClass);
				Method method = pd.getWriteMethod();
				method.invoke(obj, filedValue);
			}
		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
}
