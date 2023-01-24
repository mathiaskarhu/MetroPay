# MetroPay

## Project
Group project in the second year of Bachelor's Degree studies during the OTP -course (Software production project). Developed in group of four students.

## App
MetroPay is a banking application that is developed by using Java. The application can be used to send money, receive money, buy, sell and send cryptocurrencies, browse transaction history and take up quickie loans. App's interface is easy to use and its localized to four different languages. Interface has two different color themes.

## Technologies
Project is build with Maven, developed using mainly Java, JavaFX and CSS. For testing purposes there are JUnit and JaCoCo libraries used. Database is located in cloud services, uses Hibernate and MariaDB. Jenkins was used during the project as a DevOps tool and Nektion as project management tool (SCRUM).

## Configuration
App requires database, in file metropay_database.sql are the queries we used to create database. Database requires configurations in hibernate.cfg.xml file, URL and credentials at least. In our project, tests haves it's own database that is configured on different hibernate.cfg.xml file locating in test -directory.

## API
App is using [Free Currency Rates API](https://github.com/fawazahmed0/currency-api) for currency exchange rates.

## Screenshots

Login:  
  
![kuva](https://user-images.githubusercontent.com/82876489/212395082-a6a8bd40-f697-430b-ace7-f7f79d3c2d27.png)

Color theme switched:

![kuva](https://user-images.githubusercontent.com/82876489/212401642-f28be398-2f85-4665-bb5c-f863b401b54b.png)

Loading screen:

![kuva](https://user-images.githubusercontent.com/82876489/212401715-2e929fb1-5334-4a63-a833-53a51c0baa5c.png)

Front page after logged in:  
  
![kuva](https://user-images.githubusercontent.com/82876489/212401769-fa9cc968-b3a6-47fb-bd2e-edaf26081191.png)

New payment:

![kuva](https://user-images.githubusercontent.com/82876489/212401826-d24f506f-06cf-40a8-979b-7bdf3474ab67.png)

Payment confirmation:

![kuva](https://user-images.githubusercontent.com/82876489/212401883-d8b2ab79-04b4-4e67-a0b5-cf89524656d8.png)

Transactions:

![kuva](https://user-images.githubusercontent.com/82876489/212402100-fa1ed39c-b589-4c84-9d2a-a7502666bc7e.png)

Crypto page:

![kuva](https://user-images.githubusercontent.com/82876489/212401939-c1f8280e-d2b9-4200-93a9-7172040ea87d.png)

Buying crypto:

![kuva](https://user-images.githubusercontent.com/82876489/212402177-1dcb28c5-820d-4444-8b53-520fed129f60.png)

Quickie loan:

![kuva](https://user-images.githubusercontent.com/82876489/212402252-f07f6e2f-fb95-482b-bf7b-98620957d08c.png)
