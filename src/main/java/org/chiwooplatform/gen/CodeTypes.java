/**
 * @author seonbo.shim
 * @version 1.0, 2017-04-21
 * @copyright BESPIN GLOBAL
 */
package org.chiwooplatform.gen;

public class CodeTypes {

    public enum DBMS {
                      ORACLE,
                      MARIADB
    }

    //camel-case|snake-case|spinal-case
    public enum ConjectionType {
                                CamelCase("camel-case"),
                                SnakeCase("snake-case"),
                                SpinalCase("spinal-case"),
                                NotSupport("not-support");

        private String code;

        ConjectionType( String code ) {
            this.code = code;
        }

        public String code() {
            return this.code;
        }

        static public ConjectionType get( String code ) {
            if ( code == null ) {
                return ConjectionType.NotSupport;
            }
            ConjectionType[] elements = ConjectionType.values();
            for ( ConjectionType type : elements ) {
                if ( code.equals( type.code ) ) {
                    return type;
                }
            }
            return ConjectionType.NotSupport;
        }
    }
}
