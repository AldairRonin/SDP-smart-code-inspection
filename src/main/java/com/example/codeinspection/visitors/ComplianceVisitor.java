package com.example.codeinspection.visitors;
import com.example.codeinspection.*; import com.example.codeinspection.elements.*; import java.util.*;
public class ComplianceVisitor implements ProjectVisitor {
 private final List<String> violations=new ArrayList<>();
 public void visit(SourceCodeFile f){if(f.hasHardcodedCredentials())violations.add("Exposed credentials: "+f.getName());if(!f.hasTests())violations.add("Production source has no tests: "+f.getName());}
 public void visit(TestFile f){} public void visit(ConfigurationFile f){if(f.isInsecure())violations.add("Insecure configuration: "+f.getName());}
 public void visit(DependencyFile f){if(f.getVersion()==null||f.getVersion().isBlank())violations.add("Unspecified dependency version: "+f.getName());if(f.isProhibited())violations.add("Prohibited dependency: "+f.getName());}
 public void visit(Directory d){for(ProjectElement e:d.getChildren())e.accept(this);}
 public List<String> getViolations(){return Collections.unmodifiableList(violations);} public boolean isCompliant(){return violations.isEmpty();}
 public String report(){return isCompliant()?"Compliance: PASS":"Compliance: FAIL\n"+String.join("\n",violations);}
}
