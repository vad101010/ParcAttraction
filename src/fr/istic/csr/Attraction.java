package fr.istic.csr;

import java.util.ArrayList;
import java.util.List;

public class Attraction
{
    private Navette navetteaQuai;
    private List<Navette> navettes;

    //création de l'attraction avec nbNavette navettes d'une durée de circuit de tpsCircuit avec nbPlaces places
    public Attraction(int nbNavette, int tpsCircuit, int nbPlaces)
    {
        navettes = new ArrayList<>();
        navetteaQuai = null;
        for (int i = 0; i < nbNavette; i++)
        {
            Navette tmpNav = new Navette(tpsCircuit, nbPlaces, this);
            navettes.add(tmpNav);
            tmpNav.start();
        }
    }

    //méthode permettant à un client d'entrer dans une navette dès qu'il y a de la place disponible (attendre si non) et
    // ressortir à la fin du tour dès que la navette revient à quai
    public synchronized void embarquer(Client client) throws InterruptedException
    {
        while (navetteaQuai == null || !navetteaQuai.isAvailablePlace()) wait();
        System.out.println("J'embarque");
        navetteaQuai.embarquer(client);
        wait();
        navetteaQuai.debarquer(client);
        System.out.println("je débarque");
    }

    //permet à une navette de se positionner dans la file d'attente pour le retour
    public synchronized void retourNavette(Navette nav) throws InterruptedException
    {
        while (navetteaQuai != null) wait();
        navetteaQuai = nav;
        notifyAll();
    }

    //permet à une navette de signaler son départ
    public synchronized void departNavette()
    {
        navetteaQuai = null;
    }

    //retourne la navette actuellement à quai
    public Navette getNavetteaQuai()
    {
        return navetteaQuai;
    }
}
