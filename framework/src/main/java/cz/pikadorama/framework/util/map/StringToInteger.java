package cz.pikadorama.framework.util.map;


import cz.pikadorama.framework.util.Objects;

/**
 * Created by Tomas on 1.9.2015.
 */
public class StringToInteger implements Objects.MapFunction<String, Integer> {
    @Override
    public Integer map(String string) {
        if (string == null || string.isEmpty()) {
            return null;
        }
        return Integer.valueOf(string);
    }
}
