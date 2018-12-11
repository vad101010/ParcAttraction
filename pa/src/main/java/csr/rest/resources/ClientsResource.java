package main.java.csr.rest.resources;

import main.java.csr.parc.Client;
import main.java.csr.parc.EtatClient;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;
import org.restlet.ext.json.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ClientsResource extends ServerResource {

    private List<Client> clients;

    public ClientsResource() {
        super();
        clients = (List<Client>) getApplication().getContext().getAttributes().get("clients");
    }

    @Get("json")
    public Representation getClients() throws Exception {
        JSONObject jsonClients = new JSONObject();
        int index = 0;
        for (Client cli : clients)
        {
            JSONObject current = new JSONObject();
            current.put("Etat", cli.getEtat().toString());
            jsonClients.put(String.valueOf(index), current);
            index++;
        }
        return new JsonRepresentation(jsonClients);
    }

}
