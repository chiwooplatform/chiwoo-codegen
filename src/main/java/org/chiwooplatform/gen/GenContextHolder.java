package org.chiwooplatform.gen;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.chiwooplatform.gen.config.ValueHolder;

public class GenContextHolder {

    private static final ThreadLocal<GenContextHolder> CONTEXT = new ThreadLocal<GenContextHolder>();

    protected Map<String, Object> holder = new HashMap<String, Object>();

    public static GenContextHolder get() {
        GenContextHolder ctx = CONTEXT.get();
        if ( ctx == null ) {
            ctx = new GenContextHolder();
            CONTEXT.set( ctx );
        }
        return ctx;
    }

    public boolean validate() {
        assert ( this.holder.get( ValueHolder.DBMS ) != null ) : "You need set 'ValueHolder.DBMS' of GenContextHolder.";
        assert ( this.holder.get( ValueHolder.PKG_NAME ) != null ) : "You need set 'ValueHolder.PKG_NAME' of GenContextHolder.";
        assert ( this.holder.get( ValueHolder.TABLE_NAME ) != null ) : "You need set 'ValueHolder.TABLE_NAME' of GenContextHolder.";
        assert ( this.holder.get( ValueHolder.MODEL_NAME ) != null ) : "You need set 'ValueHolder.MODEL_NAME' of GenContextHolder.";
        assert ( this.holder.get( ValueHolder.SERVICE_NAME ) != null ) : "You need set 'ValueHolder.SERVICE_NAME' of GenContextHolder.";
        return true;
    }

    public static void clear( final String key ) {
        GenContextHolder.get().remove( key );
    }

    public Object add( String key, Object value ) {
        return this.holder.put( key, value );
    }

    public Object remove( String key ) {
        return this.holder.remove( key );
    }

    public Object value( String key ) {
        return this.holder.get( key );
    }

    private final static char BL = ' ';

    public String toString() {
        StringBuilder ret = new StringBuilder();
        ret.append( "{" );
        Iterator<Entry<String, Object>> iter = this.holder.entrySet().iterator();
        while ( iter.hasNext() ) {
            ret.append( BL );
            Entry<String, ?> entry = iter.next();
            ret.append( entry.getKey() );
            ret.append( '=' ).append( '"' );
            Object v = entry.getValue();
            if ( v == null ) {
                ret.append( "{null}" );
            } else {
                if ( v instanceof String ) {
                    ret.append( v );
                } else {
                    ret.append( v.toString() );
                }
            }
            ret.append( '"' );
            if ( iter.hasNext() ) {
                ret.append( ',' );
            }
        }
        ret.append( BL );
        ret.append( "}" );
        return ret.toString();
    }

    public void clear() {
        Map<String, Object> map = CONTEXT.get().holder;
        if ( map != null ) {
            map.clear();
        }
        CONTEXT.remove();
    }
}
