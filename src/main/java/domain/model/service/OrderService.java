package domain.model.service;

import java.util.Collection;
import domain.model.entities.Order;

/**
 *
 * @author Luis Vergara
 */
public interface OrderService {

	public boolean add(Order order) throws Exception;
	public boolean update(Order order) throws Exception;
	public Order get(String id) throws Exception; 
	public Collection<Order> getAll() throws Exception;
	public boolean delete(String id) throws Exception;
}
