package wom.neg;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import wom.neg.orders.boundary.OrdersResource;

@ApplicationPath("resources")
public class JAXRSConfiguration extends Application {

    // nothing to configure
	@Override
	public Set<Class<?>> getClasses() {
	    final Set<Class<?>> classes = new HashSet<>();
	    // register root resource
	    classes.add(OrdersResource.class);
	    return classes;
	}

}