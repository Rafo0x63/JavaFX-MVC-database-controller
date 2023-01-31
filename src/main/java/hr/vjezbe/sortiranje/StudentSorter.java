package hr.vjezbe.sortiranje;

import hr.vjezbe.entitet.Student;

import java.util.Comparator;

public class StudentSorter implements Comparator<Student> {


    @Override
    public int compare(Student s1, Student s2) {
        if (s1.getPrezime().compareTo(s2.getPrezime()) > 0) {
            return 1;
        } else if (s1.getPrezime().compareTo(s2.getPrezime()) < 0) {
            return -1;
        } else if (s1.getPrezime().equals(s2.getPrezime())) {
            if (s1.getIme().compareTo(s2.getIme()) > 0) {
                return 1;
            } else return -1;
        } else return 0;
    }
}


