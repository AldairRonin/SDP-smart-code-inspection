package com.example.codeinspection.elements;

import com.example.codeinspection.ProjectElement;
import com.example.codeinspection.ProjectVisitor;

public class ConfigurationFile implements ProjectElement {
    private final String name; private final boolean insecure;
    public ConfigurationFile(String name,boolean insecure){if(name==null||name.isBlank())throw new IllegalArgumentException("File name is required");this.name=name;this.insecure=insecure;}
    public String getName(){return name;} public boolean isInsecure(){return insecure;}
    @Override public void accept(ProjectVisitor visitor){visitor.visit(this);}
}
