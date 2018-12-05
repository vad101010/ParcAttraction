package fr.istic.csr;

import java.util.List;
import java.util.Random;

public class Client extends Thread
{
    private int nbBillet = 0;
    private List<Attraction> listAttraction;
    private Attraction currentAttraction;
    private Billetterie billetterie;

    public Client(List<Attraction> listAttraction, Billetterie billetterie)
    {
        this.listAttraction = listAttraction;
        this.billetterie = billetterie;
    }

    public int getNbBillet()
    {
        return nbBillet;
    }

    public void setNbBillet(int nbBillet)
    {
        this.nbBillet = nbBillet;
    }

    public Attraction getCurrentAttraction()
    {
        return currentAttraction;
    }

    public void setCurrentAttraction(Attraction currentAttraction)
    {
        this.currentAttraction = currentAttraction;
    }

    @Override
    public void run()
    {
        try
        {
            Random random = new Random();
            int randomNumber = random.nextInt(20 + 1 - 1) + 1;
            nbBillet = billetterie.takeTicket(randomNumber);
            for (int i = 0; i < 2; i++)
            {
                randomNumber = random.nextInt(listAttraction.size() + 1 - 0) + 0;
                currentAttraction = listAttraction.get(randomNumber);
                sleep(500);
                Navette nav = currentAttraction.play(this);
                nav.rouler();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

    }
}
