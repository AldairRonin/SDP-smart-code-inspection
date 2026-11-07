package com.example.codeinspection.visitors;

import com.example.codeinspection.*;
import com.example.codeinspection.elements.*;

public class ProjectStatisticsVisitor implements ProjectVisitor {
    private int totalFiles,sourceFiles,testFiles,dependencyCount,totalLines,totalComplexity;
    public void visit(SourceCodeFile f){totalFiles++;sourceFiles++;totalLines+=f.getLinesOfCode();totalComplexity+=f.getComplexity();}
    public void visit(TestFile f){totalFiles++;testFiles++;totalLines+=f.getLinesOfCode();}
    public void visit(ConfigurationFile f){totalFiles++;}
    public void visit(DependencyFile f){totalFiles++;dependencyCount++;}
    public void visit(Directory d){for(ProjectElement e:d.getChildren())e.accept(this);}
    public int getTotalFiles(){return totalFiles;} public int getSourceFiles(){return sourceFiles;} public int getTestFiles(){return testFiles;}
    public int getDependencyCount(){return dependencyCount;} public int getTotalLinesOfCode(){return totalLines;}
    public double getAverageComplexity(){return sourceFiles==0?0.0:(double)totalComplexity/sourceFiles;}
    public double getTestSourceRatio(){return sourceFiles==0?0.0:(double)testFiles/sourceFiles;}
    public String report(){return String.format(java.util.Locale.US,"Files: %d%nLines of code: %d%nTests: %d%nDependencies: %d%nAverage complexity: %.2f%nTest/source ratio: %.2f",totalFiles,totalLines,testFiles,dependencyCount,getAverageComplexity(),getTestSourceRatio());}
}
