package main.java.csr.parc;

import java.util.List;
import java.util.Random;

public class Client extends Thread
{
    private int nbBillet = 0;
    private List<Attraction> listAttraction;
    private Attraction currentAttraction;
    private Billetterie billetterie;
    private EtatClient etat;

    // Crée un client en indiquant les attractions disponibles et la billetterie
    public Client(List<Attraction> listAttraction, Billetterie billetterie) {
        this.listAttraction = listAttraction;
        this.billetterie = billetterie;
        this.etat = EtatClient.INIT;
    }

    // Prend un nombre de ticket aléatoire dans la billetterie, fait 2 attractions aléatoires et sort.
    @Override
    public void run()
    {
        try
        {
            sleep(500);
            Random random = new Random();
            int randomNumber = random.nextInt(5 + 1 - 1) + 1;
            System.out.println("Je tente de prendre un ticket " + Thread.currentThread().getId());
            nbBillet = billetterie.takeTicket(randomNumber);
            this.etat = EtatClient.ENTERED;
            System.out.println("Je prend des tickets " + Thread.currentThread().getId());
            for (int i = 0; i < 2; i++)
            {
                randomNumber = random.nextInt(listAttraction.size());
                currentAttraction = listAttraction.get(randomNumber);
                sleep(1500);
                System.out.println("Je vais à l'attraction " + i + " numéro : " + randomNumber);
                currentAttraction.embarquer(this);
                System.out.println("J'ai fini l'attraction " + i + " numéro : " + randomNumber);
                currentAttraction.debarquer(this);
            }
            this.etat = EtatClient.GONE;
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("Je m'en vais");
    }

    public void setEtat(EtatClient e) {
        this.etat = e;
    }

    public EtatClient getEtat() {
        return this.etat;
    }

}
