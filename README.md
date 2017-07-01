# Flight Fare Finder
Application built on [Selenium WebDriver](http://www.seleniumhq.org/) for finding flight fares

## Flights From
**Czechia**
- Prague (Václav Havel airport) - IATA CODE: PRG
- Brno (Turany international airport) - IATA CODE: BRQ
- Ostrava (Leoš Janáček airport) - IATA CODE: OSR

## Airlines
- Ryanair
- Wizzair

## Installation

### Prerequisites for developing
- Install IDE, eg. [Eclipse](https://www.eclipse.org/downloads/download.php?file=/oomph/epp/oxygen/R/eclipse-inst-win64.exe)
- Install [Google Chrome](https://www.google.com/chrome/)
- Download [Chrome Driver](https://sites.google.com/a/chromium.org/chromedriver/downloads) unzip to folder and set the folder's path in your system path
- (For Linux users: put chromedriver file to usr/local/bin)
- Download and install [Java Development Kit (JDK)](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
- Download [Selenium Java Client Driver](http://seleniumhq.org/download/) unzip to folder and put it in your project's classpath
- Download [Apache POI](http://poi.apache.org/download.html) unzip to folder and put it in your project's classpath
- Download [javax.mail.jar](https://javaee.github.io/javamail/#Download_JavaMail_Release) and put it in your project's classpath
- Download TestNG plugin - [Follow instructions](http://toolsqa.com/selenium-webdriver/install-testng/)
- Download Maven plugin - [Follow instructions](http://toolsqa.com/java/maven/how-to-install-maven-eclipse-ide/)

### Prerequisities for running tests from command line
- Download and set (Maven for Windows)[https://www.mkyong.com/maven/how-to-install-maven-in-windows/]

## Usage
1. Run Flights Loader to get all flights in the excel file
```{r, engine='sh'}
run cmd
navigate to project
mvn clean test -Ploader
```
2. Choose flight you want
3. Open(eg. in notepad) FlightsFinder.xml and change example suite attributes
- test name = Name of suite
- set value for parametr from = city you want to flight from
- set value for parametr to = city you want to flight to
- set value for parametr priceLimit = your price limit for flight fare
4. If you want add more flights, copy test suite, paste it under last suite and change attributes
5. Open(eg. in notepad) EmailSender.java (src/utility) and edit following lines:
- line 40: set SMTP server for sending e-mails
- line 42: set e-mail address for sending e-mails
- line 44: set password for e-mail
- line 46: set SMTP port
6. Open(eg. notepad) TestReporter.java(src/utility) and edit following lines:
- line 19: set e-mail subject
- line 20: set e-mail message. HTML allowed
7. Run Flights Finder
```{r, engine='sh'}
run cmd
navigate to project
mvn clean test -Pfinder
```

## Project
* Clone git repo
```{r, engine='sh'}
git clone https://github.com/martin-pechacek/flight-fare-finder.git
```
