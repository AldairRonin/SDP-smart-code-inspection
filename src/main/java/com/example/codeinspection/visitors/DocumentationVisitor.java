package com.example.codeinspection.visitors;
import com.example.codeinspection.*; import com.example.codeinspection.elements.*; import java.util.*;
/** Additional visitor: finds source files that have no documentation. */
public class DocumentationVisitor implements ProjectVisitor {
 private final List<String> undocumented=new ArrayList<>();
 public void visit(SourceCodeFile f){if(!f.isDocumented())undocumented.add(f.getName());} public void visit(TestFile f){} public void visit(ConfigurationFile f){} public void visit(DependencyFile f){}
 public void visit(Directory d){for(ProjectElement e:d.getChildren())e.accept(this);}
 public List<String> getUndocumentedFiles(){return Collections.unmodifiableList(undocumented);}
 public String report(){return undocumented.isEmpty()?"Documentation: PASS":"Documentation: REVIEW\nUndocumented: "+String.join(", ",undocumented);}
}
