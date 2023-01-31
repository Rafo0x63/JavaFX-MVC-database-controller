package hr.vjezbe.baza;

import hr.vjezbe.entitet.*;
import hr.vjezbe.iznimke.BazaPodatakaException;
import javafx.fxml.FXML;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;



public class BazaPodataka {
    public static Connection spajanjeNaBazu() throws Exception {
        Properties konfiguracijaBaze = new Properties();
        konfiguracijaBaze.load(new FileInputStream("dat/baza.properties"));

        Connection veza = DriverManager.getConnection(
                konfiguracijaBaze.getProperty("bazaPodatakaURL"),
                konfiguracijaBaze.getProperty("korisnickoIme"),
                konfiguracijaBaze.getProperty("lozinka")
        );
        return veza;
    }
    public static List<Profesor> dohvatiProfesorePremaKriterijima(Profesor profesor) throws BazaPodatakaException {
        List<Profesor> listaProfesora = new ArrayList<>();
        try (Connection veza = spajanjeNaBazu()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * FROM PROFESOR WHERE 1 = 1");
            if (Optional.ofNullable(profesor).isEmpty() == false) {
                if (Optional.ofNullable(profesor).map(
                        Profesor::getId).isPresent()) {
                    sqlUpit.append(" AND ID = " + profesor.getId());
                }
                if (Optional.ofNullable(profesor.getSifra()).map(
                        String::isBlank).orElse(true) == false) {
                    sqlUpit.append(" AND SIFRA LIKE '%" +
                            profesor.getSifra() + "%'");
                }
                if (Optional.ofNullable(profesor.getIme()).map(
                        String::isBlank).orElse(true) == false) {
                    sqlUpit.append(" AND IME LIKE '%" +
                            profesor.getIme() + "%'");
                }
                if (Optional.ofNullable(profesor.getPrezime()).map(
                        String::isBlank).orElse(true) == false) {
                    sqlUpit.append(" AND PREZIME LIKE '%" +
                            profesor.getPrezime() + "%'");
                }
                if (Optional.ofNullable(profesor.getTitula()).map(String::isBlank).orElse(true) == false) {
                    sqlUpit.append(" AND TITULA LIKE '%" +
                            profesor.getTitula() + "%'");
                }
            }
            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String sifra = resultSet.getString("sifra");
                String ime = resultSet.getString("ime");
                String prezime = resultSet.getString("prezime");
                String titula = resultSet.getString("titula");
                Profesor noviProfesor = new Profesor(id, sifra, ime, prezime,
                        titula);
                listaProfesora.add(noviProfesor);
            }
        } catch (Exception ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, ex);
        }
        return listaProfesora;
    }
    public static void spremiNovogProfesora(Profesor profesor) throws
            BazaPodatakaException {
        try (Connection veza = spajanjeNaBazu()) {
            PreparedStatement preparedStatement = veza
                    .prepareStatement(
                            "INSERT INTO PROFESOR(ime, prezime, sifra, titula) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, profesor.getIme());
            preparedStatement.setString(2, profesor.getPrezime());
            preparedStatement.setString(3, profesor.getSifra());
            preparedStatement.setString(4, profesor.getTitula());
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, ex);
        }
    }

    public static List<Student> dohvatiStudentePremaKriterijima(Student student) throws BazaPodatakaException {
        List<Student> listaStudenata = new ArrayList<>();
        try (Connection veza = spajanjeNaBazu()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * FROM STUDENT WHERE 1 = 1");
            if (Optional.ofNullable(student).isEmpty() == false) {
                if (Optional.ofNullable(student).map(
                        Student::getId).isPresent()) {
                    sqlUpit.append(" AND ID = " + student.getId());
                }
                if (Optional.ofNullable(student.getJmbag()).map(
                        String::isBlank).orElse(true) == false) {
                    sqlUpit.append(" AND JMBAG LIKE '%" +
                            student.getJmbag() + "%'");
                }
                if (Optional.ofNullable(student.getIme()).map(
                        String::isBlank).orElse(true) == false) {
                    sqlUpit.append(" AND IME LIKE '%" +
                            student.getIme() + "%'");
                }
                if (Optional.ofNullable(student.getPrezime()).map(
                        String::isBlank).orElse(true) == false) {
                    sqlUpit.append(" AND PREZIME LIKE '%" +
                            student.getPrezime() + "%'");
                }
                if (Optional.ofNullable(student.getDatumRodjenja()).isPresent()) {
                    sqlUpit.append(" AND DATUM_RODJENJA = '" +
                            student.getDatumRodjenja().format(DateTimeFormatter.ISO_DATE) + "'");
                }
            }
            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String ime = resultSet.getString("ime");
                String prezime = resultSet.getString("prezime");
                String jmbag = resultSet.getString("jmbag");
                LocalDate datumRodjenja = resultSet.getTimestamp("datum_rodjenja").toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Student noviStudent = new Student(id, ime, prezime, jmbag, datumRodjenja);
                listaStudenata.add(noviStudent);
            }
        } catch (Exception ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, ex);
        }
        return listaStudenata;
    }
    public static void spremiNovogStudenta(Student student) throws
            BazaPodatakaException {
        try (Connection veza = spajanjeNaBazu()) {
            PreparedStatement preparedStatement = veza
                    .prepareStatement(
                            "INSERT INTO STUDENT(ime, prezime, jmbag, datum_rodjenja) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, student.getIme());
            preparedStatement.setString(2, student.getPrezime());
            preparedStatement.setString(3, student.getJmbag());
            preparedStatement.setDate(4, Date.valueOf(student.getDatumRodjenja()));
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, ex);
        }
    }

    public static List<Predmet> dohvatiPredmetePremaKriterijima(Predmet predmet) throws BazaPodatakaException {
        List<Predmet> listaPredmeta = new ArrayList<>();
        try (Connection veza = spajanjeNaBazu()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * FROM PREDMET WHERE 1 = 1");
            if (Optional.ofNullable(predmet).isEmpty() == false) {
                if (Optional.ofNullable(predmet).map(
                        Predmet::getId).isPresent()) {
                    sqlUpit.append(" AND ID = " + predmet.getId());
                }
                if (Optional.ofNullable(predmet.getSifra()).map(
                        String::isBlank).orElse(true) == false) {
                    sqlUpit.append(" AND SIFRA LIKE '%" +
                            predmet.getSifra() + "%'");
                }
                if (Optional.ofNullable(predmet.getNaziv()).map(
                        String::isBlank).orElse(true) == false) {
                    sqlUpit.append(" AND NAZIV LIKE '%" +
                            predmet.getNaziv() + "%'");
                }
                if (Optional.ofNullable(predmet.getBrojEctsBodova().toString()).map(
                        String::isBlank).orElse(true) == false) {
                    sqlUpit.append(" AND BROJ_ECTS_BODOVA LIKE '%" +
                            predmet.getBrojEctsBodova() + "%'");
                }
                if (Optional.ofNullable(predmet.getProfesorId()).isPresent()) {
                    sqlUpit.append(" AND PROFESOR_ID = '%" +
                    predmet.getProfesorId() + "%'");
                }
            }
            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String sifra = resultSet.getString("sifra");
                String naziv = resultSet.getString("naziv");
                Integer brojEctsBodova = resultSet.getInt("broj_ects_bodova");
                Long profesorId = resultSet.getLong("profesor_id");
                Predmet noviPredmet = new Predmet(id, sifra, naziv, brojEctsBodova, profesorId);
                listaPredmeta.add(noviPredmet);
            }
        } catch (Exception ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, ex);
        }
        return listaPredmeta;
    }
    public static void spremiNoviPredmet(Predmet predmet) throws
            BazaPodatakaException {
        try (Connection veza = spajanjeNaBazu()) {
            PreparedStatement preparedStatement = veza
                    .prepareStatement("INSERT INTO PREDMET(sifra, naziv, broj_ects_bodova, profesor_id) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, predmet.getSifra());
            preparedStatement.setString(2, predmet.getNaziv());
            preparedStatement.setInt(3, predmet.getBrojEctsBodova());
            preparedStatement.setLong(4, predmet.getProfesorId());
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, ex);
        }
    }

    public static List<Ispit> dohvatiIspitePremaKriterijima(Ispit ispit) throws BazaPodatakaException {
        List<Ispit> listaIspita = new ArrayList<>();
        try (Connection veza = spajanjeNaBazu()) {
            StringBuilder sqlUpit = new StringBuilder(
                    "SELECT * FROM ISPIT WHERE 1 = 1");
            if (Optional.ofNullable(ispit).isEmpty() == false) {
                if (Optional.ofNullable(ispit).map(
                        Ispit::getId).isPresent()) {
                    sqlUpit.append(" AND ID =" +
                            ispit.getId());
                }
                if (Optional.ofNullable(ispit.getPredmetId()).isEmpty() == false) {
                    sqlUpit.append(" AND PREDMET_ID =" +
                            ispit.getPredmetId());
                }
                if (Optional.ofNullable(ispit.getStudentId()).isEmpty() == false) {
                    sqlUpit.append(" AND STUDENT_ID =" +
                            ispit.getStudentId());
                }
                if (Optional.ofNullable(ispit.getOcjena()).isEmpty() == false) {
                    sqlUpit.append(" AND OCJENA =" +
                            ispit.getOcjena());
                }
                if (Optional.ofNullable(ispit.getDatumIVrijeme()).isPresent()) {
                    DateTimeFormatter formatter =
                            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SS");
                    sqlUpit.append(" AND datum_i_vrijeme = '" +
                            ispit.getDatumIVrijeme().format(formatter) + "'");
                }
            }
            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long predmetId = resultSet.getLong("predmet_id");
                Long studentId = resultSet.getLong("studnet_id");
                Integer ocjena = resultSet.getInt("ocjena");
                LocalDateTime datumVrijeme = resultSet.getTimestamp("datum_i_vrijeme").toLocalDateTime();
                Ocjena o = Ocjena.ODLICAN;
                switch(ocjena){
                    case 1:
                        o = Ocjena.NEDOVOLJAN;
                        break;
                    case 2:
                        o = Ocjena.DOVOLJAN;
                        break;
                    case 3:
                        o = Ocjena.DOBAR;
                        break;
                    case 4:
                        o = Ocjena.VRLO_DOBAR;
                        break;
                    case 5:
                        o = Ocjena.ODLICAN;
                }
                Ispit noviIspit = new Ispit(id, predmetId, studentId, o, datumVrijeme);
                listaIspita.add(noviIspit);
            }
        } catch (Exception ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, ex);
        }
        return listaIspita;
    }
    public static void spremiNoviIspit(Ispit ispit) throws
            BazaPodatakaException {
        try (Connection veza = spajanjeNaBazu()) {
            PreparedStatement preparedStatement = veza
                    .prepareStatement(
                            "INSERT INTO ISPIT(predmet_id, student_id, ocjena, datum_i_vrijeme) VALUES (?, ?, ?, ?)");
            preparedStatement.setLong(1, ispit.getPredmetId());
            preparedStatement.setLong(2, ispit.getStudentId());
            preparedStatement.setInt(3, ispit.getOcjena());
            preparedStatement.setTimestamp(4, Timestamp.valueOf(ispit.getDatumIVrijeme()));
            preparedStatement.executeUpdate();
        } catch (Exception ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, ex);
        }
    }

    public static List<Student> dohvatiSveStudente() throws BazaPodatakaException {
        List<Student> listaStudenata = new ArrayList<>();
        try (Connection veza = spajanjeNaBazu()){
            String sqlUpit = "SELECT * FROM STUDENT";
            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery(sqlUpit.toString());
            while (resultSet.next()){
                Long id = resultSet.getLong("id");
                String jmbag = resultSet.getString("jmbag");
                String ime = resultSet.getString("ime");
                String prezime = resultSet.getString("prezime");
                LocalDate datumRodjenja = resultSet.getTimestamp("datum_rodjenja").toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                Student noviStudent = new Student(id, ime, prezime, jmbag, datumRodjenja);
                listaStudenata.add(noviStudent);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listaStudenata;
    }
    public static List<Profesor> dohvatiSveProfesore() throws BazaPodatakaException {
        List<Profesor> listaProfesora = new ArrayList<>();
        try (Connection veza = spajanjeNaBazu()){
            String sqlUpit = "SELECT * FROM PROFESOR";
            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery(sqlUpit.toString());
            while (resultSet.next()){
                Long id = resultSet.getLong("id");
                String sifra = resultSet.getString("sifra");
                String ime = resultSet.getString("ime");
                String prezime = resultSet.getString("prezime");
                String titula = resultSet.getString("titula");
                Profesor noviProfesor = new Profesor(id, sifra, ime, prezime,
                        titula);
                listaProfesora.add(noviProfesor);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return listaProfesora;
    }


    public static List<Predmet> dohvatiSvePredmete() throws BazaPodatakaException {
        List<Predmet> listaPredmeta = new ArrayList<>();
        try (Connection veza = spajanjeNaBazu()) {
            String sqlUpit = "SELECT * FROM PREDMET";
            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String sifra = resultSet.getString("sifra");
                String naziv = resultSet.getString("naziv");
                Integer brojEctsBodova = resultSet.getInt("broj_ects_bodova");
                Long profesorId = resultSet.getLong("profesor_id");
                Predmet noviPredmet = new Predmet(id, sifra, naziv, brojEctsBodova, profesorId);
                listaPredmeta.add(noviPredmet);
            }
        } catch (Exception ex) {
            String poruka = "Došlo je do pogreške u radu s bazom podataka";
            throw new BazaPodatakaException(poruka, ex);
        }
        return listaPredmeta;
    }

    public static List<Ispit> dohvatiSveIspite() throws BazaPodatakaException {
        List<Ispit> listaIspita = new ArrayList<>();
        try (Connection veza = spajanjeNaBazu()){
            String sqlUpit = "SELECT * FROM ISPIT";
            Statement upit = veza.createStatement();
            ResultSet resultSet = upit.executeQuery(sqlUpit.toString());
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long predmetId = resultSet.getLong("predmet_id");
                Long studentId = resultSet.getLong("student_id");
                Integer ocjena = resultSet.getInt("ocjena");
                LocalDateTime datumVrijeme = resultSet.getTimestamp("datum_i_vrijeme").toLocalDateTime();
                Ocjena o = Ocjena.ODLICAN;
                switch(ocjena){
                    case 1:
                        o = Ocjena.NEDOVOLJAN;
                        break;
                    case 2:
                        o = Ocjena.DOVOLJAN;
                        break;
                    case 3:
                        o = Ocjena.DOBAR;
                        break;
                    case 4:
                        o = Ocjena.VRLO_DOBAR;
                        break;
                    case 5:
                        o = Ocjena.ODLICAN;
                }
                Ispit noviIspit = new Ispit(id, predmetId, studentId, o, datumVrijeme);
                listaIspita.add(noviIspit);

            }
    } catch (Exception ex) {
        String poruka = "Došlo je do pogreške u radu s bazom podataka";
        throw new BazaPodatakaException(poruka, ex);
    }
        return listaIspita;
    }
}
