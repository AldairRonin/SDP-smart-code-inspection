package com.example.codeinspection.elements;

import com.example.codeinspection.ProjectElement;
import com.example.codeinspection.ProjectVisitor;

public class DependencyFile implements ProjectElement {
    private final String name; private final String version; private final boolean vulnerable; private final boolean prohibited;
    public DependencyFile(String name,String version,boolean vulnerable,boolean prohibited){
        if(name==null||name.isBlank())throw new IllegalArgumentException("Dependency name is required");
        this.name=name;this.version=version;this.vulnerable=vulnerable;this.prohibited=prohibited;
    }
    public String getName(){return name;} public String getVersion(){return version;} public boolean isVulnerable(){return vulnerable;} public boolean isProhibited(){return prohibited;}
    @Override public void accept(ProjectVisitor visitor){visitor.visit(this);}
}
