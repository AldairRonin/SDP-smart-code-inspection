package com.example.codeinspection.elements;

import com.example.codeinspection.ProjectElement;
import com.example.codeinspection.ProjectVisitor;

public class TestFile implements ProjectElement {
    private final String name; private final int linesOfCode; private final int testCases;
    public TestFile(String name,int linesOfCode,int testCases){
        if(name==null||name.isBlank()) throw new IllegalArgumentException("File name is required");
        if(linesOfCode<0||testCases<0) throw new IllegalArgumentException("Metrics cannot be negative");
        this.name=name;this.linesOfCode=linesOfCode;this.testCases=testCases;
    }
    public String getName(){return name;} public int getLinesOfCode(){return linesOfCode;} public int getTestCases(){return testCases;}
    @Override public void accept(ProjectVisitor visitor){visitor.visit(this);}
}
