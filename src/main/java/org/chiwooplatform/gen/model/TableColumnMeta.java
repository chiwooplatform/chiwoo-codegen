package org.chiwooplatform.gen.model;

public class TableColumnMeta {

    private String column_name;

    private String comments;

    private String type;

    private Integer column_size;

    private Integer data_precision;

    private Integer data_scale;

    private Boolean primarykey;

    private Boolean nullable;

    private String defaultValue;

    private String table_comments;

    public final String getColumn_name() {
        return column_name;
    }

    public final void setColumn_name( String column_name ) {
        this.column_name = column_name;
    }

    public final String getComments() {
        return comments;
    }

    public final void setComments( String comments ) {
        this.comments = comments;
    }

    public final String getType() {
        return type;
    }

    public final void setType( String type ) {
        this.type = type;
    }

    public final Integer getColumn_size() {
        return column_size;
    }

    public final void setColumn_size( Integer column_size ) {
        this.column_size = column_size;
    }

    public final Integer getData_precision() {
        return data_precision;
    }

    public final void setData_precision( Integer data_precision ) {
        this.data_precision = data_precision;
    }

    public final Integer getData_scale() {
        return data_scale;
    }

    public final void setData_scale( Integer data_scale ) {
        this.data_scale = data_scale;
    }

    public final Boolean isPrimarykey() {
        return primarykey;
    }

    public final void setPrimarykey( Boolean primarykey ) {
        this.primarykey = primarykey;
    }

    public final Boolean isNullable() {
        return nullable;
    }

    public final void setNullable( Boolean nullable ) {
        this.nullable = nullable;
    }

    public final String getDefaultValue() {
        return defaultValue;
    }

    public final void setDefaultValue( String defaultValue ) {
        this.defaultValue = defaultValue;
    }

    public final String getTable_comments() {
        return table_comments;
    }

    public final void setTable_comments( String table_comments ) {
        this.table_comments = table_comments;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append( "TableColumnMeta [column_name=" );
        builder.append( column_name );
        builder.append( ", comments=" );
        builder.append( comments );
        builder.append( ", type=" );
        builder.append( type );
        builder.append( ", column_size=" );
        builder.append( column_size );
        builder.append( ", data_precision=" );
        builder.append( data_precision );
        builder.append( ", data_scale=" );
        builder.append( data_scale );
        builder.append( ", primarykey=" );
        builder.append( primarykey );
        builder.append( ", nullable=" );
        builder.append( nullable );
        builder.append( ", defaultValue=" );
        builder.append( defaultValue );
        builder.append( ", table_comments=" );
        builder.append( table_comments );
        builder.append( "]" );
        return builder.toString();
    }
}
