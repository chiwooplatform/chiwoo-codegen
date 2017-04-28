package org.chiwooplatform.gen.model;

public class Attribute {

    private String nm;

    private String name;

    private String type;

    private String value;

    private String assertValue;

    private String column;

    private boolean nullable;

    private boolean primaryKey;

    private String comment;

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

    /**
     * @return the column
     */
    public String getColumn() {
        return column;
    }

    /**
     * @param column the column to set
     */
    public void setColumn( String column ) {
        this.column = column;
    }

    /**
     * @return the nullable
     */
    public boolean isNullable() {
        return nullable;
    }

    /**
     * @param nullable the nullable to set
     */
    public void setNullable( boolean nullable ) {
        this.nullable = nullable;
    }

    /**
     * @return the primaryKey
     */
    public boolean isPrimaryKey() {
        return primaryKey;
    }

    /**
     * @param primaryKey the primaryKey to set
     */
    public void setPrimaryKey( boolean primaryKey ) {
        this.primaryKey = primaryKey;
    }

    /**
     * @return the comment
     */
    public String getComment() {
        if ( comment == null ) {
            return "";
        }
        return comment;
    }

    /**
     * @param comment the comment to set
     */
    public void setComment( String comment ) {
        this.comment = comment;
    }
}
