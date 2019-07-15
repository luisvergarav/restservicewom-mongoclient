package domain.model.entities;

import java.io.Serializable;

import org.mongodb.morphia.annotations.Entity;


/**
 * @author lvergara
 *
 */
@Entity
public class Order extends BaseEntity<String> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String type;
	private String beanOrigin;


	public Order() {
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBeanOrigin() {
		return beanOrigin;
	}

	public void setBeanOrigin(String beanOrigin) {
		this.beanOrigin = beanOrigin;
	}

	

	

}