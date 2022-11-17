# Projektdokumentation codelineway Sequel

By Delia Reho, Melanie Völlmin & Joel Jütte

![Logo](codelinewayLogoOnlyClear.png)

| **Datum** | **Version** | **Änderung** | **Autor** |
| --- | --- | --- | --- |
| 03.11.2022 | 1.1 | Initial I P E | Joel Jütte |
| 10.11.2022 | 1.2 | Start R | Melanie Völlmin |
| 17.11.2022 | 1.3 | Fokus Kommentarbereich | Delia Reho |
| 24.11.2022 ||||
| 01.12.2022 ||||
| 08.12.2022 ||||
| 15.12.2022 ||||

## 1. Informieren

### 1.1 Projekt

Wir wollen unseren Webshop erweitern. Dabei möchten wir gerne die Registrierung und Anmeldung vom letzten Mal fertig realisieren. Die Benutzer sollen Kommentare zu den Produkten hinterlassen können. Ebenfalls soll die Adresse beim ersten Check-Out in der Datenbank gespeichert werden und ab dort automatisch ausgefüllt werden. Der Webshop soll gehostet werden, wobei auf die Sicherheit der Daten geachtet werden muss. Eventuelle weitere Features/Erweiterungen wären:

- Profil-Seite, wo man Dinge ändern kann
- Bewertungen mit Sternen
- Echte Zahlungsanbindung (Twint, Debit- und Kreditkarte, PayPal, etc.)
- Webshop responsive gestalten (für Handy, Tablet)
- Webshop in eine App verwandeln

Im Bezug auf die Sicherheit möchten wir gerne die Leistungsziele 19, 22 und 24 des Moduls 183 «Applikationssicherheit» beachten.

**Leistungsziel 19** fordert, dass Passwörter, die in Datenbanken gespeichert werden, korrekt gehasht werden müssen. Da wir unsere Benutzer und deren Daten in einer Datenbank speichern, wollen wir die Benutzerdaten, also Passwörter und Emails hashen, um die Sicherheit zu erhöhen.

Im **Leistungsziel 22** geht es darum, Eingaben zu validieren und Codemanipulation zu verhindern. Die sogenannte SQL-Injektion zum Beispiel wird genutzt, um ohne Passwort in einen beliebigen Useraccount zu kommen. Solche Gefahren wollen wir verhindern, in dem wir einerseits im Code vorsichtig mit den uns gegebenen Daten umgehen und andererseits die Daten validieren, bevor wir sie weiterverarbeiten. So wird ein Passwort zuerst auf Abstände und problematische Zeichen geprüft, bevor es in der Datenbank weitergenutzt wird.

Beim **Leistungsziel 24** handelt es sich um das Sessionhandling und wo man dabei vielleicht vorsichtig sein muss. Daten müssen beim Sessionhandling vorübergehend gespeichert werden. Dabei muss man aufpassen, wo und wie die Daten gespeichert werden, um die Sicherheit der Daten zu gewähren. Wir machen uns Gedanken dazu, wie diese Daten zwischengespeichert werden, insofern wir Gebrauch von Sessions machen.

### 1.2 Quellen

- [CSS costum properties (variables)](https://developer.mozilla.org/en-US/docs/Web/CSS/Using_CSS_custom_properties)
- [Spring Applikation ausführen](https://www.jetbrains.com/help/idea/your-first-spring-application.html#run-spring-application)
- [Full Course – Spring Boot Tutorial](https://www.youtube.com/watch?v=9SGDpanrc8U)
- [Relationale Daten mit Spring](https://spring.io/guides/gs/relational-data-access)
- [Spring Boot JDBC](https://www.javatpoint.com/spring-boot-jdbc)
- [Spring JDBC Template Querying Beispiele](https://mkyong.com/spring/spring-jdbctemplate-querying-examples/)
- [Tutorial für automatisch generierte E-Mail](https://netcorecloud.com/tutorials/send-email-in-java-using-gmail-smtp/)
- [Textarea verändert Grösse nach Inhalt](https://youtu.be/s-sYzWPDTP8)
- [Bootstrap Form](https://getbootstrap.com/docs/4.0/components/forms/?)
- jQuery zu [Springboot](https://www.codejava.net/frameworks/spring-boot/add-bootstrap-and-jquery-in-a-spring-boot-project) hinzufügen
- [Test ob String eine Nummer enthält](https://www.tutorialspoint.com/Check-if-a-string-contains-a-number-using-Java#:~:text=To%20find%20whether%20a%20given,method%20of%20the%20Character%20class.)

### 1.3 Anforderungen

| **Nummer** | **Muss / Kann?** | **Funktional? Qualität? Rand?** | **Beschreibung** |
| --- | --- | --- | --- |
| 1 | Muss | Funktional | Produkte im Warenkorb können entfernt werden |
| 2 | Muss | Funktional | Um ins Check-out zu gelangen muss man eingeloggt sein. |
| 3 | Muss | Funktional | Ein Benutzer kann sich mit Namen, E-Mail und Passwort registrieren. |
| 4 | Muss | Funktional | Ein registrierter Benutzer kann sich mit E-Mail und Passwort anmelden. |
| 5 | Muss | Funktional | Alle Eingaben werden auf Gefahren überprüft. |
| 6 | Muss | Funktional | Es gibt eine Password Policy. |
| 7 | Muss | Funktional | Die E-Mail wird validiert. |
| 8 | Muss | Funktional | Es gibt eine E-Mail-Verifikation. |
| 9 | Muss | Funktional | Um etwas zu kaufen, muss man verifiziert sein. |
| 10 | Muss | Funktional | Die Adresseingaben im ersten Check-Out eines neu registrierten Benutzers werden in der Datenbank gespeichert. |
| 11 | Muss | Funktional | Die Adresseingaben im Check-Out werden automatisch befüllt, wenn die Daten in der Datenbank vorhanden sind. |
| 12 | Kann | Funktional | Der Benutzer kann online bezahlen. |
| 13 | Muss | Funktional | Die versendete E-Mail enthält den Code als Datei im Anhang. |
| 14 | Muss | Funktional | Es gibt ein Kommentarbereich auf der Produktseite. |
| 15 | Muss | Funktional | Allen Benutzern werden die Kommentare von anderen Benutzern angezeigt. |
| 16 | Muss | Funktional | Nur eingeloggte Benutzer können Kommentare schreiben. |
| 17 | Muss | Rand | OneDrive wird als Speicherort verwendet. |
| 18 | Muss | Rand | Jeden Halbtag wird das Projekt und die Dokumentation Auf GitHub gepusht. |
| 19 | Muss | Rand | Die Webapplikation wird gehostet. |
| 20 | Muss | Rand | Die Datenbank für die Webapplikation wird online gehostet, so dass alle Mitentwickler damit arbeiten können. |
| 21 | Muss | Rand | Das Projekt muss bis am 15.12.2022 fertig sein. |

  
### 1.4 Diagramme

## 2 Planen

### 2.1 Arbeitspakete/To-Do

| **Nr.** | **Frist** | **Beschreibung** | **Person** | **Zeit (geplant)** | **Zeit (effektiv)** |
| --- | --- | --- | --- | --- | --- |
| 1.1 | 03.11.2022 | Dokumentation erstellen und IPE ausfüllen | Alle | 90 | 120 |
| 1.2 | 03.11.2022 | Mögliche Verbindungen zu Online-Datenbank recherchieren | Melanie | 45 | 50+ |
| 1.3 | 03.11.2022 | Online-Datenbank einrichten (Tables) und mit bestehendem Webshop verbinden | Melanie | 45 | 35+ |
| 1.4 | 03.11.2022 | Überarbeitung der Login Seite und erstellen der Verifikation Seite | Joel | 45 | 55 |
| 1.5 | 03.11.2022 | Informieren wie man JavaScript (jQuery) mit Springboot verwendet. jQuery für die Login Seite schreiben. | Joel | 45 | 25 |
| 1.6 | 03.11.2022 | GUI des Kommentarbereichs entwerfen | Delia | 45 | 30 |
| 1.7 | 03.11.2022 | GUI des Kommentarbereichs implementieren | Delia | 45 | 60 |
| 1.8 | ~~03.11.2022~~ 10.11.2022 | Dokumentation fertigstellen und gemeinsam mit aktuellen Projektstand auf dem bereits vorhandenen GitHub-Repository hochladen | Alle | 45 | 20 |
| 2 ||||||
| 2.1 | 10.11.2022 | Wöchentliches HDKOM\* | Alle | 22 | 30 |
| 2.2 | 10.11.2022 | Login mit Datenbank verbinden | Joel | 45 | 55 |
| 2.3 | 10.11.2022 | Validierung von Eingabe und Password Policy Implementieren | Joel | 45 | 55 |
| 2.4 | ~~10.11.2022~~ 17.11.2022 | Login auf der Webpage implementieren | Joel | 90 | 45 + 85130 |
| 2.5 | 10.11.2022 | GUI des Kommentarbereichs fertigstellen | Delia | 90 | 30 |
| 2.6 | ~~10.11.2022~~ 17.11.2022 | Funktionalität des Kommentarbereichs umsetzen| Delia | 90 | 120 |
| 2.7 | 10.11.2022 | Warenkorb-Feature «entfernen» hinzufügen | Melanie | 45 | 60 |
| 2.8 | 10.11.2022 | Check-Out Formular anpassen/ Pflichtcheckbox hinzufügen | Melanie | 45 | 25 |
| 2.9 | ~~10.11.2022~~ 17.11.2022 | Autofill Check-Out Formular vorbereiten | Melanie | 45 | 20+75 |
| 2.10 | 10.11.2022 | Reserve | Melanie | 45 | 75\*\* |
| 2.11 | 10.11.2022 | Dokumentation nachführen und mit dem Projekt auf GitHub pushen | Alle | 22 | 20 |
| 3 ||||||
| 3.1 | 17.11.2022 | Wöchentliches HDKOM\* | Alle | 22 | 30 |
| 3.2 | 17.11.2022 | Besprechung und Hilfestellung Kommentarbereich | Alle | 45 | 45 |
| 3.3 | 17.11.2022 | Einrichtung des MySQL Workbench Servers, um das Projekt auf meinem Gerät starten zu können | Joel | 45 | 10+ |
| 3.4 | 17.11.2022 | «Nicht gefunden» Anzeige erstellen | Joel | 30 | 30 |
| 3.5 | 17.11.2022 | XSS in der Suchfunktion verhindern | Joel | 60 | 40 |
| 3.6 | 17.11.2022 | Fehlertesting: Produkt mehr als einmal in den Warenkorb hinzufügen | Melanie | 25 | 10 |
| 3.7 | ~~17.11.2022~~ 24.11.2022 | Alle Links im Header funktionierend machen | Melanie | 20 | 5 |
| 3.8 | 17.11.2022| Hilfestellung Comments | Melanie | 45 | 45 |
| 3.9 | ~~17.11.2022~~ 24.11.2022 | CSS für Warenkorb und CheckOut anpassen | Melanie | 20 ||
| 3.10 | ~~17.11.2022~~ 24.11.2022 | Funktionalität Kommentare senden | Delia | 60 | 50+ |
| 3.11 | 17.11.2022 | Funktionalität Kommentare anzeigen | Delia | 45 | 60 |
| 3.12 | 17.11.2022 | Funktionalität Textarea-Erweiterung bei Input | Delia | 30 | 40 |
| 3.13 | 17.11.2022 | CSS für Comment Section anpassen | Delia | 15 | 10 |
| 3.14 | 17.11.2022 | Dokumentation nachführen und mit dem Projekt auf GitHub pushen | Alle | 22 | 15 |
| 4 ||||||
| 4.1 | 24.11.2022 | Wöchentliches HDKOM\* | Alle | 22 ||
| 4.2 | 24.11.2022 |XSS-Testen und allenfalls verhindern | Joel | 22 ||
| 4.3 | 24.11.2022 | SQL-Injektion verhindern | Joel / Melanie | 45 ||
| 4.4 | 24.11.2022 | CSS aufräumen | Joel | 22 ||
| 4.5 | 24.11.2022 | Formulare optisch überarbeiten | Joel | 45 ||
| 4.6 | 24.11.2022 | Code Datei (Produkt) in die DB einbinden | Joel | 45 ||
| 4.7 | 24.11.2022 | Error Seite erstellen | Melanie | 45 ||
| 4.8 | 24.11.2022 | Error Seite Implementieren | Melanie | 22 ||
| 4.9 | 24.11.2022 | Bestellbestätigungsemail anpassen | Melanie | 45 ||
| 4.10 | 24.11.2022 | Aktuelles Datum anzeigen bei Kommentaren | Delia | 45 ||
| 4.11 | 24.11.2022 | Funktionalität Kommentare senden | Delia | 60 ||
| 4.12 | 24.11.2022 | Funktionalität mehrere Kommentare anzeigen | Delia | 45 ||
| 4.13 | 24.11.2022 | Dokumentation nachführen und mit dem Projekt auf Github pushen | Alle | 22 ||

**\*HDKOM** = **H** ead **D** eveloper **K** ick- **O** ff **M** eeting (Zweck: Besprechung des Projektstands und den Plänen für diese Woche)
_alternativ__:_ _HDMI = Head Developer_ _Management_ _Interchange_

\*\*verbraucht wegen Problemen mit OneDrive, Probleme mit Code-Sharing und da Projekt nicht gestartet werden konnte

### 2.2 Milestones

| Datum | Ziel |
|---|---|
| 03.11.2022 | IPE abschliessen |
| 10.11.2022 | Projekt läuft auf allen Notebooks der Gruppenmitglieder |
| 17.11.2022 | ~~Webpage ist gehostet~~ |
| 01.12.2022 | Kommentierbereich funktioniert |
| 08.12.2022 | Check-Out funktioniert |
| 08.12.2022 | Webpage ist gehostet |

## 3. Entscheiden

##### Wie wird die Password Policy gestaltet?
Die Passwörter sollen mindestens 8 Zeichen beinhalten, darunter mindestens eine Zahl und mindestens einen Buchstaben. Mindestens ein Buchstabe muss grossgeschrieben werden. Leerzeichen im Passwort sind nicht erlaubt.

##### Wie stellen wir den Code auf der Item Page dar?
Da wir den Code nicht als Bild darstellen können, werden wir einen Screenshot eines Codes überall als Filler auf die Items-Pages klatschen und in der Shopübersicht sind die Bilder einfach das Logo unseres Projekts.

##### Welche Farben wollen wir benutzen?
Wir wollen unsere Website ähnlich wie Programmiercode in einer IDE gestalten. Der Hintergrund soll grau/schwarz sein, die Schrift weiss. Es gibt einige farbige Akzente, die in Blautönen gehalten sind, wie unser Logo. Folgendes sind die gewählten Farben mit Hex Code:

| **Farbe** | **Verwendung** | **Hex Code** |
| --- | --- | --- |
| Schwarzgrau | Hintergrund | #3b3b3b |
| Helleres Schwarzgrau | Akzente über Hintergrund | #525252 |
| Weissgrau | Schrift | #F9F6EE |
| Blauer Akzent 1 | Akzente für Titel | #7DF9FF |
| Blauer Akzent 2 | Akzente für wichtige Abschnitte | #6495ED |
| Blauer Akzent 3 | Akzente für Navbar und Suche | #0047AB |

## 4. Realisieren

## 5. Kontrollieren

Wir testen nur die Anforderungen, die erfüllt wurden und nicht zur Kategorie «_Rahmenbedingungen_» zählen:

| **Nummer** | **Beschreibung** | **Status** |
| --- | --- | --- |
| 1 | Produkte im Warenkorb können entfernt werden | Erfüllt |
| 2 | Um ins Check-out zu gelangen muss man eingeloggt sein. | In Bearbeitung |
| 3 | Ein Benutzer kann sich mit Namen, E-Mail und Passwort registrieren. | Erfüllt |
| 4 | Ein registrierter Benutzer kann sich mit E-Mail und Passwort anmelden. | Erfüllt |
| 5 | Alle Eingaben werden auf Gefahren überprüft. | In Bearbeitung |
| 6 | Es gibt eine Password Policy. | Erfüllt |
| 7 | Die E-Mail wird validiert. | In Bearbeitung |
| 8 | Es gibt eine E-Mail-Verifikation. | Noch nicht erfüllt |
| 9 | Um etwas zu kaufen, muss man verifiziert sein. | Noch nicht erfüllt |
| 10 | Die Adresseingaben im ersten Check-Out eines neu registrierten Benutzers werden in der Datenbank gespeichert. | In Bearbeitung |
| 11 | Die Adresseingaben im Check-Out werden automatisch befüllt, wenn die Daten in der Datenbank vorhanden sind. | In Bearbeitung |
| 12 | Der Benutzer kann online bezahlen. | Noch nicht erfüllt |
| 13 | Die versendete E-Mail enthält den Code als Datei im Anhang. | Noch nicht erfüllt |
| 14 | Es gibt ein Kommentarbereich auf der Produktseite. | Erfüllt |
| 15 | Allen Benutzern werden die Kommentare von anderen Benutzern angezeigt. | In Bearbeitung |
| 16 | Nur eingeloggte Benutzer können Kommentare schreiben. | Noch nicht erfüllt |
| 17 | OneDrive wird als Speicherort verwendet. | Erfüllt |
| 18 | Jeden Halbtag wird das Projekt und die Dokumentation Auf GitHub gepusht. | In Bearbeitung |
| 19 | Die Webapplikation wird gehostet. | Noch nicht erfüllt |
| 20 | Die Datenbank für die Webapplikation wird online gehostet, so dass alle Mitentwickler damit arbeiten können. | Noch nicht erfüllt|
| 21 | Das Projekt muss bis am 15.12.2022 fertig sein. | Noch nicht erfüllt |

### 5.1 Testfälle

| **Nummer** | **Voraussetzung** | **Eingabe** | **Erwartete Ausgabe** |
| --- | --- | --- | --- |
|||||

### 5.2 Testprotokoll

| **Nummer** | **Datum** | **Resultat** | **Durchgeführt** |
| --- | --- | --- | --- |
|||||

### 5.3 Testfazit

## 6. Auswerten

- _Siehe_ _Portfolios_
