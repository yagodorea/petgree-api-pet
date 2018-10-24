package com.dorea.petgree.pet.specification;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.ParameterRegistry;
import org.hibernate.query.criteria.internal.Renderable;
import org.hibernate.query.criteria.internal.compile.RenderingContext;
import org.hibernate.query.criteria.internal.expression.LiteralExpression;
import org.hibernate.query.criteria.internal.predicate.AbstractSimplePredicate;

import javax.persistence.criteria.Expression;

public class WithinPredicate extends AbstractSimplePredicate {
	private final Expression<Point> matchExpression;
	private final Expression<Geometry> area;

	public WithinPredicate(CriteriaBuilderImpl criteriaBuilder, Expression<Point> matchExpression, Geometry area) {
		this(criteriaBuilder, matchExpression, new LiteralExpression<Geometry>(criteriaBuilder, area));
	}

	public WithinPredicate(CriteriaBuilderImpl criteriaBuilder, Expression<Point> matchExpression, Expression<Geometry> area) {
		super(criteriaBuilder);
		this.matchExpression = matchExpression;
		this.area = area;
	}

	public Expression<Point> getMatchExpression() {
		return matchExpression;
	}

	public Expression<Geometry> getArea() {
		return area;
	}

	public void registerParameters(ParameterRegistry registry) {
		// Nothing to register
	}

	@Override
	public String render(boolean isNegated, RenderingContext renderingContext) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(" within(")
				.append(((Renderable) getMatchExpression()).render(renderingContext))
				.append(", ")
				.append(((Renderable) getArea()).render(renderingContext))
				.append(") = true ");
		return buffer.toString();
	}

}
