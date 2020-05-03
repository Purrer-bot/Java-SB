import com.sbt.jschool.generics.LinkedList;

import java.util.ArrayList;
import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        LinkedList<? super Number> l = new LinkedList<>();
        Collection<Integer> c = new ArrayList<>();
        l.add(0);
        l.add(0);
        l.out();
        c.add(3);
        l.copy(c);
        l.out();

//        l.add(2);
//        l.add(4);
//        l.add(5);
//        l.add(3, 1);



//        l.out();
//        int elem = l.get(3);
//        System.out.println(elem);
//        l.remove(3);
//        l.out();
//        System.out.println("====");
//        System.out.println(l.get(1));
//        System.out.println(l.get(2));
//        Iterator<Integer> iter = new Iterator<>(l);
//        System.out.println(iter.next());
//        System.out.println(iter.hasNext());
//        System.out.println(iter.next());
//        System.out.println(iter.next());
//        System.out.println(iter.hasNext());
//        //exception goes here
//        System.out.println(iter.next());
    }
}
