### VARIABLES ###

PACKAGE = fr.iutfbleau.sae31.AtoZ
SDIR = src/fr/iutfbleau/sae31/AtoZ
BDIR = bin/fr/iutfbleau/sae31/AtoZ
DRIVER = /export/documents/mariadb-client.jar

JC = javac
JCFLAGS = -encoding UTF-8 -implicit:none -d bin -cp "bin:${DRIVER}" -sourcepath src

JVM = java
JVMFLAGS = -cp "bin:${DRIVER}"

### REGLES DE COMPILATION ###

atoz.jar : ${BDIR}/Main.class
	jar cvfe atoz.jar ${PACKAGE}.Main -C bin .

${BDIR}/Main.class : ${SDIR}/Main.java ${BDIR}/vue/Fenetre.class ${BDIR}/controleur/Controleur.class ${BDIR}/model/Carnet.class
	${JC} ${JCFLAGS} ${SDIR}/Main.java

${BDIR}/vue/Fenetre.class : ${SDIR}/vue/Fenetre.java ${BDIR}/vue/PanneauCentral.class ${BDIR}/vue/PanneauGauche.class ${BDIR}/vue/PanneauDroit.class
	${JC} ${JCFLAGS} ${SDIR}/vue/Fenetre.java

${BDIR}/vue/PanneauCentral.class : ${SDIR}/vue/PanneauCentral.java
	${JC} ${JCFLAGS} ${SDIR}/vue/PanneauCentral.java

${BDIR}/vue/PanneauGauche.class : ${SDIR}/vue/PanneauGauche.java
	${JC} ${JCFLAGS} ${SDIR}/vue/PanneauGauche.java

${BDIR}/vue/PanneauDroit.class : ${SDIR}/vue/PanneauDroit.java
	${JC} ${JCFLAGS} ${SDIR}/vue/PanneauDroit.java

${BDIR}/controleur/Controleur.class : ${SDIR}/controleur/Controleur.java ${BDIR}/controleur/CarnetListener.class
	${JC} ${JCFLAGS} ${SDIR}/controleur/Controleur.java

${BDIR}/controleur/CarnetListener.class : ${SDIR}/controleur/CarnetListener.java
	${JC} ${JCFLAGS} ${SDIR}/controleur/CarnetListener.java

${BDIR}/model/Carnet.class : ${SDIR}/model/Carnet.java ${BDIR}/model/ContactDAO.class
	${JC} ${JCFLAGS} ${SDIR}/model/Carnet.java

${BDIR}/model/ContactDAO.class : ${SDIR}/model/ContactDAO.java ${BDIR}/model/Connexion.class
	${JC} ${JCFLAGS} ${SDIR}/model/ContactDAO.java

${BDIR}/model/Connexion.class : ${SDIR}/model/Connexion.java
	${JC} ${JCFLAGS} ${SDIR}/model/Connexion.java

### REGLES D'AUTRES ACTIONS ###

run : atoz.jar
	${JVM} ${JVMFLAGS} ${PACKAGE}.Main

clean :
	-rmdir -fr bin/*
	-rm -f atoz.jar

mrproper : clean atoz.jar

### BUTS FACTICES ###

.PHONY : run clean mrproper

### FIN ###