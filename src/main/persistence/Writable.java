package persistence;

import org.json.JSONObject;

// Writable interface has an abstract method for other classes to implement
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

