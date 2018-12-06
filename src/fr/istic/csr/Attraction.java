package fr.istic.csr;

import java.util.ArrayList;
import java.util.List;

public class Attraction
{
    private Navette navetteaQuai;
    private List<Navette> navettes;

    public Attraction(int nbNavette, int tpsAttente, int nbPlaces)
    {
        navettes = new ArrayList<>();
        navetteaQuai = new Navette(tpsAttente, nbPlaces, this);
        navettes.add(navetteaQuai);
        navetteaQuai.start();
        for (int i = 1; i < nbNavette; i++)
        {
            Navette tmpNav = new Navette(tpsAttente, nbPlaces, this);
            navettes.add(tmpNav);
            tmpNav.start();
        }
    }

    public synchronized void embarquer(Client client) throws InterruptedException
    {
        while (navetteaQuai == null || !navetteaQuai.isAvailablePlace()) wait();
        System.out.println("J'embarque");
        navetteaQuai.embarquer(client);
        wait();
        navetteaQuai.debarquer(client);
        System.out.println("je débarque");
    }

    public synchronized void retourNavette(Navette nav) throws InterruptedException
    {
        while (navetteaQuai != null) wait();
        navetteaQuai = nav;
        notifyAll();
    }

    public void departNavette()
    {
        navetteaQuai = null;
    }

    public Navette getNavetteaQuai()
    {
        return navetteaQuai;
    }
}
