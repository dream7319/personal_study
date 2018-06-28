package com.springboot.mybatis.condition;

import com.springboot.mybatis.controller.Spring4AllController;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @Conditional(MyCondition.class)
 * 这句代码可以标注在类上面，表示该类下面的所有@Bean都会启用配置
 * 也可以标注在方法上面，只是对该方法启用配置
 *
 *
 * 除了自己自定义Condition之外，Spring还提供了很多Condition给我们用
 * @ConditionalOnBean（仅仅在当前上下文中存在某个对象时，才会实例化一个Bean）
 * @ConditionalOnClass（某个class位于类路径上，才会实例化一个Bean）
 * @ConditionalOnExpression（当表达式为true的时候，才会实例化一个Bean）
 * @ConditionalOnMissingBean（仅仅在当前上下文中不存在某个对象时，才会实例化一个Bean）
 * @ConditionalOnMissingClass（某个class类路径上不存在的时候，才会实例化一个Bean）
 * @ConditionalOnNotWebApplication（不是web应用）
 *
 * @author lierlei@xingyoucai.com
 * @create 2018-06-27 17:13
 **/
@Configuration
public class ConditionConfig {

	/**
	 * @Conditional(MyCondition.class)
	 * 这句代码可以标注在类上面，表示该类下面的所有@Bean都会启用配置
	 * 也可以标注在方法上面，只是对该方法启用配置
	 * @return
	 */
	@Bean
	@Conditional(MyCondition.class)
	public String condition(){
		System.err.println("自定义的condition的match方法返回值为true时，才会进入该方法创建bean");
		return "condition";
	}

	/**
	 * @ConditionalOnBean（仅仅在当前上下文中存在某个对象时，才会实例化一个Bean）
	 *
	 * 存在Spring4AllController类的实例时
	 */
	@ConditionalOnBean(Spring4AllController.class)
	@Bean
	public String bean() {
		System.err.println("ConditionalOnBean is exist");
		return "bean";
	}

	@ConditionalOnClass(TestConditionOnClass.class)
	@Bean
	public String conditionOnClass(){
		System.out.println("condition on class");
		return "conditionOnClass";
	}
	/**
	 * 不存在Spring4AllController类的实例时
	 * @return
	 */
	@ConditionalOnMissingBean(Spring4AllController.class)
	@Bean
	public String missBean() {
		System.err.println("ConditionalOnBean is missing");
		return "missBean";
	}

	/**
	 * 表达式为true时 value可以spel表达式
	 */
	@ConditionalOnExpression(value = "true")
	@Bean
	public String expresssion() {
		System.err.println("expresssion is true");
		return "expresssion";
	}

	/**
	 * @ConditionalOnProperty是指在application.yml里配置的属性是否为true
	 */
	@ConditionalOnProperty(
			value = {"custom.field"},
			matchIfMissing = false)
	@Bean
	public String property() {
		System.err.println("property is true");
		return "property";
	}
}
