import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Classes {
    public static void main(String[] args) throws Exception {
        long j = 0;
        List<ClassLoader> classLoaders = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            if ((i + 1) % 1000 == 0) {
                System.out.println(i + 1);
            }
            String fictiousClassloaderJAR = "file:" + j++ + ".jar";
            URL[] fictiousClassloaderURL = new URL[]{new URL(fictiousClassloaderJAR)};
            URLClassLoader newClassLoader = new URLClassLoader(fictiousClassloaderURL);
            classLoaders.add(newClassLoader);

            ClassToOptimize classToOptimize = (ClassToOptimize) Proxy.newProxyInstance(newClassLoader,
                    new Class<?>[]{ClassToOptimize.class},
                    new ClassToOptimizeInvocationHandler(new ClassToOptimizeImpl()));
        }
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
    }

    public interface ClassToOptimize {
        void run();
    }

    static public class ClassToOptimizeImpl implements ClassToOptimize {
        public void run() {
            for (int i = 0; i < 1000; i++) {
                new Object();
                doSomething(1 + (i % 10));
            }
        }

        private void doSomething(int o) {
            int acc = 0;
            for (int i = 0; i < o; i++) {
                acc = add(acc, i);
            }
        }

        private int add(int acc, int i) {
            return acc + sqrt(i);
        }

        private int sqrt(int i) {
            return (int) Math.sqrt(i);
        }
    }

    public static class ClassToOptimizeInvocationHandler implements InvocationHandler {
        private Object impl;

        public ClassToOptimizeInvocationHandler(Object impl) {
            this.impl = impl;
        }

        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (Object.class == method.getDeclaringClass()) {
                String name = method.getName();
                if ("equals".equals(name)) {
                    return proxy == args[0];
                } else if ("hashCode".equals(name)) {
                    return System.identityHashCode(proxy);
                } else if ("toString".equals(name)) {
                    return proxy.getClass().getName() + "@" +
                            Integer.toHexString(System.identityHashCode(proxy)) +
                            ", with InvocationHandler " + this;
                } else {
                    throw new IllegalStateException(String.valueOf(method));
                }
            }

            return method.invoke(impl, args);
        }
    }
}
