package fr.istic.csr;

import java.util.ArrayList;
import java.util.List;

public class main
{
    public static void main(String[] args)
    {
        //créer une liste d'attractions avec nombre de navettes différent pour chacune
        List<Attraction> attractions = new ArrayList<>();
        for (int i = 0; i < 4; i++)
        {
            attractions.add(new Attraction(i + 1, 200, 5));
        }
        //création de la billetterie et de son responsable
        Billetterie billetterie = new Billetterie();
        ResponsableBilletterie resp = new ResponsableBilletterie(billetterie);
        resp.start();
        //création de 20 clients
        for (int i = 0; i < 20; i++)
        {
            Client tmpCli = new Client(attractions, billetterie);
            tmpCli.start();
        }
        System.out.println("tout s'est bien passé");
    }
}
