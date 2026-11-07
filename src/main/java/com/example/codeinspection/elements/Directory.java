package com.example.codeinspection.elements;

import com.example.codeinspection.ProjectElement;
import com.example.codeinspection.ProjectVisitor;
import java.util.*;

public class Directory implements ProjectElement {
    private final String name; private final List<ProjectElement> children=new ArrayList<>();
    public Directory(String name){if(name==null||name.isBlank())throw new IllegalArgumentException("Directory name is required");this.name=name;}
    public String getName(){return name;}
    public void add(ProjectElement element){children.add(Objects.requireNonNull(element,"element"));}
    public List<ProjectElement> getChildren(){return Collections.unmodifiableList(children);}
    @Override public void accept(ProjectVisitor visitor){visitor.visit(this);}
}
