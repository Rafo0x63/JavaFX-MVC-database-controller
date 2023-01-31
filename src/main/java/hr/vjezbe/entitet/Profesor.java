package hr.vjezbe.entitet;

/**
 * Profesor nasljeduje klasu Osoba
 * Takoder sadrzi podatke o sifri profesora te njegovoj tituli
 */
public class Profesor extends Osoba {
    private Long id;
    private String sifra;

    private String titula;

    private boolean nositelj = false;

    /**
     *
     * @param sifra - sifra profesora
     * @param ime - ime profesora
     * @param prezime - prezime profesora
     * @param titula - titula profesora
     */
    public Profesor(Long id, String sifra, String ime, String prezime, String titula) {
        super(ime, prezime);
        this.id = id;
        this.sifra = sifra;
        this.titula = titula;
    }

    public Long getId(){
        return id;
    }
    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getIme() {
        return super.getIme();
    }



    public String getPrezime() {
        return super.getPrezime();
    }



    public String getTitula() {
        return titula;
    }

    public void setTitula(String titula) {
        this.titula = titula;
    }

    public void setNositelj(boolean input){
        if (input){
            this.nositelj = true;
        } else this.nositelj = false;
    }

    public boolean getNositelj(){
        return this.nositelj;
    }

    @Override
    public String toString() {
        return getIme() + " " + getPrezime();
    }
}
