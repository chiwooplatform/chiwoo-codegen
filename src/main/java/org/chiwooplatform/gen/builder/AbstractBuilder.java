package org.chiwooplatform.gen.builder;

import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.text.WordUtils;

import org.chiwooplatform.gen.GenContants;
import org.chiwooplatform.gen.Inflector;
import org.chiwooplatform.gen.config.ValueHolder;
import org.chiwooplatform.gen.model.TableColumnMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractBuilder {

    protected final Logger logger = LoggerFactory.getLogger( this.getClass() );

    protected static final char NL = GenContants.NL;

    protected static final char CM = GenContants.CM;

    protected static final String NLBS4 = GenContants.NLBS4;

    protected static final String NLBS8 = GenContants.NLBS8;

    protected static final String NLBS10 = GenContants.NLBS10;

    protected final Inflector inflector = new Inflector();

    abstract public String build()
        throws Exception;

    public abstract List<TableColumnMeta> getColumnsMeta();

    protected ValueHolder holder;

    protected AbstractBuilder( ValueHolder holder ) {
        this.holder = holder;
    }

    protected String javaType( TableColumnMeta col ) {
        String type = col.getType().toUpperCase();
        // int size = col.getColumn_size();
        Integer precision = col.getData_precision();
        Integer scale = col.getData_scale();
        String javaType = null;
        switch ( type ) {
            case "TIME":
            case "DATE":
            case "DATETIME":
                javaType = "Date";
                break;
            case "TIMESTAMP":
                javaType = "java.sql.Timestamp";
                break;
            case "LONG":
                javaType = "Long";
                break;
            case "NUMBER":
                if ( precision <= 10 ) {
                    if ( scale > 0 ) {
                        javaType = "Float";
                    } else {
                        javaType = "Integer";
                    }
                } else {
                    javaType = "java.math.BigDecimal";
                }
                break;
            default:
                javaType = "String";
                break;
        }
        return javaType;
    }

    protected String lowerName( String name ) {
        if ( name == null ) {
            return null;
        }
        return name.substring( 0, 1 ).toLowerCase() + name.substring( 1 );
    }

    protected String upperName( String name ) {
        if ( name == null ) {
            return name;
        }
        return name.substring( 0, 1 ).toUpperCase() + name.substring( 1 );
    }

    protected String getClazzName( String name ) {
        String result = null;
        if ( name != null ) {
            result = WordUtils.capitalizeFully( name, new char[] { '_', '-', ' ' } );
            result = result.replaceAll( "_|-| ", "" );
        }
        return result;
    }

    protected String getTokendText( String text ) {
        if ( text == null ) {
            return null;
        }
        StringTokenizer tokenizer = new StringTokenizer( text );
        String result = tokenizer.nextToken( "\n" );
        if ( result == null ) {
            result = text;
        }
        return result;
    }
}
