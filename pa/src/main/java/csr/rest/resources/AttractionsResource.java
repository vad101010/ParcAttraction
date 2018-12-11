package main.java.csr.rest.resources;

import main.java.csr.parc.Attraction;
import main.java.csr.parc.Client;
import main.java.csr.parc.Navette;
import org.json.JSONArray;
import org.json.JSONObject;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AttractionsResource extends ServerResource {

    private List<Attraction> attractions;

    public AttractionsResource() {
        super();
        attractions = (List<Attraction>) getApplication().getContext().getAttributes().get("attractions");
    }

    @Get("json")
    public Representation getNavettes() throws Exception {
        JSONObject jsonAttNavette = new JSONObject();
        int numAtt = 0;
        for (Attraction att : attractions)
        {
            JSONObject current = new JSONObject();
            int numNav = 0;
            for (Navette n : att.getNavettes()) {
                JSONObject nav = new JSONObject();
                nav.put("Etat", n.getEtat());
                current.put(String.valueOf(numNav), nav);
                numNav++;
            }
            jsonAttNavette.put("Attraction " + numAtt, current);
            numAtt++;
        }
        return new JsonRepresentation(jsonAttNavette);
    }

}
