package com.example.codeinspection.visitors;
import com.example.codeinspection.*; import com.example.codeinspection.elements.*; import java.util.*;
public class QualityGateVisitor implements ProjectVisitor {
 private int sourceFiles,testFiles,totalComplexity,criticalSecurityIssues; private final List<String> checks=new ArrayList<>();
 public void visit(SourceCodeFile f){sourceFiles++;totalComplexity+=f.getComplexity();if(f.hasHardcodedCredentials())criticalSecurityIssues++;}
 public void visit(TestFile f){testFiles++;} public void visit(ConfigurationFile f){}
 public void visit(DependencyFile f){if(f.isVulnerable())criticalSecurityIssues++;}
 public void visit(Directory d){for(ProjectElement e:d.getChildren())e.accept(this);}
 public boolean isReleaseReady(){checks.clear();double avg=sourceFiles==0?0:(double)totalComplexity/sourceFiles;double ratio=sourceFiles==0?0:(double)testFiles/sourceFiles;checks.add("Security: "+(criticalSecurityIssues==0?"PASS":"FAIL"));checks.add("Complexity: "+(avg<=10?"PASS":"FAIL"));checks.add("Tests: "+(ratio>=0.40?"PASS":"FAIL"));return criticalSecurityIssues==0&&avg<=10&&ratio>=0.40;}
 public List<String> getChecks(){isReleaseReady();return Collections.unmodifiableList(checks);}
 public String report(){boolean pass=isReleaseReady();return String.join("\n",checks)+"\nFINAL RESULT: "+(pass?"PASSED":"FAILED");}
}
