package fr.istic.csr;

import java.util.List;

public class Attraction
{
    private Navette navetteaQuai;
    private List<Navette> navettes;

    public Attraction(int nbNavette, int tpsAttente, int nbPlaces)
    {
        navetteaQuai = new Navette(tpsAttente, nbPlaces, this);
        navettes.add(navetteaQuai);
        navetteaQuai.run();
        for (int i = 1; i < nbNavette; i++)
        {
            Navette tmpNav = new Navette(tpsAttente, nbPlaces, this);
            navettes.add(tmpNav);
            tmpNav.run();
        }
    }

    public synchronized Navette play(Client client) throws InterruptedException
    {
        while (navetteaQuai == null || !navetteaQuai.addClients(client)) wait();
        return navetteaQuai;
    }

    public synchronized void retourNavette(Navette nav) throws InterruptedException
    {
        while (navetteaQuai != null) wait();
        navetteaQuai = nav;
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
