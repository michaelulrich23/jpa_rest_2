
package rest;

import java.util.HashMap;

public class Slovnik {
    private HashMap<String, HashMap<String, String>> preklady = new HashMap<>();

    public HashMap<String, HashMap<String, String>> getPreklady() {
        return preklady;
    }

    public void setPreklady(String jazyk,HashMap<String, String> preklady) {
        this.preklady.put(jazyk, preklady);
    }
   
    
}