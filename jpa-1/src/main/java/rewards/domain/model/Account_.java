package rewards.domain.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Account.class)
public abstract class Account_ {

	public static volatile SingularAttribute<Account, String> number;
	public static volatile SetAttribute<Account, Card> cards;
	public static volatile SingularAttribute<Account, String> name;
	public static volatile SingularAttribute<Account, Integer> totalPoints;
	public static volatile SingularAttribute<Account, Long> id;

}

