package com.student.app.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    @Id
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return phoneNumber.equals(teacher.phoneNumber) &&
                firstName.equals(teacher.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneNumber, firstName);
    }


}
