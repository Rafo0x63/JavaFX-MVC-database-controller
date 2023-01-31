package hr.vjezbe.entitet;

//DEPRECATED - do not use or change
//TODO: check for usages and remove

/**
 * interface koji se koristi kada se ispiti odrzavaju online, sadrzi podatke o software-u koji se koristi za online ispite
 */
public sealed interface Online permits Ispit{
    public String softverZaIspit(String nazivSoftvera);
}
