package main.java.csr.rest.app;


import main.java.csr.parc.Attraction;
import main.java.csr.rest.resources.AttractionsResource;
import main.java.csr.rest.resources.ClientsResource;
import org.restlet.Application;
import org.restlet.Context;
import org.restlet.Restlet;
import org.restlet.routing.Router;

public class ParcApplication extends Application {

    public ParcApplication(Context context) {
        super(context);
    }

    @Override
    public Restlet createInboundRoot() {
        Router router = new Router(getContext());
        router.attach("/clients", ClientsResource.class);
        router.attach("/navettes", AttractionsResource.class);
        return router;
    }

}
