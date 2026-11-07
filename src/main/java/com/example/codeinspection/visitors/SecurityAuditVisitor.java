package com.example.codeinspection.visitors;
import com.example.codeinspection.*; import com.example.codeinspection.elements.*; import java.util.*;
public class SecurityAuditVisitor implements ProjectVisitor {
 private final List<String> issues=new ArrayList<>();
 public void visit(SourceCodeFile f){if(f.hasHardcodedCredentials())issues.add("CRITICAL: hardcoded credentials in "+f.getName());if(f.isHighRisk())issues.add("HIGH: high-risk source file "+f.getName());}
 public void visit(TestFile f){}
 public void visit(ConfigurationFile f){if(f.isInsecure())issues.add("HIGH: insecure configuration "+f.getName());}
 public void visit(DependencyFile f){if(f.isVulnerable())issues.add("CRITICAL: vulnerable dependency "+f.getName());}
 public void visit(Directory d){for(ProjectElement e:d.getChildren())e.accept(this);}
 public List<String> getIssues(){return Collections.unmodifiableList(issues);}
 public long getCriticalIssueCount(){return issues.stream().filter(s->s.startsWith("CRITICAL:")).count();}
 public int getSecurityScore(){return Math.max(0,100-issues.stream().mapToInt(s->s.startsWith("CRITICAL:")?30:15).sum());}
 public String report(){return "Security score: "+getSecurityScore()+"/100\n"+(issues.isEmpty()?"No security issues found.":String.join("\n",issues));}
}
