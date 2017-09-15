package rewards.domain.model;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Merchant.class)
public abstract class Merchant_ {

	public static volatile SingularAttribute<Merchant, String> number;
	public static volatile SingularAttribute<Merchant, String> name;
	public static volatile SingularAttribute<Merchant, BigDecimal> minimumAmount;
	public static volatile SingularAttribute<Merchant, Long> id;
	public static volatile SingularAttribute<Merchant, BigDecimal> amountPerPoint;

}

