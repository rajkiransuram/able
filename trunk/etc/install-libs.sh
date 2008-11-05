mvn install:install-file -DgroupId=warp-persist -DartifactId=warp-persist -Dversion=1.0.1 -Dpackaging=jar -Dfile=warp-persist-1.0.1.jar

mvn install:install-file -DgroupId=com.google.code.guice -DartifactId=guice-servlet -Dversion=1.0 -Dpackaging=jar -Dfile=guice-servlet-1.0.jar

mvn install:install-file -DgroupId=org.stripesframework -DartifactId=stripes -Dversion=1.5.1-SNAPSHOT -Dpackaging=jar -Dfile=stripes-1.5.1-SNAPSHOT.jar
mvn install:install-file -DgroupId=org.stripesframework -DartifactId=stripes -Dversion=1.5.1-SNAPSHOT -Dclassifier=sources -Dpackaging=jar -Dfile=stripes-1.5.1-SNAPSHOT-src.jar

mvn install:install-file -DgroupId=org.directwebremoting -DartifactId=dwr -Dversion=3.0.0.109.dev -Dpackaging=jar -Dfile=dwr-3.0.0.109.dev.jar

mvn install:install-file -DgroupId=org.hibernate -DartifactId=hibernate-parent -Dversion=3.3.2-SNAPSHOT -Dpackaging=pom -Dfile=hibernate-parent-3.3.2-SNAPSHOT.pom
mvn install:install-file -DgroupId=org.hibernate -DartifactId=hibernate-core -Dversion=3.3.2-SNAPSHOT -Dpackaging=jar -Dfile=hibernate-core-3.3.2-SNAPSHOT.jar
mvn install:install-file -DgroupId=org.hibernate -DartifactId=hibernate-core -Dversion=3.3.2-SNAPSHOT -Dpackaging=pom -Dfile=hibernate-core-3.3.2-SNAPSHOT.pom
mvn install:install-file -DgroupId=org.hibernate -DartifactId=hibernate-validator -Dversion=3.1.0.GA -Dpackaging=jar -Dfile=hibernate-validator-3.1.0.GA.jar
