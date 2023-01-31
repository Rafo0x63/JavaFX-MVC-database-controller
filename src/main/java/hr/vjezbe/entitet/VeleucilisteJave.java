package hr.vjezbe.entitet;

import hr.vjezbe.iznimke.NemoguceOdreditiProsjekStudentaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Veleuciliste nasljeduje klasu ObrazovnaUstanova, pa tako ima svoje metode za izracunavanje ocjena i najboljih studenata
 */
public class VeleucilisteJave extends ObrazovnaUstanova implements Visokoskolska {

    private static final Logger logger = LoggerFactory.getLogger(VeleucilisteJave.class);
    public VeleucilisteJave(String naziv, ArrayList<Predmet> predmeti, ArrayList<Profesor> profesori, ArrayList<Student> studenti, ArrayList<Ispit> ispiti) {
        super(naziv, predmeti, profesori, studenti, ispiti);
    }

    /**
     *
     * @param ispiti - ispiti kojima je student pristupio
     * @param ocjenaPismenog - ocjena zavrsnog rada studenta
     * @param ocjenaObrane - ocjena obrane
     * @return BigDecimal - vraca konacnu ocjenu studenta na studiju
     */
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(ArrayList<Ispit> ispiti, int ocjenaPismenog, int ocjenaObrane) {
        float prosjekOcjena = 0;
        for (int i = 0; i < ispiti.size(); i++){
            prosjekOcjena += ispiti.get(i).getOcjena();
        }
        prosjekOcjena /= ispiti.size();
        float ocjena = (2 * prosjekOcjena + ocjenaPismenog + ocjenaObrane) / 4;
        BigDecimal konacnaOcjena = new BigDecimal(String.valueOf(ocjena));

        return konacnaOcjena;
    }

    /**
     *
     * @param godina - godina za koju se odreduje najbolji student
     * @return vraca najboljeg studenta na godini
     */
    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(int godina) {
        ArrayList<Ispit> ispiti = this.getIspiti();
        ArrayList<Student> studenti = this.getStudenti();

        ArrayList<Ispit> ispitiNaGodini = new ArrayList<Ispit>();
        ArrayList<Student> studentiNaGodini = new ArrayList<Student>();

        for (int i = 0; i < ispiti.size(); i++){
            if (ispiti.get(i).getDatumIVrijeme().getYear() == godina){
                ispitiNaGodini.add(ispiti.get(i));
                studentiNaGodini.add(ispiti.get(i).getStudent());
            }
        }
        Student najStudent = studentiNaGodini.get(0);
        BigDecimal najProsjek = null;
        try {
            najProsjek = odrediProsjekOcjenaNaIspitima(filtrirajIspitePoStudentu(ispitiNaGodini, najStudent));
        } catch (NemoguceOdreditiProsjekStudentaException e) {
            System.out.println(e.getMessage());
            logger.error("Student ima nedovoljnu ocjenu na ispitu", e);
            najProsjek = BigDecimal.ZERO;
        }
        for (int i = 1; i < studentiNaGodini.size(); i++){
            BigDecimal prosjek = null;
            try {
                prosjek = odrediProsjekOcjenaNaIspitima(filtrirajIspitePoStudentu(ispitiNaGodini, studentiNaGodini.get(i)));
            } catch (NemoguceOdreditiProsjekStudentaException e) {
                System.out.println(e.getMessage());
                logger.error("Student ima nedovoljnu ocjenu na ispitu", e);
                prosjek = BigDecimal.ZERO;
            }
            if (prosjek.compareTo(najProsjek) == 1 || prosjek.compareTo(najProsjek) == 0){
                najStudent = studentiNaGodini.get(i);
                najProsjek = prosjek;
            }
        }

        return najStudent;
    }
}
