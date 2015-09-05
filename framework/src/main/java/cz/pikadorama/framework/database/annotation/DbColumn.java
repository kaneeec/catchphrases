package cz.pikadorama.framework.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cz.pikadorama.framework.database.DbDataType;


/**
 * Created by Tomas on 8.8.2015.
 *
 * Annotation for database column.
 * <p/>
 * NOTE: if you annotate an ID column with autoincrement option, make sure that the data type is
 * {@link Integer}.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DbColumn {

    /**
     * Column name.
     *
     * @return column name
     */
    public String name();

    /**
     * Database column data type.
     *
     * @return database column data type
     */
    public DbDataType type();

    /**
     * (OPTIONAL) Additional database column properties.
     *
     * @return additional database column properties
     */
    public String properties() default "";

}
