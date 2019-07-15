package wom.neg.orders.boundary;

import wom.neg.events.entity.CoffeeType;


import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.sun.jersey.api.NotFoundException;

import domain.model.entities.Order;
import domain.model.service.OrderServiceImpl;

import java.net.URI;
import java.util.UUID;
import java.util.logging.Logger;

@Path("orders")
public class OrdersResource {
	@Inject
	OrderServiceImpl orderService;

	 @Inject
	    Logger logger;

	
    @POST
    public Response orderCoffee(JsonObject order) {
        final String beanOrigin = order.getString("beanOrigin", null);
        final CoffeeType coffeeType = CoffeeType.fromString(order.getString("coffeeType", null));

        if (beanOrigin == null || coffeeType == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        Order orderToPersist = new Order();
        orderToPersist.setBeanOrigin(order.getString("beanOrigin", null));
        orderToPersist.setType(order.getString("coffeeType", null));
        orderToPersist.setId(UUID.randomUUID().toString());
        try {
			orderService.add(orderToPersist);
		} catch (Exception e) {
			logger.log(java.util.logging.Level.SEVERE, "Error persisting order " + order);
		}
 
        return Response.ok().header(HttpHeaders.LOCATION, "").build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON) 
    public Response getOrder(@PathParam("id") UUID orderId) {
        Order order = null;
		try {
			order = orderService.get(orderId.toString());
		} catch (Exception e) {
			logger.log(java.util.logging.Level.SEVERE, "Error finding order " + order);
		}

        if (order == null)
            throw new NotFoundException();

        return Response.ok(order).build();

    }

}
