package com.perficient.hr.security;

import org.springframework.expression.spel.support.StandardEvaluationContext;

public abstract class ForwardingStandardEvaluationContext extends StandardEvaluationContext {

	protected abstract StandardEvaluationContext standardEvaluationContext();

	//@Override public void setRootObject(final Object rootObject, final TypeDescriptor typeDescriptor) { standardEvaluationContext().setRootObject(rootObject, typeDescriptor); }

}
