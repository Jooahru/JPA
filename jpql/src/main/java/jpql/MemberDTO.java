package jpql;

public class MemberDTO {
    private String userName;
    private int age;

    public MemberDTO(String username, int age) {
        this.userName = username;
        this.age = age;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
