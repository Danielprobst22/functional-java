package dapg.examples.domainmodelling;

import dapg.control.option.Option;

import java.util.Map;

//interface Pr<SelfT, T2 extends SelfT>{}
//interface Pr<SelfT, T1 super SelfT>{}

//public class JStructTest<T1, T2, T3, SelfT extends Pr<SelfT>> {
public class JStructTest {

    public static abstract class JStruct {
        // protected Map<AliasKey<?>, Object> unsafeValues();

        // todo doesn't work because Java is...
//        static <T1, T2, T3 extends T1 & T2> void aha(Option<? >) {
//
//        }
    }

//    interface HasFoo extends JStruct{}
}
