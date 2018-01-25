package irpbc.iteh.service.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * A DTO for the Course entity.
 */
public class StudentCourseDTO {

    private String name;
    private Integer espbPoints;
    private Integer grade;
    private Integer yearOfStudies;
    private String schoolYear;

    public StudentCourseDTO(String name, Integer espbPoints, Integer grade, Integer yearOfStudies, String schoolYear) {
        this.name = name;
        this.espbPoints = espbPoints;
        this.grade = grade;
        this.yearOfStudies = yearOfStudies;
        this.schoolYear = schoolYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getEspbPoints() {
        return espbPoints;
    }

    public void setEspbPoints(Integer espbPoints) {
        this.espbPoints = espbPoints;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getYearOfStudies() {
        return yearOfStudies;
    }

    public void setYearOfStudies(Integer yearOfStudies) {
        this.yearOfStudies = yearOfStudies;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    @Override
    public String toString() {
        return "StudentCourseDTO{" +
            "name='" + name + '\'' +
            ", espbPoints=" + espbPoints +
            ", grade=" + grade +
            ", yearOfStudies=" + yearOfStudies +
            ", schoolYear='" + schoolYear + '\'' +
            '}';
    }
}
