package hr.vjezbe.entitet;

public enum Ocjena {
    NEDOVOLJAN(1, "nedovoljan"),
    DOVOLJAN(2, "dovoljan"),
    DOBAR(3, "dobar"),
    VRLO_DOBAR(4, "vrlo dobar"),
    ODLICAN(5, "odlican");

    private int number;
    private String opis;
    Ocjena (int number, String opis) {
        this.number = number;
        this.opis = opis;
    }

    public int getNumber(){
        return number;
    }
}
