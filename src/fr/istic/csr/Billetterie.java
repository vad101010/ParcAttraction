package fr.istic.csr;

public class Billetterie
{
    private int nbBillet = 50;
    private boolean rupture = false;

    public synchronized void remettreBillet(int nbBillet)
    {
        while (!rupture)
        {
            try
            {
                wait();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        this.nbBillet += nbBillet;
        rupture = false;
        notifyAll();
    }

    public int getNbBillet()
    {
        return nbBillet;
    }

    public synchronized int takeTicket(int nbBillet) throws InterruptedException
    {
        while (this.nbBillet - nbBillet < 0)
        {
            rupture = true;
            notifyAll();
            wait();
        }
        this.nbBillet = this.nbBillet - nbBillet;
        return nbBillet;
    }

    public boolean isRupture()
    {
        return rupture;
    }
}
