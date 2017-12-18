package hu.neckermann.service;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

public class NeckermannTravel extends Application {

	static {
		System.setProperty("org.restlet.engine.loggerFacadeClass", "org.restlet.ext.slf4j.Slf4jLoggerFacade");
	}

	public static void main(String[] args) {
		Component component = new Component();
		component.getDefaultHost().attach("/NeckermannTravel", new NeckermannTravel());
		Server server = new Server(Protocol.HTTP, 8111, component);
		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.setDefaultMatchingQuery(true);
		router.attach("/keywordSearch?{keywords}", SearchResource.class);
		router.attach("/flightSearch?{country}", FlightResource.class);
		router.attach("/flightSearch?{country}&{region}", FlightResource.class);
		router.attach("/singleSearch?{name}&{id}", SingleResource.class);
		router.attach("/countries", ListAllCountries.class);
		return router;
	}
	
}