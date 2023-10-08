# Exemple d'aplicació adaptativa amb JavaFX #

En aquest projecte un exemple d'aplicació adaptadiva amb .fxml

És la base que detecta el canvi de mida de finestra, a partir de la que començar a desenvolupar altres projectes.

### Compilació i funcionament ###

Cal el 'Maven' per compilar el projecte
```bash
mvn clean
mvn compile
mvn exec:java -Dexec.mainClass="com.project.Main" -q
```

Per executar el projecte a Windows cal
.\run.ps1 com.project.Main

Per executar el projecte a Linux/macOS cal
./run.sh com.project.Main

