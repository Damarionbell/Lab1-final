import com.google.gson.*;
import java.io.*;
import java.util.*;

public class SimpleDatabase {
    private HashMap<String, List<HashMap<String, Object>>> storage;

    // Constructor to initialize storage
    public SimpleDatabase() {
        this.storage = new HashMap<>();
    }

    // Insert a record into a table
    public String insert(String table, HashMap<String, Object> record) {
        storage.putIfAbsent(table, new ArrayList<>());
        storage.get(table).add(record);
        return "Record added to " + table;
    }

    // Display all records (for testing)
    public void displayTable(String table) {
        System.out.println("Table " + table + ": " + storage.get(table));
    }

    // Save the database state to a JSON file
    public void saveToFile(String fileName) {
        try (Writer writer = new FileWriter(fileName)) {
            Gson gson = new Gson();
            gson.toJson(storage, writer); // Serialize and save storage
            System.out.println("Database saved to " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving database: " + e.getMessage());
        }
    }

    // Load the database state from a JSON file
    public void loadFromFile(String fileName) {
        try (Reader reader = new FileReader(fileName)) {
            Gson gson = new Gson();
            storage = gson.fromJson(reader, HashMap.class); // Deserialize and load storage
            System.out.println("Database loaded from " + fileName);
        } catch (IOException e) {
            System.err.println("Error loading database: " + e.getMessage());
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        SimpleDatabase db = new SimpleDatabase();

        // Create a new record
        HashMap<String, Object> record1 = new HashMap<>();
        record1.put("id", 1);
        record1.put("name", "Alice");

        // Insert into "students" table
        db.insert("students", record1);

        // Display table data
        db.displayTable("students");

        // Save database to file
        db.saveToFile("database.json");

        // Create a new database instance and load from file
        SimpleDatabase newDb = new SimpleDatabase();
        newDb.loadFromFile("database.json");

        // Display loaded table data
        newDb.displayTable("students");
    }
}
