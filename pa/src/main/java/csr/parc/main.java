package main.java.csr.parc;

import main.java.csr.rest.app.ParcApplication;
import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Context;
import org.restlet.data.Protocol;

import java.util.ArrayList;
import java.util.List;

public final class main
{
    static private List<Client> clientList = new ArrayList<>();
    static private List<Attraction> attractions = new ArrayList<>();

    public static void main(String[] args) throws Exception
    {
        // Création des éléments nécessaires pour REST
        Component component = new Component();
        Context context = component.getContext().createChildContext();
        component.getServers().add(Protocol.HTTP, 8124);

        Application app = new ParcApplication(context);

        context.getAttributes().put("clients", clientList);
        context.getAttributes().put("attractions", attractions);
        component.getDefaultHost().attach(app);

        component.start();

        // Instanciation des éléments du parc
        Billetterie billetterie = new Billetterie();
        ResponsableBilletterie resp = new ResponsableBilletterie(billetterie);
        resp.start();
        for (int i = 0; i < 4; i++)
        {
            attractions.add(new Attraction(i + 1, 2000, 5));
        }
        for (int i = 0; i < 20; i++)
        {
            Client tmpCli = new Client(attractions, billetterie);
            clientList.add(tmpCli);
            tmpCli.start();
        }
        System.out.println("terminé bonsoir");
    }

}
