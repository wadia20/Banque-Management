import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.banque.classes.Client;




import org.junit.Assert;
import org.junit.Test;

public class ClientTest {

    @Test
    public void testJsonSerialization() {
        Client client1 = new Client(1, "John", "Doe", "john.doe@example.com");

        String clientToJson = client1.toJson();

        // Adjusted JSON string to match the actual field names
        String expectedJson = "{\"clientId\":1,\"nom\":\"John\",\"prenom\":\"Doe\",\"email\":\"john.doe@example.com\"}";

        Assert.assertEquals(expectedJson, clientToJson);
    }
}