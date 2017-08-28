package cn.yesway.demo.privateprotocol;

/**
 * @author Administrator
 * @date 2017/8/21 14:12
 * @desc
 */
class Student{
      private  String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
public class CommonTest {


    public static void main(String[] args) {
        Student aaa = new Student();
        doStudent( aaa);
        System.out.println(aaa.getName());
        System.out.println(1>>0);
    }

    private static void doStudent(final  Student  aaa) {
        Student bbb =new Student();
        bbb.setAge(15);
        bbb.setName("sadsa");
    }

}
