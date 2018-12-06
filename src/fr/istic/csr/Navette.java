package fr.istic.csr;

import java.util.ArrayList;
import java.util.List;

public class Navette extends Thread
{
    private int tpsAttente;
    private int nbPlace;
    private int tempsderoute = 100;
    private List<Client> clients;
    private Attraction attraction;

    public Navette(int tpsAttente, int nbPlace, Attraction attraction)
    {
        this.tpsAttente = tpsAttente;
        this.nbPlace = nbPlace;
        this.attraction = attraction;
        this.clients = new ArrayList<>();
        this.setDaemon(true);
    }

    public synchronized boolean isAvailablePlace()
    {
        if (nbPlace == clients.size()) return false;
        return true;
    }

    public synchronized void embarquer(Client cli)
    {
        clients.add(cli);
    }

    public synchronized void debarquer(Client cli) throws InterruptedException
    {
        while (this != attraction.getNavetteaQuai()) wait();
        clients.remove(cli);
    }

    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                attraction.departNavette();
                sleep(tempsderoute);
                attraction.retourNavette(this);
                sleep(tpsAttente);
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
