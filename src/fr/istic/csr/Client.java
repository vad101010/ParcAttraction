package fr.istic.csr;

import java.util.List;

public class Client extends Thread
{
    private int nbBillet = 0;
    private List<Attraction> listAttraction;
    private Attraction currentAttraction;

    public Client(List<Attraction> listAttraction)
    {
        this.listAttraction = listAttraction;
    }

    @Override
    public void run()
    {
        super.run();
    }
}
