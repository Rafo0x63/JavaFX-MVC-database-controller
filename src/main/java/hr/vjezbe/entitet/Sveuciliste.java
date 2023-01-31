package hr.vjezbe.entitet;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Sveuciliste<T extends ObrazovnaUstanova> implements Serializable {

    private List<T> lista;

    public Sveuciliste(){
        this.lista = new ArrayList<T>();
    }

    public void dodajObrazovnuUstanovu(T objekt){
        this.lista.add(objekt);
    }

    public T dohvatiObrazovnuUstanovu(int index){
        return lista.get(index);
    }

    public List<T> dohvatiListu(){
        return this.lista;
    }
}
