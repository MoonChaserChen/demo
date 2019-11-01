package ink.akira.demoSpring.exception;

public class ExceptionThrow {
    public void method1() throws FirstException {
        throw new FirstException("First Exception");
    }

    public void method2() throws SecondException {
        try {
            method1();
        } catch (FirstException e) {
            throw new SecondException("Second Exception", e);
        }
    }

    public void method3() throws ThirdException {
        try {
            method2();
        } catch (SecondException e) {
            throw new ThirdException("Third Exception", e);
        }
    }

    public static void main(String[] args) {
        try {
            new ExceptionThrow().method3();
        } catch (ThirdException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getRootCause());
            System.out.println(e.contains(SecondException.class));
            e.printStackTrace();
        }
    }
}
