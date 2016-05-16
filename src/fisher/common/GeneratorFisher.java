package fisher.common;

import java.util.*;

/**
 * Created by Tomasz Jodko on 2016-05-16.
 */
public class GeneratorFisher {


    private SystemDecyzyjny sd;
    private List<ArrayList<Obiekt>> koncepty = new ArrayList<>();
    private Hashtable<Integer, Hashtable<Integer,Double>> esy;
    private List<Integer> najlepsze = new ArrayList<>();

    public GeneratorFisher(){

    }

    public SystemDecyzyjny getSd() {
        return sd;
    }

    public void setSd(SystemDecyzyjny sd) {
        this.sd = sd;
    }

    public void generuj(){
        this.generujKoncepty();
        Hashtable<Integer, Hashtable<Integer,Double>> result = new Hashtable<Integer, Hashtable<Integer, Double>>();
        //przejscie po klasach decyzyjnych
        for(int i = 0; i < this.koncepty.size(); i++){
            int klasa = this.koncepty.get(i).get(0).getKlasaDecyzyjna();
            Hashtable<Integer, Double> abc = new Hashtable<>();
            //przejscie po atrybutach
            for(int j = 0; j < this.sd.getIloscAtrybutow(); j++){
                Double cKreska = liczCkreska(i,j);
                Double cDaszek = liczCdaszek(i,j);
                Double zKreska = liczZkreska(i,j,cKreska);
                Double zDaszek = liczZdaszek(i,j,cDaszek);
                Double s = liczS(cKreska,cDaszek,zKreska,zDaszek);
                abc.put(j,s);
            }
            result.put(klasa,abc);
        }
    this.esy = result;
    }

    private Double liczCkreska(int nrKonceptu, int nrAtrybutu){
        Double cKreska = 0.00;
        for(int i = 0; i<this.koncepty.get(nrKonceptu).size(); i++){
            cKreska += this.koncepty.get(nrKonceptu).get(i).getAtrybuty().get(nrAtrybutu);
        }
        return cKreska/(double) this.koncepty.get(nrKonceptu).size();
    }

    private Double liczCdaszek(int nrKonceptu, int nrAtrybutu){
        Double cDaszek = 0.00;
        Double licznik=0.00;
        for (int i = 0; i<this.koncepty.size();i++){
            if(i != nrKonceptu){
                for(int j = 0; j<this.koncepty.get(i).size(); j++){
                    cDaszek += this.koncepty.get(i).get(j).getAtrybuty().get(nrAtrybutu);
                    licznik++;
                }
            }
        }
        return cDaszek/licznik;
    }

    private Double liczZkreska(int nrKonceptu, int nrAtrybutu, Double cKreska){
        Double zKreska = 0.00;
        for(int i = 0; i<this.koncepty.get(nrKonceptu).size(); i++){
            zKreska += Math.pow((double) this.koncepty.get(nrKonceptu).get(i).getAtrybuty().get(nrAtrybutu) - cKreska,2);
        }
        return zKreska/this.koncepty.get(nrKonceptu).size();
    }

    private Double liczZdaszek(int nrKonceptu, int nrAtrybutu, Double cDaszek){
        Double zDaszek = 0.00;
        Double licznik=0.00;
        for (int i = 0; i<this.koncepty.size();i++){
            if(i != nrKonceptu){
                for(int j = 0; j<this.koncepty.get(i).size(); j++){
                    zDaszek += Math.pow((double) this.koncepty.get(i).get(j).getAtrybuty().get(nrAtrybutu) - cDaszek,2);
                    licznik++;
                }
            }
        }
        return zDaszek/licznik;
    }

    private Double liczS(Double cKreska, Double cDaszek, Double zKreska, Double zDaszek){
        return Math.pow(cKreska - cDaszek,2)/(zKreska + zDaszek);
    }

    private void generujKoncepty(){
        Set<Integer> unikalne = new HashSet<Integer>();
        for (int i = 0; i < this.sd.getObiekty().size(); i++) {
            unikalne.add(this.sd.getObiekty().get(i).getKlasaDecyzyjna());
        }
        List<Integer> klasyDecyzyjne = new ArrayList<Integer>(unikalne);

        for (int i = 0; i < klasyDecyzyjne.size(); i++) {
            ArrayList<Obiekt> koncept = new ArrayList<Obiekt>();
            for (int j = 0; j < this.sd.getObiekty().size(); j++) {
                if(this.sd.getObiekty().get(j).getKlasaDecyzyjna() == klasyDecyzyjne.get(i)){
                    koncept.add(this.sd.getObiekty().get(j));
                }
            }
            this.koncepty.add(koncept);

        }

    }

    private String sortuj(Hashtable<Integer,Double> sep){
        String result = "";
        List<Double> abc = new ArrayList<>(sep.values());

        Collections.sort(abc);
        Collections.reverse(abc);
        for ( Double value : abc ) {
            for(Map.Entry entry: sep.entrySet()){
                if(value.equals(entry.getValue())){
                    result += "a" + ((Integer) entry.getKey()+1) + " ";
                    break;
                }
            }
        }
        return result;
    }

    private void wybierzNajlepszeAtrybuty(List<Integer> klasyDecyzyjne){
            int licznik=0;
            int licznik2=0;
            while(najlepsze.size() != klasyDecyzyjne.size()){
                List<Double> abc = new ArrayList<>(esy.get(klasyDecyzyjne.get(licznik)).values());
                Collections.sort(abc);
                Collections.reverse(abc);
                List<Integer> posortowane = new ArrayList<>();
                for ( Double value : abc ) {
                    for(Map.Entry entry: esy.get(klasyDecyzyjne.get(licznik)).entrySet()){
                        if(value.equals(entry.getValue())){
                            posortowane.add((Integer) entry.getKey());
                            break;
                        }
                    }
                }
                if(!najlepsze.contains(posortowane.get(licznik2))){
                    najlepsze.add(posortowane.get(licznik2));
                }
                licznik++;
                if(licznik%klasyDecyzyjne.size() == 0){
                    licznik2++;
                    licznik=0;
                }

            }
    }

    @Override
    public String toString(){
        String result = "";
        Set<Integer> unikalne = new HashSet<Integer>();
        for (int i = 0; i < this.sd.getObiekty().size(); i++) {
            unikalne.add(this.sd.getObiekty().get(i).getKlasaDecyzyjna());
        }
        List<Integer> klasyDecyzyjne = new ArrayList<Integer>(unikalne);

        for (int i = 0; i < klasyDecyzyjne.size() ; i++){
            result+= "c"+klasyDecyzyjne.get(i)+": "+sortuj(esy.get(klasyDecyzyjne.get(i)));
            result+= "\n";
        }

        wybierzNajlepszeAtrybuty(klasyDecyzyjne);
        result+=this.sd.toStringPoSelekcji(this.najlepsze);

        return result;
    }

}
