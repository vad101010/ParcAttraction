package fr.istic.csr;

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
    }

    public List<Client> getClients()
    {
        return clients;
    }

    public synchronized boolean addClients(Client client)
    {
        if (clients.size() == nbPlace) return false;
        clients.add(client);
        return true;
    }

    public void removeClients()
    {
        clients.clear();
    }

    public void rouler() throws InterruptedException
    {
        wait();
        while (this != attraction.getNavetteaQuai()) wait();
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
                notifyAll();
                sleep(tpsAttente);
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
