package fisher.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Tomasz Jodko on 2016-05-16.
 */
public class SystemDecyzyjny {

    private List<Obiekt> obiekty = new ArrayList<Obiekt>();
    private int iloscAtrybutow;

    public SystemDecyzyjny(File selectedFile) {
        // odczyt pliku i generowanie listy obiektow
        try {
            Scanner sc = new Scanner(selectedFile);
            List<String> lines = new ArrayList<String>();
            while (sc.hasNextLine()) {
                lines.add(sc.nextLine().trim());
            }

            for (int i = 0; i < lines.size(); i++) {
                this.obiekty.add(new Obiekt(lines.get(i)));
            }
            sc.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.iloscAtrybutow = this.obiekty.get(0).getAtrybuty().size();
    }

    public List<Obiekt> getObiekty(){
        return this.obiekty;
    }

    public int getIloscAtrybutow(){
        return this.iloscAtrybutow;
    }


    @Override
    public String toString() {
        String wynik = " ";
        for (int i = 1; i <= this.iloscAtrybutow; i++) {
            wynik += "a" + i + " ";
        }
        wynik += " d\n";
        for (int i = 0; i < obiekty.size(); i++) {
            wynik += obiekty.get(i).toString() + "\n";
        }


        return wynik;
    }

    public String toStringPoSelekcji(List<Integer> atrybuty){
        String wynik = " ";
        for (int i = 0; i < this.iloscAtrybutow; i++) {
            if(atrybuty.contains(i)){
                wynik += "a" + (i+1) + " ";
            }

        }
        wynik += " d\n";
        for (int i = 0; i < obiekty.size(); i++) {
            wynik += obiekty.get(i).toStringPoSelekcji(atrybuty) + "\n";
        }


        return wynik;
    }

}