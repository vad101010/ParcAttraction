package main.java.csr.parc;

import java.util.ArrayList;
import java.util.List;

public class Attraction
{
    private Navette navetteaQuai;
    private List<Navette> navettes;

    // Création de l'attraction avec son nombre de navettes, sa durée et le nombre de places par navette
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

    //méthode permettant à un client d'entrer dans une navette dès qu'il y a de la place disponible (attendre si non)
    public synchronized void embarquer(Client client) throws InterruptedException
    {
        while (navetteaQuai == null || !navetteaQuai.isAvailablePlace()) wait();
        navetteaQuai.embarquer(client);
        System.out.println("J'embarque");
        wait();
    }

    // Méthode permettant de faire sortir les clients de la navette une fois le tour terminé
    public synchronized void debarquer(Client client) throws InterruptedException
    {
        while (navetteaQuai == null || !navetteaQuai.getClients().contains(client)) wait();
        navetteaQuai.debarquer(client);
        System.out.println("je débarque");
    }

    //permet à une navette de se positionner dans la file d'attente pour le retour
    public synchronized void retourNavette(Navette nav) throws InterruptedException
    {
        while (navetteaQuai != null) wait();
        navetteaQuai = nav;
        nav.setEtat(EtatNavette.STOPPED);
        notifyAll();
    }

    //permet à une navette de signaler son départ
    public synchronized void departNavette()
    {
        navetteaQuai = null;
        notifyAll();
    }

    //retourne la navette actuellement à quai
    public Navette getNavetteaQuai()
    {
        return navetteaQuai;
    }

    public List<Navette> getNavettes() {
        return navettes;
    }
}
