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

    //créer une nouvelle navette
    public Navette(int tpsAttente, int nbPlace, Attraction attraction)
    {
        this.tpsAttente = tpsAttente;
        this.nbPlace = nbPlace;
        this.attraction = attraction;
        this.clients = new ArrayList<>();
        this.setDaemon(true);
    }

    //retourn vrai si il y a de la place dans la navette
    public synchronized boolean isAvailablePlace()
    {
        if (nbPlace == clients.size()) return false;
        return true;
    }

    //ajoute un client dans la navette
    public synchronized void embarquer(Client cli)
    {
        clients.add(cli);
        System.out.println("il y a " + clients.size() + " clients à bord");
    }

    //débarque uniquement les clients si la navatte this se trouve à quai dans l'attraction
    public synchronized void debarquer(Client cli) throws InterruptedException
    {
        while (this != attraction.getNavetteaQuai()) wait();
        clients.remove(cli);
    }

    public synchronized void signalerArrivee()
    {
        notifyAll();
    }

    //la navette signal son départ à l'attraction, roule, et se met en place file d'attente pour le retour puis attend que les gens
    //montent et repart
    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                attraction.retourNavette(this);
                signalerArrivee();
                sleep(tpsAttente);
                attraction.departNavette();
                sleep(tempsderoute);
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
