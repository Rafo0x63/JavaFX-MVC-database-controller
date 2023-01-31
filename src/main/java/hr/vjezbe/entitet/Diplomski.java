package hr.vjezbe.entitet;

//DEPRECATED - do not use or change
//TODO: check for usages and remove

/**
 *  Interface za Diplomski studij
 *  Sadrzi metodu za odredivanje studenta za Rektorovu nagradu
 */
public interface Diplomski extends Visokoskolska{

    /**
     *
     * @return Student - vraca objekt tipa Student koji je osvojio Rektorovu nagradu
     */
    public Student odrediStudentaZaRektorovuNagradu();
}
