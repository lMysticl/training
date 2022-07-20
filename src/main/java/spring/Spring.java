package spring;

/**
 * @author Pavel Putrenkov
 * @version 1.0
 * @since
 */
public class Spring {

    interface InjectPerson {
        public void injectHere(Person p);
    }

    class Company implements InjectPerson {
        Person injectedPerson;

        public void injectHere(Person p) {
            this.injectedPerson = p;
        }
    }
}
class Person{}