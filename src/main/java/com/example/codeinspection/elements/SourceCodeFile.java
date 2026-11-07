package com.example.codeinspection.elements;

import com.example.codeinspection.ProjectElement;
import com.example.codeinspection.ProjectVisitor;

public class SourceCodeFile implements ProjectElement {
    private final String name;
    private final int linesOfCode;
    private final int complexity;
    private final boolean hardcodedCredentials;
    private final boolean highRisk;
    private final boolean hasTests;
    private final boolean documented;

    public SourceCodeFile(String name, int linesOfCode, int complexity,
                          boolean hardcodedCredentials, boolean highRisk,
                          boolean hasTests, boolean documented) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("File name is required");
        if (linesOfCode < 0 || complexity < 0) throw new IllegalArgumentException("Metrics cannot be negative");
        this.name=name; this.linesOfCode=linesOfCode; this.complexity=complexity;
        this.hardcodedCredentials=hardcodedCredentials; this.highRisk=highRisk;
        this.hasTests=hasTests; this.documented=documented;
    }
    public String getName(){return name;}
    public int getLinesOfCode(){return linesOfCode;}
    public int getComplexity(){return complexity;}
    public boolean hasHardcodedCredentials(){return hardcodedCredentials;}
    public boolean isHighRisk(){return highRisk;}
    public boolean hasTests(){return hasTests;}
    public boolean isDocumented(){return documented;}
    @Override public void accept(ProjectVisitor visitor){visitor.visit(this);}
}
