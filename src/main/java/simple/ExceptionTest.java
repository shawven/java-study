public class ExceptionTest {


    public static void main(String[] args) {

        try {
            if (throwsExcpetion(0) == 0) {
                System.out.println("ok!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Integer throwsExcpetion(Integer i) throws Exception {
        if (i == 0) {
            throw new RuntimeException("i = 0");
        }
        return i;
    }
}
