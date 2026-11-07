package com.example.codeinspection.demo;
import com.example.codeinspection.elements.*; import com.example.codeinspection.visitors.*;
public class Main {
 public static void main(String[] args){
  Directory project=new Directory("fintech-project"),src=new Directory("src"),tests=new Directory("tests"),config=new Directory("config");
  src.add(new SourceCodeFile("PaymentService.java",180,12,false,true,true,true));src.add(new SourceCodeFile("UserService.java",120,7,true,false,false,false));
  tests.add(new TestFile("PaymentServiceTest.java",95,8));config.add(new ConfigurationFile("application.properties",true));project.add(src);project.add(tests);project.add(config);
  project.add(new DependencyFile("spring-core","6.1.5",false,false));project.add(new DependencyFile("legacy-json","1.0",true,true));
  SecurityAuditVisitor security=new SecurityAuditVisitor();project.accept(security);System.out.println("=== SECURITY AUDIT ===\n"+security.report());
  ProjectStatisticsVisitor stats=new ProjectStatisticsVisitor();project.accept(stats);System.out.println("\n=== PROJECT STATISTICS ===\n"+stats.report());
  QualityGateVisitor quality=new QualityGateVisitor();project.accept(quality);System.out.println("\n=== QUALITY GATE ===\n"+quality.report());
  ComplianceVisitor compliance=new ComplianceVisitor();project.accept(compliance);System.out.println("\n=== COMPLIANCE ===\n"+compliance.report());
  DocumentationVisitor docs=new DocumentationVisitor();project.accept(docs);System.out.println("\n=== DOCUMENTATION ===\n"+docs.report());
 }
}
