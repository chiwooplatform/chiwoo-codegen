package org.chiwooplatform.gen.model;

public class Attribute {

    private String nm;

    private String name;

    private String type;

    private String value;

    private String assertValue;

    /**
     * @return the nm
     */
    public String getNm() {
        return nm;
    }

    /**
     * @param nm the nm to set
     */
    public void setNm( String nm ) {
        this.nm = nm;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName( String name ) {
        this.name = name;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType( String type ) {
        this.type = type;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    public String getVal() {
        if ( "String".equals( this.type ) ) {
            return '"' + value + '"';
        } else {
            return value;
        }
    }

    /**
     * @param value the value to set
     */
    public void setValue( String value ) {
        this.value = value;
    }

    /**
     * @return the assertValue
     */
    public String getAssertValue() {
        return assertValue;
    }

    public String getAssertVal() {
        if ( "String".equals( this.type ) ) {
            return '"' + assertValue + '"';
        } else {
            return assertValue;
        }
    }

    /**
     * @param assertValue the assertValue to set
     */
    public void setAssertValue( String assertValue ) {
        this.assertValue = assertValue;
    }
}
