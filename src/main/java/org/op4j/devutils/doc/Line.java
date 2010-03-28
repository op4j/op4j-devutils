package org.op4j.devutils.doc;

import java.util.List;

import org.apache.commons.lang.Validate;

public class Line {

    private String functionName;
    private String type;
    private List<String> params;
    private String javadoc;
    
    public Line(String functionName, String type, List<String> params,
            String javadoc) {
        super();
        Validate.notEmpty(functionName, "functionName can't be empty");
        Validate.notEmpty(type, "type can't be empty");
        Validate.notNull(params, "params can't be null");
        Validate.notNull(javadoc, "javadoc can't be empty");
        
        this.functionName = functionName;
        this.type = type;
        this.params = params;
        this.javadoc = javadoc;
    }
    public String getFunctionName() {
        return this.functionName;
    }
    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public List<String> getParams() {
        return this.params;
    }
    public void setParams(List<String> params) {
        this.params = params;
    }
    public String getJavadoc() {
        return this.javadoc;
    }
    public void setJavadoc(String javadoc) {
        this.javadoc = javadoc;
    }
    @Override
    public String toString() {
        return "Line [functionName=" + this.functionName + ", javadoc="
                + this.javadoc + ", params=" + this.params + ", type="
                + this.type + "]";
    }
    
    
}
