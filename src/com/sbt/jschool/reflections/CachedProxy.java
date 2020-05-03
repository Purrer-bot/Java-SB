package com.sbt.jschool.reflections;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

//Example classes
public class CachedProxy implements CachedProxyInterface {
    /*
    * Task 3.4 Cached Proxy
    * */
    @Override
    public int sum(int a, int b) {
        return a + b;
    }

    @Override
    public String concat(String a, String b) {
        return a + b;
    }

    @Override
    public void print() {
        System.out.println("Yay");
    }

    public static void main(String[] args) {
        CachedProxy cp = new CachedProxy();
        CachedProxyInterface p = (CachedProxyInterface) Proxy.newProxyInstance(
                CachedProxy.class.getClassLoader(),
                new Class[]{CachedProxyInterface.class},
                new CachedInvocationHandler(cp)
        );
//        p.print();
        p.sum(1, 2);
        System.out.println(p.sum(1, 2));
    }
}

interface CachedProxyInterface {
    int sum(int a, int b);

    String concat(String a, String b);

    void print();
}

//Cache logic starts here
class CachedInvocationHandler implements InvocationHandler {
    private Object target;
    private HashMap<MethodsCacher, Object> cacheMap;

    CachedInvocationHandler(Object target) {
        this.target = target;
        this.cacheMap = new HashMap<>();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final MethodsCacher cache = new MethodsCacher(method, args);
        if (cacheMap.containsKey(cache)) {
            System.out.println("Loaded from cache");
            return cacheMap.get(cache);
        }
        System.out.println("Added to cache");
        Object result = method.invoke(target, args);
        cacheMap.put(cache, result);
        return result;
    }

}

//Using helper class for better caching
class MethodsCacher {
    private final Method method;
    private final Object[] args;

    MethodsCacher(Method m, Object[] args) {
        this.method = m;
        this.args = args;
    }

    public String getMethodName() {
        return method.getName();
    }

    public Object[] getArgs() {
        return args;
    }

    @Override
    public int hashCode() {
        int hash = method.getName().hashCode();
        for (Object arg : args) {
            hash += arg.hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        MethodsCacher sec = (MethodsCacher) obj;
        String secMethodName = sec.getMethodName();
        Object[] secArgs = sec.getArgs();

        if (!this.getMethodName().equals(secMethodName)) return false;
        if (this.args.length != secArgs.length) return false;
        for (int i = 0; i < args.length; i++) {
            if (args[i] != secArgs[i]) return false;
        }
        return true;
    }
}
