package domain.model.service;

import java.util.Collection;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Criteria;
import org.mongodb.morphia.query.Query;

import domain.model.entities.Order;
import domain.model.repository.OrderDaoImpl;

/**
 *
 * @author Luis Vergara
 */

public class OrderServiceImpl implements OrderService {

	@Inject
	private OrderDaoImpl orderDao;

	public OrderServiceImpl() {

	}

	

	public Order get(String id) throws Exception {
		

		
		Query<Order> query = this.orderDao.createQuery().filter("id", id);
		
		return (Order) this.orderDao.findOne(query);
	}

	public boolean add(Order entity) throws Exception {
		this.orderDao.save(entity);
		return true;
	}

	@Override
	public boolean update(Order order) throws Exception {
		this.orderDao.save(order);
		return true;
	}

	@Override
	public Collection<Order> getAll() throws Exception {
		return null;
	}

	@Override
	public boolean delete(String id) throws Exception {
		this.orderDao.delete(this.get(id));
		return true;
	}

}
