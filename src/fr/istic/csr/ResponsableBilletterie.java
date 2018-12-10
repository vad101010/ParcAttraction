package fr.istic.csr;

public class ResponsableBilletterie extends Thread
{
    private Billetterie laBilletterie;

    //créer un responsable (en daemon)
    public ResponsableBilletterie(Billetterie laBilletterie)
    {
        this.laBilletterie = laBilletterie;
        this.setDaemon(true);
    }

    //va dans l'attraction pour remettre des billets
    @Override
    public void run()
    {
        laBilletterie.remettreBillet();
    }
}
