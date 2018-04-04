package com.jldata.smartframe.core.jpa;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.util.Assert;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.ManagedType;
import javax.persistence.metamodel.SingularAttribute;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author ljl
 */
public class JpaQueryUtil {
    /**
     * logger.
     */
    private static Logger logger = LoggerFactory.getLogger(JpaQueryUtil.class);

    private static final Set<Attribute.PersistentAttributeType> ASSOCIATION_TYPES;

    static {
        ASSOCIATION_TYPES = new HashSet<Attribute.PersistentAttributeType>(Arrays.asList(Attribute.PersistentAttributeType.MANY_TO_MANY,
                Attribute.PersistentAttributeType.MANY_TO_ONE, Attribute.PersistentAttributeType.ONE_TO_MANY, Attribute.PersistentAttributeType.ONE_TO_ONE));
    }

    public static <T> Specification buildSpecification(final List<PropertyFilter> propertyFilters) {
        return new Specification<T>() {
            @Override
            public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                return JpaQueryUtil.getPredicate(root, criteriaBuilder, propertyFilters);
            }
        };
    }

    private static Map<String, PropertyFilter> converMap(List<PropertyFilter> propertyFilters) {
        Map<String, PropertyFilter> result = new HashMap<String, PropertyFilter>();
        for (PropertyFilter propertyFilter : propertyFilters) {
            result.put(propertyFilter.getPropertyName().toLowerCase(), propertyFilter);
        }
        return result;
    }

    public static <T> Predicate getPredicate(Root<T> root, CriteriaBuilder cb, List<PropertyFilter> propertyFilters) {

        Assert.notNull(root, "Root must not be null!");
        Assert.notNull(cb, "CriteriaBuilder must not be null!");
        Assert.notNull(propertyFilters, "PropertyFilters must not be null!");
        Map<String, PropertyFilter> propertyFilterMap = converMap(propertyFilters);


        List<Predicate> predicates = getPredicates("", cb, root, root.getModel(), propertyFilterMap);

        if (predicates.isEmpty()) {
            return cb.isTrue(cb.literal(true));
        }

        if (predicates.size() == 1) {
            return predicates.iterator().next();
        }

        Predicate[] array = predicates.toArray(new Predicate[predicates.size()]);

        return cb.and(array);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    static List<Predicate> getPredicates(String path, CriteriaBuilder cb, Path<?> from, ManagedType<?> type, Map<String, PropertyFilter> propertyFilters) {

        List<Predicate> predicates = new ArrayList<>();

        for (SingularAttribute attribute : type.getSingularAttributes()) {

            String currentPath = !StringUtils.hasText(path) ? attribute.getName() : path + "." + attribute.getName();
            currentPath =currentPath.toLowerCase();
            //  predicates.add(cb.isNull(from.get(attribute)));
            if (attribute.getPersistentAttributeType().equals(Attribute.PersistentAttributeType.EMBEDDED)) {
                predicates.addAll(getPredicates(currentPath, cb, from.get(attribute.getName()),
                        (ManagedType<?>) attribute.getType(), propertyFilters));
                continue;
            }

            if (isAssociation(attribute)) {
                if (!(from instanceof From)) {
                    throw new JpaSystemException(new IllegalArgumentException(String
                            .format("Unexpected path type for %s. Found %s where From.class was expected.", currentPath, from)));
                }
                predicates.addAll(getPredicates(currentPath, cb, ((From<?, ?>) from).join(attribute.getName()),
                        (ManagedType<?>) attribute.getType(), propertyFilters));
                continue;
            }
            PropertyFilter propertyFilter = propertyFilters.get(currentPath);
            if (propertyFilter == null) {
                continue;
            }
            Object attributeValue = propertyFilter.getMatchValue();
            if (attributeValue == null) {
                continue;
            }

            Predicate predicate = null;
            MatchType matchType = propertyFilter.getMatchType();
            // 根据MatchType构造predicate
            switch (matchType) {
                case EQ:
                    predicate = cb.equal(from.get(attribute), attributeValue);
                    break;
                case NOT:
                    predicate = cb.notEqual(from.get(attribute), attributeValue);
                    break;
                case CONTAIN:
                    predicate = cb.like(from.get(attribute), "%" + attributeValue + "%");
                    break;
                case START:
                    predicate = cb.like(from.get(attribute), attributeValue + "%");
                    break;
                case END:
                    predicate = cb.like(from.get(attribute), "%" + attributeValue);
                    break;
                case LE:
                    predicate = cb.lessThanOrEqualTo(from.get(attribute), (Comparable)attributeValue);
                    break;
                case LT:
                    predicate = cb.lessThan(from.get(attribute), (Comparable)attributeValue);
                    break;
                case GE:
                    predicate = cb.greaterThanOrEqualTo(from.get(attribute), (Comparable)attributeValue);
                    break;
                case GT:
                    predicate = cb.greaterThan(from.get(attribute), (Comparable)attributeValue);
                    break;
                case IN:
                   CriteriaBuilder.In<Object> in =cb.in(from.get(attribute));
                   for(Object v:(Collection)attributeValue){
                       in.value(v);
                   }
                    predicate = in;
                    break;
                case INL:
                    predicate = cb.isNull(from.get(attribute));
                    break;
                case NNL:
                    predicate = cb.isNotNull(from.get(attribute));
                    break;
                default:
                   // predicate = cb.equal(from.get(attribute), attributeValue);
                    predicate= null;
                    break;
            }
            if(predicate!=null){
                predicates.add(predicate);
            }
        }
        return predicates;
    }
    private static Number castNumber(Object o){
        Number result = null;
        if(o instanceof Number){
            result =  (Number)o;
        } else if(o instanceof Date){
           result = BigDecimal.valueOf(((Date)o).getTime());
        } else if (o instanceof String){
            result = NumberUtils.parseNumber((String)o,BigDecimal.class);
        }
        return result;
    }

    private static boolean isAssociation(Attribute<?, ?> attribute) {
        return ASSOCIATION_TYPES.contains(attribute.getPersistentAttributeType());
    }


}
