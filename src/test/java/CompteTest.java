import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.banque.classes.Compte;
import org.junit.Assert;
import org.junit.Test;

public class CompteTest {

    @Test
    public void testJsonSerialization() {
        // Create a Compte instance with test data
        Compte compte = new Compte(1000.00, 12, 1);

        // Serialize Compte instance to JSON
        String compteToJson = compte.toJson();

        // Expected JSON output
        String expectedJson = "{\"compteId\":0,\"solde\":1000.0,\"dateCreation\":\"2024-11-07\",\"dateUpdate\":\"2024-11-07\",\"clientId\":12,\"banqueId\":1}";

        // Compare expected JSON with the actual serialized JSON
        Assert.assertEquals(expectedJson, compteToJson);
    }
}
