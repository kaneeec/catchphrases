package cz.pikadorama.framework.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.pikadorama.framework.database.dao.DaoQueryHelper;

/**
 * Created by Tomas on 8.8.2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DbTable {

    /**
     * Table name.
     *
     * @return table name
     */
    public String name();

    /**
     * Class name for {@link DaoQueryHelper}.
     *
     * @return cursor object mapping class
     */
    public Class<?> mappingClass();

}
