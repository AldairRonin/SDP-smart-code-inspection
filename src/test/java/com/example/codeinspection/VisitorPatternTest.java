package com.example.codeinspection;

import com.example.codeinspection.elements.*;
import com.example.codeinspection.visitors.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VisitorPatternTest {
    @Test void eachElementDispatchesToVisitor(){
        Directory d=new Directory("root");d.add(new SourceCodeFile("A.java",10,2,false,false,true,true));d.add(new TestFile("ATest.java",5,1));d.add(new ConfigurationFile("app.properties",false));d.add(new DependencyFile("lib","1.0",false,false));
        ProjectStatisticsVisitor v=new ProjectStatisticsVisitor();d.accept(v);assertEquals(4,v.getTotalFiles());assertEquals(1,v.getTestFiles());
    }
    @Test void securityDetectsHardcodedCredentials(){Directory d=new Directory("root");d.add(new SourceCodeFile("Auth.java",20,3,true,false,true,true));SecurityAuditVisitor v=new SecurityAuditVisitor();d.accept(v);assertEquals(1,v.getCriticalIssueCount());assertTrue(v.getIssues().get(0).contains("Auth.java"));}
    @Test void securityDetectsVulnerableDependency(){Directory d=new Directory("root");d.add(new DependencyFile("old-lib","1",true,false));SecurityAuditVisitor v=new SecurityAuditVisitor();d.accept(v);assertEquals(1,v.getCriticalIssueCount());}
    @Test void securityScoreNeverNegative(){Directory d=new Directory("root");for(int i=0;i<5;i++)d.add(new DependencyFile("v"+i,"1",true,false));SecurityAuditVisitor v=new SecurityAuditVisitor();d.accept(v);assertEquals(0,v.getSecurityScore());}
    @Test void statisticsCountsLinesAndDependencies(){Directory d=new Directory("root");d.add(new SourceCodeFile("A.java",30,6,false,false,true,true));d.add(new TestFile("ATest.java",12,3));d.add(new DependencyFile("lib","1",false,false));ProjectStatisticsVisitor v=new ProjectStatisticsVisitor();d.accept(v);assertEquals(42,v.getTotalLinesOfCode());assertEquals(1,v.getDependencyCount());}
    @Test void statisticsCalculatesAverageComplexity(){Directory d=new Directory("root");d.add(new SourceCodeFile("A.java",10,4,false,false,true,true));d.add(new SourceCodeFile("B.java",10,8,false,false,true,true));ProjectStatisticsVisitor v=new ProjectStatisticsVisitor();d.accept(v);assertEquals(6.0,v.getAverageComplexity());}
    @Test void statisticsCalculatesTestSourceRatio(){Directory d=new Directory("root");d.add(new SourceCodeFile("A.java",10,1,false,false,true,true));d.add(new SourceCodeFile("B.java",10,1,false,false,true,true));d.add(new TestFile("ATest.java",5,2));ProjectStatisticsVisitor v=new ProjectStatisticsVisitor();d.accept(v);assertEquals(0.5,v.getTestSourceRatio());}
    @Test void qualityGatePassesWhenAllThresholdsMet(){Directory d=new Directory("root");d.add(new SourceCodeFile("A.java",10,8,false,false,true,true));d.add(new TestFile("ATest.java",5,2));QualityGateVisitor v=new QualityGateVisitor();d.accept(v);assertTrue(v.isReleaseReady());}
    @Test void qualityGateFailsForHighComplexity(){Directory d=new Directory("root");d.add(new SourceCodeFile("A.java",10,11,false,false,true,true));d.add(new TestFile("ATest.java",5,2));QualityGateVisitor v=new QualityGateVisitor();d.accept(v);assertFalse(v.isReleaseReady());assertTrue(v.report().contains("Complexity: FAIL"));}
    @Test void qualityGateFailsForCriticalSecurityIssue(){Directory d=new Directory("root");d.add(new SourceCodeFile("A.java",10,2,true,false,true,true));d.add(new TestFile("ATest.java",5,2));QualityGateVisitor v=new QualityGateVisitor();d.accept(v);assertFalse(v.isReleaseReady());}
    @Test void complianceFindsMissingVersionAndProhibitedDependency(){Directory d=new Directory("root");d.add(new DependencyFile("unknown",null,false,false));d.add(new DependencyFile("blocked","2.0",false,true));ComplianceVisitor v=new ComplianceVisitor();d.accept(v);assertEquals(2,v.getViolations().size());}
    @Test void complianceFindsMissingTestsAndExposedCredentials(){Directory d=new Directory("root");d.add(new SourceCodeFile("Payment.java",20,2,true,false,false,true));ComplianceVisitor v=new ComplianceVisitor();d.accept(v);assertEquals(2,v.getViolations().size());}
    @Test void nestedDirectoriesAreTraversed(){Directory root=new Directory("root"),nested=new Directory("nested");nested.add(new SourceCodeFile("Nested.java",7,2,false,false,true,true));root.add(nested);ProjectStatisticsVisitor v=new ProjectStatisticsVisitor();root.accept(v);assertEquals(1,v.getTotalFiles());assertEquals(7,v.getTotalLinesOfCode());}
    @Test void emptyDirectoryHasZeroMetrics(){Directory empty=new Directory("empty");ProjectStatisticsVisitor v=new ProjectStatisticsVisitor();empty.accept(v);assertEquals(0,v.getTotalFiles());assertEquals(0.0,v.getAverageComplexity());assertEquals(0.0,v.getTestSourceRatio());}
    @Test void documentationVisitorFindsUndocumentedSource(){Directory d=new Directory("root");d.add(new SourceCodeFile("NoDocs.java",3,1,false,false,true,false));DocumentationVisitor v=new DocumentationVisitor();d.accept(v);assertEquals(java.util.List.of("NoDocs.java"),v.getUndocumentedFiles());}
    @Test void negativeMetricsAreRejected(){assertThrows(IllegalArgumentException.class,()->new SourceCodeFile("Bad.java",-1,1,false,false,false,false));}
}
