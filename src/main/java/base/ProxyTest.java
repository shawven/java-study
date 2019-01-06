import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyTest {
    public static void main(String[] args) {
        ISubject proxyInstance = (ISubject) new MyProxyTest(new Subject()).getProxyInstance();
        proxyInstance.say();
    }
}


interface ISubject {
    void say();
}

class Subject implements ISubject {

    @Override
    public void say() {
        System.out.println("hello world");
    }
}

abstract class MyProxy {

    private Object target;
    public MyProxy(Object target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        before();
                        Object invoke = method.invoke(target, args);
                        after();
                        return invoke;
                    }
                });
    }

    protected abstract void before();
    protected abstract void after();

}

class MyProxyTest extends MyProxy {

    public MyProxyTest(Object target) {
        super(target);
    }

    @Override
    protected void before() {
        System.out.println(111);
    }

    @Override
    protected void after() {
        System.out.println(222);
    }
}