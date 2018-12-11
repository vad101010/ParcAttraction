package main.java.csr.parc;

import java.util.ArrayList;
import java.util.List;

public class Navette extends Thread
{
    private int tpsAttente = 1000;
    private int nbPlace;
    private int tempsderoute;
    private List<Client> clients;
    private Attraction attraction;
    private EtatNavette etat;

    // Création d'une navette
    public Navette(int tpsRoute, int nbPlace, Attraction attraction)
    {
        this.tempsderoute = tpsRoute;
        this.nbPlace = nbPlace;
        this.attraction = attraction;
        this.clients = new ArrayList<>();
        this.etat = EtatNavette.TRAVELING;
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
        if (cli.getEtat() == EtatClient.ENTERED) {
            cli.setEtat(EtatClient.RIDE1);
        } else {
            cli.setEtat(EtatClient.RIDE2);
        }
        clients.add(cli);
        System.out.println("il y a " + clients.size() + " clients à bord");
    }

    //débarque uniquement les clients si la navette this se trouve à quai dans l'attraction
    public synchronized void debarquer(Client cli) throws InterruptedException
    {
        while (this != attraction.getNavetteaQuai()) wait();
        if (cli.getEtat() == EtatClient.RIDE1) {
            cli.setEtat(EtatClient.TRANSIT);
        }
        clients.remove(cli);
    }

    public void setEtat(EtatNavette e) {
        this.etat = e;
    }

    public EtatNavette getEtat() {
        return this.etat;
    }

    public synchronized void signalerArrivee()
    {
        notifyAll();
    }

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
                this.etat = EtatNavette.TRAVELING;
                sleep(tempsderoute);
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
