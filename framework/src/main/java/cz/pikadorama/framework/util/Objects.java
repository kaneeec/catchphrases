package cz.pikadorama.framework.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Tomas on 1.9.2015..
 */
public class Objects {

    /**
     * Perform operation defined by {@link cz.pikadorama.framework.util.Objects.MapFunction}
     * on all elements of the input list.
     *
     * @param list input list
     * @param mapFunction mapping function
     * @param <I> input type
     * @param <O> output type
     * @return list of output type elements
     */
    public static <I, O> List<O> map(List<I> list, MapFunction<I, O> mapFunction) {
        List<O> result = new ArrayList<>();
        for (I item : list) {
            result.add(mapFunction.map(item));
        }
        return result;
    }

    /**
     * Maps items of type I into type O.
     *
     * @param <I> input type
     * @param <O> output type
     */
    public interface MapFunction<I, O> {
        O map(I item);
    }

}
