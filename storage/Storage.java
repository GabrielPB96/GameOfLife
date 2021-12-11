package storage;
import java.util.ArrayList;
import java.io.*;


/**
 * Write a description of class Storage here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Storage implements Serializable{
    private ArrayList<Pattern> patterns;
    
    public Storage () {
        load();
        if (patterns == null) {
            patterns = new ArrayList<Pattern>();
        }
    }
    
    public void load() {
        //leer fichero
        Thread load = new Thread(new Runnable(){
            public void run(){
                try{
                    ObjectInputStream lector = new ObjectInputStream(new FileInputStream("./patterns/filePatterns.txt"));
                    patterns = (ArrayList<Pattern>)lector.readObject();
                    lector.close();
                }catch(Exception e) {
                    System.out.println("Error in load() -> " + e.getMessage());
                }
            }
        });
        load.start();
    }
    
    public void save(Pattern pattern) {
        patterns.add(pattern);
    }
    
    public void saveObject() {
        Thread load = new Thread(new Runnable(){
            public void run(){
                try{
                    ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream("./patterns/filePatterns.txt"));
                    escritor.writeObject(patterns);
                    escritor.close();
                }catch(Exception e) {
                    System.out.println("Error in saveObject() -> " + e.getMessage());
                }
            }
        });
        load.start();
    }
    
    public Pattern getPattern(int index) {
        return patterns.get(index);
    }
    
    public ArrayList<Pattern> getPatterns() {
        return patterns;
    }
}
