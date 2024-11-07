import org.example.banque.classes.Transaction;
import org.example.banque.classes.Banque;
import org.example.banque.classes.Compte;
import org.example.banque.classes.Transaction;
import org.example.banque.classes.Banque;
import org.example.banque.classes.Compte;
import org.junit.Assert;
import org.junit.Test;

public class TransactionTest {

    @Test
    public void testTransactionSerialization() {
        // Créer une instance de Banque
        Banque banque1 = new Banque(1, "Banque X", "France");
        Banque banque2 = new Banque(2, "Banque Y", "Maroc");

        // Créer deux instances de Compte, chacune associée à une banque
        Compte compteEmetteur = new Compte(1000.0, 12, banque1.getBanque_id()); // Associe le compte à la banque via l'ID
        Compte compteRecepteur = new Compte(2000.0, 13, banque2.getBanque_id()); // Associe le compte à la banque via l'ID

        // Créer une transaction entre ces deux comptes
        Transaction transaction = new Transaction(1, 1000.0, compteEmetteur.getCompteId(), compteRecepteur.getCompteId());

        // Vérifier que la transaction a été correctement créée
        Assert.assertNotNull(transaction);
        Assert.assertEquals(1000.0, transaction.getMontant(), 0.01);
        Assert.assertEquals(compteEmetteur.getCompteId(), transaction.getCompteIdEmetteur());
        Assert.assertEquals(compteRecepteur.getCompteId(), transaction.getCompteIdRecepteur());

        // Vérifier que les comptes sont associés à des banques valides
        Assert.assertNotNull(compteEmetteur.getBanqueId());
        Assert.assertNotNull(compteRecepteur.getBanqueId());
        Assert.assertEquals(1, compteEmetteur.getBanqueId());
        Assert.assertEquals(2, compteRecepteur.getBanqueId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTransactionWithoutBanque() {
        // Créer une instance de Banque
        Banque banque1 = new Banque(1, "Banque X", "France");

        // Créer un compte sans banque associée
        Compte compteEmetteur = new Compte(5000.0, 12, 0); // Compte sans banque associée (ID de banque = 0)
        Compte compteRecepteur = new Compte(3000.0, 13, banque1.getBanque_id());

        // Essayer de créer une transaction avec un compte sans banque
        new Transaction(1, 1000.0, compteEmetteur.getCompteId(), compteRecepteur.getCompteId());
    }
}
