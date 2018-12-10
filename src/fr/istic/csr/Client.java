package fr.istic.csr;

import java.util.List;
import java.util.Random;

public class Client extends Thread
{
    private int nbBillet = 0;
    private List<Attraction> listAttraction;
    private Attraction currentAttraction;
    private Billetterie billetterie;

    //créer un client en indiquand les attractions disponible et la billetterie
    public Client(List<Attraction> listAttraction, Billetterie billetterie)
    {
        this.listAttraction = listAttraction;
        this.billetterie = billetterie;
    }

    //prend un nombre de ticket aléatoire dans la billetterie, fait 2 attractions aléatoire et sort.
    @Override
    public void run()
    {
        try
        {
            Random random = new Random();
            int randomNumber = random.nextInt(20 + 1 - 1) + 1;
            System.out.println("Je tente de prendre un ticket" + Thread.currentThread().getId());
            nbBillet = billetterie.takeTicket(randomNumber);
            System.out.println("Je prend des tickets" + Thread.currentThread().getId());
            for (int i = 0; i < 2; i++)
            {
                randomNumber = random.nextInt(listAttraction.size());
                currentAttraction = listAttraction.get(randomNumber);
                sleep(500);
                System.out.println("Je vais à l'attraction " + i + " numéro :" + randomNumber);
                currentAttraction.embarquer(this);
                System.out.println("J'ai finis l'attraction " + i + " numéro :" + randomNumber);
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        System.out.println("je me casse");
    }
}
