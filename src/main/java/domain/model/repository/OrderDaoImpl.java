package domain.model.repository;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.inject.Named;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;

import domain.model.entities.Order;


@Named("orderDao") 
public class OrderDaoImpl extends BasicDAO<Order, ObjectId> implements OrderDao {

	@Inject
	public OrderDaoImpl(@Named("ds") Datastore ds) {
		super(ds);
	}	
	
}