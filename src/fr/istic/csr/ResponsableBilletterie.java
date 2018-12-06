package fr.istic.csr;

public class ResponsableBilletterie extends Thread
{
    private int remiseBillet = 10;
    private Billetterie laBilletterie;

    public ResponsableBilletterie(Billetterie laBilletterie)
    {
        this.laBilletterie = laBilletterie;
        this.setDaemon(true);
    }

    public synchronized void checkRupture()
    {
    }

    @Override
    public void run()
    {
        while (true)
        {
            laBilletterie.remettreBillet(10);
        }
    }
}
