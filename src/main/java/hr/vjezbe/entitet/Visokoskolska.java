package hr.vjezbe.entitet;

import hr.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;

import java.math.BigDecimal;
import java.util.ArrayList;

//DEPRECATED - do not use or change
//TODO: check for usages and remove

/**
 * Interface za visoke skole
 * Sadrzi metode za racunanje prosjeka ocjena, konacne ocjene i filtriranje ispita po studentu
 */
public interface Visokoskolska {

    /**
     *
     * @param ispiti - ispiti kojima je student pristupio
     * @param ocjenaPismenog - ocjena pismenog dijela ispita
     * @param ocjenaObrane - ocjena obrane
     * @return BigDecimal - vraca konacnu ocjenu studenta
     */
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(ArrayList<Ispit> ispiti,
                                                              int ocjenaPismenog,
                                                              int ocjenaObrane);

    /**
     *
     * @param ispiti - ispiti kojima je student pristupio
     * @return BigDecimal - vraca prosjek ocjena na ispitima
     * @throws NemoguceOdreditiProsjekStudentaException baca iznimku kada student ima nedovoljnu ocjenu na barem jednom
     * od ispita, te mu je nemoguce odrediti prosjek
     */
    default BigDecimal odrediProsjekOcjenaNaIspitima(ArrayList<Ispit> ispiti) throws NemoguceOdreditiProsjekStudentaException {
        BigDecimal avg = new BigDecimal("0");
        int counter = 0;
        for (int i = 0; i < ispiti.size(); i++){
            if (ispiti.get(i).getOcjena() == 1){
                throw new NemoguceOdreditiProsjekStudentaException("Student " + ispiti.get(i).getStudent().getIme() + " " + ispiti.get(i).getStudent().getPrezime() + " je na ispitu iz predmeta " + ispiti.get(i).getPredmet().getNaziv() + " dobio nedovoljnu ocjenu i za kraj ima ocjenu nedovaljan(1)!");
            } else {
                avg.add(BigDecimal.valueOf(ispiti.get(i).getOcjena()));
                counter++;
            }
        }
        return avg.divide(BigDecimal.valueOf(counter));

    }

    /**
     *
     * @param ispiti - ispiti studenta
     * @return Ispit[] - vraca listu ispita koje je student polozio
     */
    private  Ispit[] filtriralPolozeneIspite(Ispit[] ispiti){
        int counter = 0;
        for (int i = 0; i < ispiti.length; i++){
            if (ispiti[i].getOcjena() > 1)
                counter++;
        }
        Ispit[] filtriraniIspiti = new Ispit[counter];
        int pos = 0;
        for (int i = 0; i < ispiti.length; i++){
            if (ispiti[i].getOcjena() > 1)
                filtriraniIspiti[pos++] = ispiti[i];
        }
        return filtriraniIspiti;
    }

    /**
     *
     * @param ispiti - ispiti na ustanovi
     * @param student - student za kojeg se filtrijaju ispiti
     * @return Ispit[] - vraca listu ispita za zadanog studenta
     */
    default ArrayList<Ispit> filtrirajIspitePoStudentu(ArrayList<Ispit> ispiti, Student student){

        ArrayList<Ispit> filtriraniIspiti = new ArrayList<Ispit>();

        ispiti.forEach((i)->{
            if(i.getStudent() == student) filtriraniIspiti.add(i);
        });

        return filtriraniIspiti;
    }

}
