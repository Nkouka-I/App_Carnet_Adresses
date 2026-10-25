### VARIABLES ###

PACKAGE = fr.iutfbleau.sae31.AtoZ
SDIR = src/fr/iutfbleau/sae31/AtoZ
BDIR = bin/fr/iutfbleau/sae31/AtoZ
DRIVER = lib/*

JC = javac
JCFLAGS = -encoding UTF-8 -implicit:none -d bin -cp "bin:${DRIVER}" -sourcepath src

JVM = java
JVMFLAGS = -cp "bin:${DRIVER}"

### REGLES DE COMPILATION ###

atoz.jar : ${BDIR}/Main.class
	jar cvfe atoz.jar ${PACKAGE}.Main -C bin .

${BDIR}/Main.class : ${SDIR}/Main.java ${BDIR}/controleur/CarnetListener.class ${BDIR}/model/Connexion.class ${BDIR}/controleur/Controleur.class ${BDIR}/controleur/ControleurFormulaireContact.class ${BDIR}/controleur/ControleurFormulaireEtiquette.class ${BDIR}/controleur/ControleurFormulaireListe.class ${BDIR}/controleur/ControleurNavigation.class ${BDIR}/vue/Fenetre.class ${BDIR}/vue/StyleComposant.class
	${JC} ${JCFLAGS} ${SDIR}/Main.java

${BDIR}/vue/ChampRecherche.class : ${SDIR}/vue/ChampRecherche.java ${BDIR}/vue/Style.class
	${JC} ${JCFLAGS} ${SDIR}/vue/ChampRecherche.java

${BDIR}/vue/CritereData.class : ${SDIR}/vue/CritereData.java ${BDIR}/controleur/CritereVue.class ${BDIR}/controleur/EtiquetteVue.class
	${JC} ${JCFLAGS} ${SDIR}/vue/CritereData.java

${BDIR}/vue/EcouteurClicLigne.class : ${SDIR}/vue/EcouteurClicLigne.java ${BDIR}/vue/NavigationListener.class
	${JC} ${JCFLAGS} ${SDIR}/vue/EcouteurClicLigne.java

${BDIR}/vue/EcouteurFermeture.class : ${SDIR}/vue/EcouteurFermeture.java
	${JC} ${JCFLAGS} ${SDIR}/vue/EcouteurFermeture.java

${BDIR}/vue/Fenetre.class : ${SDIR}/vue/Fenetre.java ${BDIR}/controleur/ContactVue.class ${BDIR}/controleur/Controleur.class ${BDIR}/vue/EcouteurFermeture.class ${BDIR}/controleur/EtiquetteVue.class ${BDIR}/controleur/ListeDiffusionVue.class ${BDIR}/vue/NavigationListener.class ${BDIR}/vue/PanneauCentral.class ${BDIR}/vue/PanneauDroit.class ${BDIR}/vue/PanneauGauche.class ${BDIR}/vue/Style.class ${BDIR}/vue/ZoneDefilante.class
	${JC} ${JCFLAGS} ${SDIR}/vue/Fenetre.java

${BDIR}/vue/FenetreDialogueAbstraite.class : ${SDIR}/vue/FenetreDialogueAbstraite.java ${BDIR}/vue/FormulaireListener.class ${BDIR}/vue/Style.class ${BDIR}/vue/StyleBouton.class ${BDIR}/vue/StyleComposant.class
	${JC} ${JCFLAGS} ${SDIR}/vue/FenetreDialogueAbstraite.java

${BDIR}/vue/FenetreFormulaireContact.class : ${SDIR}/vue/FenetreFormulaireContact.java ${BDIR}/controleur/Controleur.class ${BDIR}/controleur/EtiquetteVue.class ${BDIR}/vue/FenetreDialogueAbstraite.class ${BDIR}/vue/Style.class ${BDIR}/vue/StyleBouton.class ${BDIR}/vue/StyleComposant.class
	${JC} ${JCFLAGS} ${SDIR}/vue/FenetreFormulaireContact.java

${BDIR}/vue/FenetreFormulaireEtiquette.class : ${SDIR}/vue/FenetreFormulaireEtiquette.java ${BDIR}/controleur/Controleur.class ${BDIR}/vue/FenetreDialogueAbstraite.class ${BDIR}/vue/Style.class
	${JC} ${JCFLAGS} ${SDIR}/vue/FenetreFormulaireEtiquette.java

${BDIR}/vue/FenetreFormulaireListeDiffusion.class : ${SDIR}/vue/FenetreFormulaireListeDiffusion.java ${BDIR}/vue/CritereData.class ${BDIR}/controleur/CritereVue.class ${BDIR}/controleur/EtiquetteVue.class ${BDIR}/vue/FenetreDialogueAbstraite.class ${BDIR}/vue/StyleBouton.class
	${JC} ${JCFLAGS} ${SDIR}/vue/FenetreFormulaireListeDiffusion.java

${BDIR}/vue/FormulaireListener.class : ${SDIR}/vue/FormulaireListener.java
	${JC} ${JCFLAGS} ${SDIR}/vue/FormulaireListener.java

${BDIR}/vue/LigneContactAbstraite.class : ${SDIR}/vue/LigneContactAbstraite.java ${BDIR}/controleur/ContactVue.class ${BDIR}/vue/EcouteurClicLigne.class ${BDIR}/vue/NavigationListener.class ${BDIR}/vue/PressePapiers.class ${BDIR}/vue/Style.class ${BDIR}/vue/StyleBouton.class
	${JC} ${JCFLAGS} ${SDIR}/vue/LigneContactAbstraite.java

${BDIR}/vue/LigneContact.class : ${SDIR}/vue/LigneContact.java ${BDIR}/controleur/ContactVue.class ${BDIR}/controleur/EtiquetteVue.class ${BDIR}/vue/LigneContactAbstraite.class ${BDIR}/vue/NavigationListener.class ${BDIR}/vue/Style.class
	${JC} ${JCFLAGS} ${SDIR}/vue/LigneContact.java

${BDIR}/vue/LigneListeDiffusion.class : ${SDIR}/vue/LigneListeDiffusion.java ${BDIR}/vue/LigneContactAbstraite.class ${BDIR}/vue/NavigationListener.class
	${JC} ${JCFLAGS} ${SDIR}/vue/LigneListeDiffusion.java

${BDIR}/vue/NavigationListener.class : ${SDIR}/vue/NavigationListener.java ${BDIR}/controleur/ContactVue.class ${BDIR}/controleur/EtiquetteVue.class ${BDIR}/controleur/ListeDiffusionVue.class
	${JC} ${JCFLAGS} ${SDIR}/vue/NavigationListener.java

${BDIR}/vue/PanneauCentral.class : ${SDIR}/vue/PanneauCentral.java ${BDIR}/vue/ChampRecherche.class ${BDIR}/controleur/Controleur.class ${BDIR}/controleur/EtiquetteVue.class ${BDIR}/controleur/ListeDiffusionVue.class ${BDIR}/vue/NavigationListener.class ${BDIR}/vue/PanneauListeDiffusion.class ${BDIR}/vue/PanneauRepertoire.class ${BDIR}/vue/Style.class ${BDIR}/vue/StyleComposant.class
	${JC} ${JCFLAGS} ${SDIR}/vue/PanneauCentral.java

${BDIR}/vue/PanneauDroit.class : ${SDIR}/vue/PanneauDroit.java ${BDIR}/controleur/ContactVue.class ${BDIR}/controleur/Controleur.class ${BDIR}/controleur/EtiquetteVue.class ${BDIR}/controleur/ListeDiffusionVue.class ${BDIR}/vue/PanneauOperationsContact.class ${BDIR}/vue/PanneauOperationsEtiquette.class ${BDIR}/vue/PanneauOperationsListeDiffusion.class ${BDIR}/vue/Style.class
	${JC} ${JCFLAGS} ${SDIR}/vue/PanneauDroit.java

${BDIR}/vue/PanneauGauche.class : ${SDIR}/vue/PanneauGauche.java ${BDIR}/controleur/ContactVue.class ${BDIR}/controleur/Controleur.class ${BDIR}/controleur/EtiquetteVue.class ${BDIR}/vue/FenetreDialogueAbstraite.class ${BDIR}/vue/FenetreFormulaireContact.class ${BDIR}/vue/FenetreFormulaireEtiquette.class ${BDIR}/vue/FenetreFormulaireListeDiffusion.class ${BDIR}/controleur/ListeDiffusionVue.class ${BDIR}/vue/NavigationListener.class ${BDIR}/vue/Style.class ${BDIR}/vue/StyleBouton.class
	${JC} ${JCFLAGS} ${SDIR}/vue/PanneauGauche.java

${BDIR}/vue/PanneauListeDiffusion.class : ${SDIR}/vue/PanneauListeDiffusion.java ${BDIR}/controleur/ContactVue.class ${BDIR}/controleur/Controleur.class ${BDIR}/vue/LigneListeDiffusion.class ${BDIR}/controleur/ListeDiffusionVue.class ${BDIR}/vue/PressePapiers.class ${BDIR}/vue/Style.class ${BDIR}/vue/StyleBouton.class ${BDIR}/vue/StyleComposant.class
	${JC} ${JCFLAGS} ${SDIR}/vue/PanneauListeDiffusion.java

${BDIR}/vue/PanneauOperationsAbstrait.class : ${SDIR}/vue/PanneauOperationsAbstrait.java ${BDIR}/controleur/Controleur.class ${BDIR}/vue/FenetreDialogueAbstraite.class ${BDIR}/vue/Style.class ${BDIR}/vue/StyleBouton.class
	${JC} ${JCFLAGS} ${SDIR}/vue/PanneauOperationsAbstrait.java

${BDIR}/vue/PanneauOperationsContact.class : ${SDIR}/vue/PanneauOperationsContact.java ${BDIR}/controleur/ContactVue.class ${BDIR}/controleur/Controleur.class ${BDIR}/vue/FenetreDialogueAbstraite.class ${BDIR}/vue/FenetreFormulaireContact.class ${BDIR}/vue/PanneauOperationsAbstrait.class
	${JC} ${JCFLAGS} ${SDIR}/vue/PanneauOperationsContact.java

${BDIR}/vue/PanneauOperationsEtiquette.class : ${SDIR}/vue/PanneauOperationsEtiquette.java ${BDIR}/controleur/Controleur.class ${BDIR}/controleur/EtiquetteVue.class ${BDIR}/vue/FenetreDialogueAbstraite.class ${BDIR}/vue/FenetreFormulaireEtiquette.class ${BDIR}/controleur/ListeDiffusionVue.class ${BDIR}/vue/PanneauOperationsAbstrait.class
	${JC} ${JCFLAGS} ${SDIR}/vue/PanneauOperationsEtiquette.java

${BDIR}/vue/PanneauOperationsListeDiffusion.class : ${SDIR}/vue/PanneauOperationsListeDiffusion.java ${BDIR}/controleur/Controleur.class ${BDIR}/controleur/CritereVue.class ${BDIR}/vue/FenetreDialogueAbstraite.class ${BDIR}/vue/FenetreFormulaireListeDiffusion.class ${BDIR}/controleur/ListeDiffusionVue.class ${BDIR}/vue/PanneauOperationsAbstrait.class
	${JC} ${JCFLAGS} ${SDIR}/vue/PanneauOperationsListeDiffusion.java
	
${BDIR}/vue/PanneauRepertoire.class : ${SDIR}/vue/PanneauRepertoire.java ${BDIR}/controleur/ContactVue.class ${BDIR}/controleur/Controleur.class ${BDIR}/controleur/EtiquetteVue.class ${BDIR}/vue/LigneContact.class ${BDIR}/vue/NavigationListener.class ${BDIR}/vue/Style.class
	${JC} ${JCFLAGS} ${SDIR}/vue/PanneauRepertoire.java

${BDIR}/vue/PressePapiers.class : ${SDIR}/vue/PressePapiers.java
	${JC} ${JCFLAGS} ${SDIR}/vue/PressePapiers.java

${BDIR}/vue/Style.class : ${SDIR}/vue/Style.java
	${JC} ${JCFLAGS} ${SDIR}/vue/Style.java

${BDIR}/vue/StyleBouton.class : ${SDIR}/vue/StyleBouton.java ${BDIR}/vue/Style.class
	${JC} ${JCFLAGS} ${SDIR}/vue/StyleBouton.java

${BDIR}/vue/StyleComposant.class : ${SDIR}/vue/StyleComposant.java ${BDIR}/vue/Style.class ${BDIR}/vue/StyleBouton.class
	${JC} ${JCFLAGS} ${SDIR}/vue/StyleComposant.java

${BDIR}/vue/ZoneDefilante.class : ${SDIR}/vue/ZoneDefilante.java
	${JC} ${JCFLAGS} ${SDIR}/vue/ZoneDefilante.java

${BDIR}/controleur/CarnetListener.class : ${SDIR}/controleur/CarnetListener.java
	${JC} ${JCFLAGS} ${SDIR}/controleur/CarnetListener.java

${BDIR}/controleur/ContactVue.class : ${SDIR}/controleur/ContactVue.java ${BDIR}/controleur/EtiquetteVue.class
	${JC} ${JCFLAGS} ${SDIR}/controleur/ContactVue.java

${BDIR}/controleur/Controleur.class : ${SDIR}/controleur/Controleur.java ${BDIR}/model/OrdreTri.class ${BDIR}/model/Carnet.class ${BDIR}/model/Contact.class ${BDIR}/model/ContactDAO.class ${BDIR}/controleur/ContactVue.class ${BDIR}/controleur/CritereVue.class ${BDIR}/model/Etiquette.class ${BDIR}/model/EtiquetteDAO.class ${BDIR}/controleur/EtiquetteVue.class ${BDIR}/model/ListeDiffusion.class ${BDIR}/model/ListeDiffusionDAO.class ${BDIR}/controleur/ListeDiffusionVue.class ${BDIR}/model/ModeleException.class
	${JC} ${JCFLAGS} ${SDIR}/controleur/Controleur.java

${BDIR}/controleur/ControleurFormulaireContact.class : ${SDIR}/controleur/ControleurFormulaireContact.java ${BDIR}/controleur/ContactVue.class ${BDIR}/controleur/Controleur.class ${BDIR}/vue/FenetreFormulaireContact.class ${BDIR}/vue/FormulaireListener.class
	${JC} ${JCFLAGS} ${SDIR}/controleur/ControleurFormulaireContact.java

${BDIR}/controleur/ControleurFormulaireEtiquette.class : ${SDIR}/controleur/ControleurFormulaireEtiquette.java ${BDIR}/controleur/Controleur.class ${BDIR}/controleur/EtiquetteVue.class ${BDIR}/vue/FenetreFormulaireEtiquette.class ${BDIR}/vue/FormulaireListener.class
	${JC} ${JCFLAGS} ${SDIR}/controleur/ControleurFormulaireEtiquette.java

${BDIR}/controleur/ControleurFormulaireListe.class : ${SDIR}/controleur/ControleurFormulaireListe.java ${BDIR}/controleur/Controleur.class ${BDIR}/vue/FenetreFormulaireListeDiffusion.class ${BDIR}/vue/FormulaireListener.class ${BDIR}/controleur/ListeDiffusionVue.class
	${JC} ${JCFLAGS} ${SDIR}/controleur/ControleurFormulaireListe.java

${BDIR}/controleur/ControleurNavigation.class : ${SDIR}/controleur/ControleurNavigation.java ${BDIR}/controleur/ContactVue.class ${BDIR}/controleur/Controleur.class ${BDIR}/controleur/EtiquetteVue.class ${BDIR}/controleur/ListeDiffusionVue.class ${BDIR}/vue/NavigationListener.class
	${JC} ${JCFLAGS} ${SDIR}/controleur/ControleurNavigation.java

${BDIR}/controleur/CritereVue.class : ${SDIR}/controleur/CritereVue.java ${BDIR}/controleur/EtiquetteVue.class
	${JC} ${JCFLAGS} ${SDIR}/controleur/CritereVue.java

${BDIR}/controleur/EtiquetteVue.class : ${SDIR}/controleur/EtiquetteVue.java
	${JC} ${JCFLAGS} ${SDIR}/controleur/EtiquetteVue.java

${BDIR}/controleur/ListeDiffusionVue.class : ${SDIR}/controleur/ListeDiffusionVue.java ${BDIR}/controleur/CritereVue.class
	${JC} ${JCFLAGS} ${SDIR}/controleur/ListeDiffusionVue.java

${BDIR}/model/Carnet.class : ${SDIR}/model/Carnet.java ${BDIR}/model/OrdreTri.class ${BDIR}/model/Texte.class ${BDIR}/model/Contact.class ${BDIR}/model/ContactDAO.class ${BDIR}/controleur/ContactVue.class ${BDIR}/model/Critere.class ${BDIR}/controleur/CritereVue.class ${BDIR}/model/Etiquette.class ${BDIR}/model/EtiquetteDAO.class ${BDIR}/controleur/EtiquetteVue.class ${BDIR}/model/ListeDiffusion.class ${BDIR}/model/ListeDiffusionDAO.class ${BDIR}/controleur/ListeDiffusionVue.class ${BDIR}/model/ModeleException.class
	${JC} ${JCFLAGS} ${SDIR}/model/Carnet.java

${BDIR}/model/Connexion.class : ${SDIR}/model/Connexion.java
	${JC} ${JCFLAGS} ${SDIR}/model/Connexion.java

${BDIR}/model/Contact.class : ${SDIR}/model/Contact.java ${BDIR}/controleur/ContactVue.class ${BDIR}/model/Etiquette.class ${BDIR}/controleur/EtiquetteVue.class ${BDIR}/model/ModeleException.class
	${JC} ${JCFLAGS} ${SDIR}/model/Contact.java

${BDIR}/model/ContactEtiquetteDAO.class : ${SDIR}/model/ContactEtiquetteDAO.java ${BDIR}/model/Contact.class ${BDIR}/model/Etiquette.class ${BDIR}/model/ModeleException.class
	${JC} ${JCFLAGS} ${SDIR}/model/ContactEtiquetteDAO.java

${BDIR}/model/ContactDAO.class : ${SDIR}/model/ContactDAO.java ${BDIR}/model/OrdreTri.class ${BDIR}/model/ContactEtiquetteDAO.class ${BDIR}/model/Contact.class ${BDIR}/model/Etiquette.class ${BDIR}/model/ModeleException.class ${BDIR}/model/Transactions.class
	${JC} ${JCFLAGS} ${SDIR}/model/ContactDAO.java

${BDIR}/model/Critere.class : ${SDIR}/model/Critere.java ${BDIR}/controleur/CritereVue.class ${BDIR}/model/Etiquette.class
	${JC} ${JCFLAGS} ${SDIR}/model/Critere.java

${BDIR}/model/Etiquette.class : ${SDIR}/model/Etiquette.java ${BDIR}/controleur/EtiquetteVue.class ${BDIR}/model/ModeleException.class
	${JC} ${JCFLAGS} ${SDIR}/model/Etiquette.java

${BDIR}/model/EtiquetteDAO.class : ${SDIR}/model/EtiquetteDAO.java ${BDIR}/model/Etiquette.class ${BDIR}/model/ModeleException.class
	${JC} ${JCFLAGS} ${SDIR}/model/EtiquetteDAO.java

${BDIR}/model/ListeDiffusion.class : ${SDIR}/model/ListeDiffusion.java ${BDIR}/model/Critere.class ${BDIR}/controleur/CritereVue.class ${BDIR}/controleur/ListeDiffusionVue.class ${BDIR}/model/ModeleException.class
	${JC} ${JCFLAGS} ${SDIR}/model/ListeDiffusion.java

${BDIR}/model/ListeDiffusionDAO.class : ${SDIR}/model/ListeDiffusionDAO.java ${BDIR}/model/Contact.class ${BDIR}/model/ContactDAO.class ${BDIR}/model/ContactEtiquetteDAO.class ${BDIR}/model/Critere.class ${BDIR}/model/Etiquette.class ${BDIR}/model/ListeDiffusion.class ${BDIR}/model/ModeleException.class ${BDIR}/model/Transactions.class
	${JC} ${JCFLAGS} ${SDIR}/model/ListeDiffusionDAO.java

${BDIR}/model/ModeleException.class : ${SDIR}/model/ModeleException.java
	${JC} ${JCFLAGS} ${SDIR}/model/ModeleException.java

${BDIR}/model/Transactions.class : ${SDIR}/model/Transactions.java
	${JC} ${JCFLAGS} ${SDIR}/model/Transactions.java

${BDIR}/model/Texte.class : ${SDIR}/model/Texte.java
	${JC} ${JCFLAGS} ${SDIR}/model/Texte.java

${BDIR}/model/OrdreTri.class : ${SDIR}/model/OrdreTri.java
	${JC} ${JCFLAGS} ${SDIR}/model/OrdreTri.java

### REGLES D'AUTRES ACTIONS ###

run : atoz.jar
	${JVM} ${JVMFLAGS} ${PACKAGE}.Main

clean :
	-rm -rf bin/*
	-rm -f atoz.jar

mrproper : clean atoz.jar

### BUTS FACTICES ###

.PHONY : run clean mrproper

### FIN ###