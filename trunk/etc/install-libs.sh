mvn deploy:deploy-file -DgroupId=warp-persist -DartifactId=warp-persist -Dversion=1.0.1 -Dpackaging=jar -Dfile=warp-persist-1.0.1.jar -Durl=file:repo -DrepositoryId=able-repo

mvn deploy:deploy-file -DgroupId=com.google.code.guice -DartifactId=guice-servlet -Dversion=1.0 -Dpackaging=jar -Dfile=guice-servlet-1.0.jar -Durl=file:repo -DrepositoryId=able-repo

mvn deploy:deploy-file -DgroupId=org.stripesframework -DartifactId=stripes -Dversion=1.5.1-able-snapshot -Dpackaging=jar -Dfile=stripes-1.5.1-able-snapshot.jar -Durl=file:repo -DrepositoryId=able-repo
mvn deploy:deploy-file -DgroupId=org.stripesframework -DartifactId=stripes -Dversion=1.5.1-able-snapshot -Dclassifier=sources -Dpackaging=jar -Dfile=stripes-1.5.1-able-snapshot-src.jar -Durl=file:repo -DrepositoryId=able-repo

mvn deploy:deploy-file -DgroupId=org.directwebremoting -DartifactId=dwr -Dversion=3.0.0.109.dev -Dpackaging=jar -Dfile=dwr-3.0.0.109.dev.jar -Durl=file:repo -DrepositoryId=able-repo

mvn deploy:deploy-file -DgroupId=org.hibernate -DartifactId=hibernate-parent -Dversion=3.3.2-able-snapshot -Dpackaging=pom -Dfile=hibernate-parent-3.3.2-able-snapshot.pom -Durl=file:repo -DrepositoryId=able-repo
mvn deploy:deploy-file -DgroupId=org.hibernate -DartifactId=hibernate-core -Dversion=3.3.2-able-snapshot -Dpackaging=jar -Dfile=hibernate-core-3.3.2-able-snapshot.jar -Durl=file:repo -DrepositoryId=able-repo
mvn deploy:deploy-file -DgroupId=org.hibernate -DartifactId=hibernate-core -Dversion=3.3.2-able-snapshot -Dpackaging=pom -Dfile=hibernate-core-3.3.2-able-snapshot.pom -Durl=file:repo -DrepositoryId=able-repo
mvn deploy:deploy-file -DgroupId=org.hibernate -DartifactId=hibernate-validator -Dversion=3.1.0.GA -Dpackaging=jar -Dfile=hibernate-validator-3.1.0.GA.jar -Durl=file:repo -DrepositoryId=able-repo