# MutationTestingProject  
Master : [![Build Status](https://travis-ci.com/Jnsll/MutationTestingProject.svg?token=yEvcBv9NYXmyDfvx1xFm&branch=master)](https://travis-ci.com/Jnsll/MutationTestingProject)
Dev : [![Build Status](https://travis-ci.com/Jnsll/MutationTestingProject.svg?token=yEvcBv9NYXmyDfvx1xFm&branch=dev)](https://travis-ci.com/Jnsll/MutationTestingProject)

V & V project.

## Usage


java -jar vandv.mutationtesting.project-1.0-SNAPSHOT-jar-with-dependencies.jar  "path/to/target/project" "path/to/save/report"

Example : java -jar vandv.mutationtesting.project-1.0-SNAPSHOT-jar-with-dependencies.jar  "/home/user/Documents/MasterIL/VV/MockMathSoftware" "/home/user/Documents/"


## Left to do

- [ ] Create a maven plugin for the project (In progress)
- [x] Test the VoidMethodMutator (with a mock to test on)
- [ ] Test the ProjectTarget class (In progress)
- [ ] Create a report on how many mutants (and which ones) were detected (In progress : Mutant detection score)
- [ ] Raise the code coverage (In progress)
- [x] Can be lauched to test a big project (ie. Common Math & Common CLI)
- [x] Write the report

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
