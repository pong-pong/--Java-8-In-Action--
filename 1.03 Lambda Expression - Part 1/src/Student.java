package lambda.part1;

public class Student {

    private String name;

    private Integer grade;

    private String major;

    private Integer stdNo;

    public Student(String name, Integer grade, String major, Integer stdNo) {
        this.name = name;
        this.grade = grade;
        this.major = major;
        this.stdNo = stdNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public Integer getStdNo() {
        return stdNo;
    }

    public void setStdNo(Integer stdNo) {
        this.stdNo = stdNo;
    }
}
