# Smart Code Inspection & Compliance Engine

A Java 17+ demonstration of the **Visitor** design pattern. The same project tree is processed by independent operations without putting audit/statistics/compliance methods into the element classes.

## Requirements
- JDK 17 or newer
- Maven 3.9+

## Run
```bash
mvn clean test
mvn -q package
java -cp target/classes com.example.codeinspection.demo.Main
```

## Structure
```text
src/main/java/com/example/codeinspection/
  ProjectElement.java
  ProjectVisitor.java
  elements/ (SourceCodeFile, TestFile, ConfigurationFile, DependencyFile, Directory)
  visitors/ (SecurityAuditVisitor, ProjectStatisticsVisitor, QualityGateVisitor,
             ComplianceVisitor, DocumentationVisitor)
  demo/Main.java
src/test/java/.../VisitorPatternTest.java
```

## Demo
`Main` builds a fintech project with `src`, `tests`, `config`, and dependency entries. It runs all five visitors against the same root directory. The sample intentionally contains several findings so reports demonstrate both passing and failing checks.

## Design questions

1. **What problem does Visitor solve?** It separates operations from the object structure. New operations can be added as visitor classes instead of adding operation methods to every element class.
2. **Where are Element and Visitor?** `ProjectElement` is the Element interface; the five classes in `elements` are Concrete Elements. `ProjectVisitor` is the Visitor interface; classes in `visitors` are Concrete Visitors.
3. **What is double dispatch?** The caller invokes `element.accept(visitor)`. The runtime element type selects its `accept` implementation, and `visitor.visit(this)` then selects the overload matching that concrete element type.
4. **Why not put operations in Element classes?** That couples data objects to every operation and makes the element classes grow whenever a new analysis is introduced.
5. **Is it easier to add a Visitor or an Element? Why?** A Visitor is easier: implement the five visit methods in a new class. A new Element is harder because `ProjectElement`'s visitor contract must gain a new overload and every concrete visitor must implement it.
6. **When is Visitor a bad choice?** When element types change frequently, when there are many element types but few operations, or when visitors need to mutate encapsulated element state.
7. **Visitor vs Strategy:** Strategy selects one interchangeable algorithm behind a common interface, usually for one task. Visitor adds operations across a set of different element types and uses overloaded `visit` methods with double dispatch.
8. **Which SOLID principles appear?** Open/Closed: add visitors without modifying elements. Single Responsibility: elements hold project data; visitors perform analyses. Dependency Inversion: elements depend on the `ProjectVisitor` abstraction, not concrete visitors. Interface Segregation is less central because the visitor interface deliberately has one method per supported element type.

## Visitor limitation challenge: adding DatabaseMigrationFile

Add `visit(DatabaseMigrationFile file)` to `ProjectVisitor`, create `DatabaseMigrationFile implements ProjectElement` with `accept(visitor) { visitor.visit(this); }`, and update **every existing concrete visitor** to implement the new overload. Existing directory traversal does not need a change because it stores the common `ProjectElement` abstraction. This demonstrates the trade-off: adding operations is cheap; adding element types requires changing the visitor contract and all visitors.

## Metrics and rules
- Average complexity is the arithmetic mean of source-file complexity; empty source sets return `0`.
- Test/source ratio is the number of test files divided by source files; an empty source set returns `0`.
- Quality gate: average complexity `<= 10`, test/source ratio `>= 0.40`, and zero critical issues (hardcoded credentials and vulnerable dependencies).
- Security score starts at 100, subtracts 30 per critical issue and 15 per high issue, and is clamped to zero.
- Compliance checks exposed credentials, missing tests for production source files, missing dependency versions, insecure configuration, and prohibited dependencies.

## Tests
The JUnit 5 suite contains 16 behavior-focused tests covering element dispatch, each main visitor, quality-gate pass/fail, nested and empty directories, the additional visitor, and invalid metric input.

## Git development history
The repository is prepared with staged, meaningful commits. Run `git log --oneline` to review the implementation sequence.
