package com.example.codeinspection;

import com.example.codeinspection.elements.*;

/** Operations that can be performed on every project element type. */
public interface ProjectVisitor {
    void visit(SourceCodeFile file);
    void visit(TestFile file);
    void visit(ConfigurationFile file);
    void visit(DependencyFile file);
    void visit(Directory directory);
}
