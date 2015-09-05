package cz.pikadorama.framework.util.map;


import cz.pikadorama.framework.util.Objects;

/**
 * Created by Tomas on 1.9.2015.
 */
public class StringToInteger implements Objects.MapFunction<String, Integer> {
    @Override
    public Integer map(String string) {
        return Integer.valueOf(string);
    }
}
