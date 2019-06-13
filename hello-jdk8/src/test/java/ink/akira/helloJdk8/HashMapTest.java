package ink.akira.helloJdk8;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HashMapTest {
    @Test
    public void testPut(){
        Set<String> set = new HashSet();
        set.add(null);
        set.add(null);
        set.add(null);
        String[] strings = set.toArray(new String[0]);
        Key key1 = new Key("key1");
        Key key2 = new Key("key2");
        Value value1 = new Value("value1");
        Value value2 = new Value("value2");
        Map<Key, Value> map = new HashMap<>();
        map.put(key1, value1);
        map.put(key2, value2);
    }
}

class Key {
    private String name;

    public Key(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}

class Value {
    private String name;

    public Value(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
        return true;
    }
}
