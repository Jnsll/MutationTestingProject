# MutationTestingProject  
Master : [![Build Status](https://travis-ci.com/Jnsll/MutationTestingProject.svg?token=yEvcBv9NYXmyDfvx1xFm&branch=master)](https://travis-ci.com/Jnsll/MutationTestingProject)
Dev : [![Build Status](https://travis-ci.com/Jnsll/MutationTestingProject.svg?token=yEvcBv9NYXmyDfvx1xFm&branch=dev)](https://travis-ci.com/Jnsll/MutationTestingProject)

V & V project.

## Usage

java -jar vandv.mutationtesting.project-1.0-SNAPSHOT-jar-with-dependencies.jar  "path/to/target/project"

Example : java -jar vandv.mutationtesting.project-1.0-SNAPSHOT-jar-with-dependencies.jar  "/home/user/Documents/MasterIL/VV/MockMathSoftware"

Note : We recommend the use of the MockMathSoftware (https://github.com/Jnsll/MockMathSoftware) as the target project

## Left to do

- [ ] Create a maven plugin for the project
- [x] Test the VoidMethodMutator (with a mock to test on)
- [ ] Test the ProjectTarget class
- [ ] Create a report on how many mutants (and which ones) were detected
- [ ] Create another mutator (if there's some time left)
- [ ] Raise the code coverage
- [ ] Write the report

## Features
- Build the target project
- List the mutants
- Replace the ByteCode of the target project
- Launch the tests of the target project
- List the output of the target tests
- Revert the change of the ByteCode
- Test some methods/mutators of the source project (MutationTestingProject) 

## Resources
- Javassist
- JUnit
- Cobertura
- Travis CI

## Target project
- Simple project with basic operations [https://github.com/Jnsll/MockMathSoftware]
- commons-math [https://github.com/apache/commons-math]

## Authors
- June Benvegnu-Sallou
- Antoine Ferey
- Emmanuel Loisance
