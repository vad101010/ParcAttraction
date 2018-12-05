package fr.istic.csr;

public class ResponsableBilletterie extends Thread
{
    private int remiseBillet = 10;
    private Billetterie laBilletterie;

    public ResponsableBilletterie(Billetterie laBilletterie)
    {
        this.laBilletterie = laBilletterie;
    }

    @Override
    public void run()
    {
        while (true)
        {
            while (!laBilletterie.isRupture())
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
            laBilletterie.remettreBillet(remiseBillet);
            notifyAll();
        }
    }
}
