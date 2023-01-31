package hr.vjezbe.entitet;

import hr.vjezbe.iznimke.PostojiViseNajmladihStudenataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 *  Fakultet Racunarstva, objekt Obrazovne ustanove, sadrzi metode za racunanje uspjeha studenata na studiju
 */
public class FakultetRacunarstva extends ObrazovnaUstanova implements Diplomski {

    private static final Logger logger = LoggerFactory.getLogger(VeleucilisteJave.class);

    /**
     *
     * @param naziv - naziv ustanove
     * @param predmeti - predmeti koji se pohadaju
     * @param profesori - profesori koji drze nastavu
     * @param studenti - studenti koji pohadaju studij
     * @param ispiti - ispiti koji se odrzavaju na studiju
     */
    public FakultetRacunarstva(String naziv, ArrayList<Predmet> predmeti, ArrayList<Profesor> profesori, ArrayList<Student> studenti, ArrayList<Ispit> ispiti) {
        super(naziv, predmeti, profesori, studenti, ispiti);
    }

    /**
     *
     * @param ispiti - ispiti na koje je student izasao
     * @param ocjenaDiplomskog - ocjena diplomskog rada studenta
     * @param ocjenaObraneDiplomskog - ocjena obrane
     * @return BigDecimal - vraca konacnu ocjenu studija koju je student ostvario
     */
    @Override
    public BigDecimal izracunajKonacnuOcjenuStudijaZaStudenta(ArrayList<Ispit> ispiti, int ocjenaDiplomskog, int ocjenaObraneDiplomskog) {
        float prosjekOcjena = 0;
        for (int i = 0; i < ispiti.size(); i++) {
            prosjekOcjena += ispiti.get(i).getOcjena();
        }
        prosjekOcjena /= ispiti.size();
        float ocjena = (3 * prosjekOcjena + ocjenaDiplomskog + ocjenaObraneDiplomskog) / 5;
        BigDecimal konacnaOcjena = new BigDecimal(String.valueOf(ocjena));

        return konacnaOcjena;
    }

    /**
     *
     * @param godina - godina na kojoj se odreduje najuspjesniji student
     * @return Student - vraca objekt tipa Student koji je najuspjesniji na godini
     */
    @Override
    public Student odrediNajuspjesnijegStudentaNaGodini(int godina) {
        ArrayList<Ispit> ispiti = this.getIspiti();
        ArrayList<Student> studenti = this.getStudenti();

        ArrayList<Ispit> ispitiNaGodini = new ArrayList<Ispit>();
        ArrayList<Student> studentiNaGodini = new ArrayList<Student>();

        for (int i = 0; i < ispiti.size(); i++) {
            if (ispiti.get(i).getDatumIVrijeme().getYear() == godina) {
                ispitiNaGodini.add(ispiti.get(i));
                studentiNaGodini.add(ispiti.get(i).getStudent());
            }
        }
        Student najStudent = studentiNaGodini.get(0);
        int najOcjene = -1;
        for (int i = 0; i < studentiNaGodini.size(); i++) {
            int izvrsneOcjene = 0;
            ArrayList<Ispit> ispitiStudenta = filtrirajIspitePoStudentu(ispitiNaGodini, studentiNaGodini.get(i));
            for (int j = 0; j < ispitiStudenta.size(); j++) {
                if (ispitiStudenta.get(j).getOcjena() == 5)
                    izvrsneOcjene++;
            }
            if (izvrsneOcjene > najOcjene) {
                najOcjene = izvrsneOcjene;
                najStudent = studentiNaGodini.get(i);
            }

        }
        return najStudent;

    }

    /**
     *
     * @return vraca studenta koji je osvojio Rektorovu nagradu
     */
    @Override
    public Student odrediStudentaZaRektorovuNagradu() {
        ArrayList<Student> studenti = this.getStudenti();
        ArrayList<Ispit> ispiti = this.getIspiti();

        Student najStudent = studenti.get(0);
        float najProsjek = -1;
        for (int i = 0; i < studenti.size(); i++) {
            ArrayList<Ispit> ispitiStudenta = filtrirajIspitePoStudentu(ispiti, studenti.get(i));
            float prosjek = 0;
            for (int j = 0; j < ispitiStudenta.size(); j++) {
                prosjek += ispitiStudenta.get(j).getOcjena();
            }
            prosjek /= ispitiStudenta.size();
            if (prosjek > najProsjek) {
                najProsjek = prosjek;
                najStudent = studenti.get(i);
            } else if (prosjek == najProsjek) {
                try {
                    if (mladjiStudent(studenti.get(i), najStudent)){
                        najStudent = studenti.get(i);
                    }
                } catch (PostojiViseNajmladihStudenataException e) {
                    System.out.println("Pronadeno je vise najmladih studenata s istim datumom rodenja, a to su " + studenti.get(i).getIme() + " " + studenti.get(i).getPrezime() + " i " + najStudent.getIme() + " " + najStudent.getPrezime());
                    logger.error("Postoji vise najmladih studenata s istim datumom rodenja", e);
                    najStudent = null;
                }
            }
        }
        return najStudent;
    }

    /**
     * Metoda provjerava koji je student mladji
     * @param student - student kojeg provjeravamo
     * @param najStudent - trenutni najbolji student
     * @return vraca true ako je student mladi ili false ako je najbolji student mladi
     * @throws PostojiViseNajmladihStudenataException baca iznimku ako su studenti rodeni na isti dan
     */
    private boolean mladjiStudent(Student student, Student najStudent) throws PostojiViseNajmladihStudenataException {

        try {
            if (student.getDatumRodjenja().isEqual(najStudent.getDatumRodjenja())){
                throw new PostojiViseNajmladihStudenataException("Pronadeno je vise najmladih studenata");
            } else if (student.getDatumRodjenja().isAfter(najStudent.getDatumRodjenja())){
                return true;
            } else return false;
        } catch (PostojiViseNajmladihStudenataException ex) {
            throw new PostojiViseNajmladihStudenataException(ex.getMessage());
        }

    }
}

