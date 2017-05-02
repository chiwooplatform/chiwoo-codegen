package org.chiwooplatform.gen.builder;

import java.util.List;

import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import org.chiwooplatform.gen.config.ValueHolder;
import org.chiwooplatform.gen.model.Attribute;
import org.chiwooplatform.gen.model.TableColumnMeta;

import freemarker.template.Template;

public class ControllerBuilderTemplateCallback
    extends AbstractDataBuilder {

    public ControllerBuilderTemplateCallback( ValueHolder valueHolder ) {
        super( valueHolder );
    }

    private String uriPath;

    private String pathVars;

    private String pathParams;

    private String buildParam;

    private void setLocalVariables( List<Attribute> attrs ) {
        StringBuffer paths = new StringBuffer();
        StringBuffer pathVars = new StringBuffer();
        StringBuffer pathParam = new StringBuffer();
        StringBuffer addParam = new StringBuffer();
        for ( Attribute key : attrs ) {
            String nm = key.getNm();
            paths.append( "/" ).append( "{" ).append( nm ).append( "}" );
            pathVars.append( " @PathVariable(" ).append( CM ).append( nm ).append( CM ).append( ") " );
            pathVars.append( key.getType() ).append( " " ).append( nm ).append( "," );
            pathParam.append( NL ).append( "     * @param " ).append( nm )
                     .append( " is the URI path variable for identifying resource." );
            addParam.append( NL ).append( "        param.put" )
                    .append( String.format( "( %s%s%s, %s );", CM, nm, CM, nm ) );
        }
        this.uriPath = paths.toString();
        this.pathVars = pathVars.toString();
        this.pathParams = pathParam.toString();
        this.buildParam = addParam.toString();
    }

    enum OperType {
                   LIST,
                   GET,
                   ADD,
                   MODIFY,
                   REMOVE,
                   ENABLE,
                   DISABLE
    }

    private String requestMapping( OperType operType, String uriPath ) {
        String value = null;
        switch ( operType ) {
            case GET:
                value = String.format( "@RequestMapping(value = BASE_URI%s, method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON_VALUE })",
                                       " + " + CM + uriPath + CM );
                break;
            case LIST:
                value = String.format( "@RequestMapping(value = BASE_URI%s, method = RequestMethod.GET, consumes = { MediaType.APPLICATION_JSON_VALUE })",
                                       " + " + CM + "/query" + CM );
                break;
            case ADD:
                value = String.format( "@RequestMapping(value = BASE_URI, method = RequestMethod.POST, consumes = { MediaType.APPLICATION_JSON_VALUE })" );
                break;
            case MODIFY:
                value = String.format( "@RequestMapping(value = BASE_URI%s, method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE })",
                                       " + " + CM + uriPath + CM );
                break;
            case REMOVE:
                value = String.format( "@RequestMapping(value = BASE_URI%s, method = RequestMethod.DELETE, consumes = { MediaType.APPLICATION_JSON_VALUE })",
                                       " + " + CM + uriPath + CM );
                break;
            case ENABLE:
                value = String.format( "@RequestMapping(value = BASE_URI%s, method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE })",
                                       " + " + CM + uriPath + "/enable" + CM );
                break;
            case DISABLE:
                value = String.format( "@RequestMapping(value = BASE_URI%s, method = RequestMethod.PUT, consumes = { MediaType.APPLICATION_JSON_VALUE })",
                                       " + " + CM + uriPath + "/disable" + CM );
                break;
            default:
                break;
        }
        return value;
    }

    public String build()
        throws Exception {
        List<TableColumnMeta> columnsMeta = getColumnsMeta();
        List<Attribute> primaryAttrs = primaryAttrs( primaryColumns( columnsMeta ) );
        setLocalVariables( primaryAttrs );
        holder.getContext().add( "controller.uriPath", this.uriPath );
        holder.getContext().add( "controller.pathVars", this.pathVars );
        holder.getContext().add( "controller.pathParams", this.pathParams );
        holder.getContext().add( "controller.buildParam", this.buildParam );
        holder.getContext().add( "controller.mapping.get", requestMapping( OperType.GET, uriPath ) );
        holder.getContext().add( "controller.mapping.add", requestMapping( OperType.ADD, uriPath ) );
        holder.getContext().add( "controller.mapping.query", requestMapping( OperType.LIST, uriPath ) );
        holder.getContext().add( "controller.mapping.modify", requestMapping( OperType.MODIFY, uriPath ) );
        holder.getContext().add( "controller.mapping.remove", requestMapping( OperType.REMOVE, uriPath ) );
        holder.getContext().add( "controller.mapping.enable", requestMapping( OperType.ENABLE, uriPath ) );
        holder.getContext().add( "controller.mapping.disable", requestMapping( OperType.DISABLE, uriPath ) );
        holder.getContext().add( "supportEnableDisable", supportEnableDisable( columnsMeta ) );
        Template t = holder.freemarkerConfigure().getTemplate( "builder.rest-controller.ftl" );
        String tval = FreeMarkerTemplateUtils.processTemplateIntoString( t, holder );
        logger.debug( "tval: {}", tval );
        return tval;
    }

    //    private TableColumnMeta tempKey() {
    //        TableColumnMeta c = new TableColumnMeta();
    //        c.setColumn_name( "temp_seq" );
    //        c.setColumn_size( 100 );
    //        c.setComments( "임시 번호" );
    //        c.setData_precision( null );
    //        c.setData_scale( null );
    //        c.setDefaultValue( null );
    //        c.setNullable( false );
    //        c.setPrimarykey( true );
    //        c.setType( "LONG" );
    //        return c;
    //    }
    public List<TableColumnMeta> getColumnsMeta() {
        // this._columnsMeta = holder.columnsMeta();
        // this._columnsMeta.add( tempKey() ); // 테스트 목적 임.
        return holder.columnsMeta();
    }
}
