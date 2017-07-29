package com.perficient.hr.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity( prePostEnabled = true )
public abstract class MethodSecurityConfiguration extends GlobalMethodSecurityConfiguration  {

	/*@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		RolesPermissionEvaluator permissionEvaluator = new RolesPermissionEvaluator();
		DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
		expressionHandler.setPermissionEvaluator(permissionEvaluator);
		return expressionHandler;
	}*/
	
	/*protected abstract ApplicationContext applicationContext();

	protected abstract ConversionService conversionService();
	
	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler() {
		final ApplicationContext applicationContext = applicationContext();
		final TypeConverter typeConverter = new StandardTypeConverter(conversionService());
		final DefaultMethodSecurityExpressionHandler handler = new DefaultMethodSecurityExpressionHandler() {
			@Override
			public StandardEvaluationContext createEvaluationContextInternal(final Authentication authentication, final MethodInvocation methodInvocation) {
				final StandardEvaluationContext decoratedStandardEvaluationContext = super.createEvaluationContextInternal(authentication, methodInvocation);
				return new ForwardingStandardEvaluationContext() {
					@Override
					protected StandardEvaluationContext standardEvaluationContext() {
						return decoratedStandardEvaluationContext;
					}

					@Override
					public TypeConverter getTypeConverter() {
						return typeConverter;
					}
				};
			}
		};
		handler.setApplicationContext(applicationContext);
		return handler;
	}*/
}
