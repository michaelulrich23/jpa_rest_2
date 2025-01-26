package rest;

import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("slovnik")
@Singleton
public class SlovnikResource {
    map<string, map<string, string>>
    private Slovnik preklady = new Slovnik();

    private void SetPreklady(String jazyk, HashMap<String, String> preklady) {
        for (Map.Entry<String, String> entry : preklady.entrySet()) {
            this.preklady.getPreklady().get(jazyk).put(entry.getKey(), entry.getValue());
        }
    }
    

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public int getPocetSlov() {
        return preklady.getPreklady().size();
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{word}")
    public String getNames(@PathParam("word") String word){
        if(!preklady.getPreklady().containsKey(word)){
            return "Nezname slovo";
        }
        String s = "";
        
        for(String str : preklady.getPreklady().get(word).keySet()){
            s = s + str;
        }
        
        return s;
    }

    @DELETE
    @Path("/{word}")
    public void delSlovo(@PathParam("word") String word) {
        if (preklady.getPreklady().containsKey(word)) {
            preklady.getPreklady().remove(word);
        }
    }
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/{word}/{lang}")
    public String getPreklad(@PathParam("word") String word, @PathParam("lang") String lang) {
        if (!preklady.getPreklady().containsKey(word)) {
            return "Nezname slovo";
        }
        if (!preklady.getPreklady().get(word).containsKey(lang)) {
            return "Preklad neexistuje";
        }
        return preklady.getPreklady().get(word).get(lang);
    }
    
    @PUT
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("/{word}/{lang}")
    public void addPreklad(@PathParam("word") String word, @PathParam("lang") String lang, String preklad) {
        HashMap<String, String> novy = new HashMap<>();
        novy.put(lang, preklad);
        if(!preklady.getPreklady().containsKey(word)){
            this.preklady.setPreklady(word, novy);
        } else if (!this.preklady.getPreklady().get(word).containsKey(lang)) {
            this.preklady.getPreklady().get(word).put(lang, preklad);
        } else {
            this.SetPreklady(word, novy);
        }
    }
    
    @DELETE
    @Path("/{word}/{lang}")
    public void delSlovoJazyk(@PathParam("word") String word, @PathParam("lang") String lang) {
        if (this.preklady.getPreklady().containsKey(word)) {
            if (this.preklady.getPreklady().get(word).containsKey(lang)) {
                this.preklady.getPreklady().get(word).remove(lang);
            }
        }
    }

}