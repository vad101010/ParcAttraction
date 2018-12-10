package fr.istic.csr;

public class Billetterie
{
    private int nbBillet = 50;
    //nombre de tickets manquants
    private int nbTicketManquant = 0;

    //méthode permetant au responsable de remettre les billets dans la billetterie et attendre si il y en a suffisament
    public synchronized void remettreBillet()
    {
        try
        {
            while (true)
            {
                while (nbTicketManquant == 0) wait();
                System.out.println("je remet des tickets nbTicketManquant=" + nbTicketManquant);
                this.nbBillet += 10 + nbTicketManquant;
                nbTicketManquant = 0;
                System.out.println("nbticket=" + nbBillet);
                notifyAll();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

    //permet à un client de retirer nbBillet ticket à la billetterie ou attendre tant qu'il n'y en a pas
    public synchronized int takeTicket(int nbBillet) throws InterruptedException
    {
        System.out.println("j'entre");
        if (this.nbBillet - nbBillet < 0)
        {
            nbTicketManquant += Math.abs(this.nbBillet - nbBillet);
            notifyAll();
        }
        while (this.nbBillet - nbBillet < 0)
        {
            notifyAll();
            wait();
        }
        this.nbBillet = this.nbBillet - nbBillet;
        System.out.println("je sort");
        return nbBillet;
    }
}
