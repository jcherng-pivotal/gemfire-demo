package io.pivotal.gemfire.demo.client.customerorder.util;

import io.pivotal.gemfire.demo.model.gf.key.PersonKey;
import io.pivotal.gemfire.demo.model.gf.pdx.Person;
import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.Region;

import java.util.*;


public class RandomPersonBuilderUtils {


    public Map<PersonKey, Person> buildPerson(int numberOfPeople, int baselineCount) {
        Map persons = new HashMap();
        for (int i = 1; i < numberOfPeople + 1; i++) {
            int id = baselineCount + i;
            String gender = generateString(50);
            String name = generateString(27);
            String address = generateString(50);
            int age = generateNum(90, 5);
            Person v = new Person(id, gender, address, age, name);
            PersonKey k = new PersonKey(id);
            persons.put(k, v);
        }
        return persons;
    }


    public static String generateString(int length) {
        String characters = "qweQWERTYUIOPASDFGHJKLZXCVBNMrtyuiopasdfghjklzxcvbnm";
        Random rng = new Random();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

    public static int generateNum(int uBound, int lBound) {
        Random rand = new Random();
        int n = rand.nextInt(uBound) + lBound;
        return n;
    }

    @SuppressWarnings("unchecked")
    public void clearPersonData(Region theRegion) {
        Set<PersonKey> personKeySet = (DataPolicy.EMPTY.equals(theRegion.getAttributes().getDataPolicy()))
                ? theRegion.keySetOnServer() : theRegion.keySet();
        theRegion.removeAll(personKeySet);
    }
}
