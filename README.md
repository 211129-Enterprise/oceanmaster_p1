# OceanMaster_ORM

## **About Us**

A custom ORM built in Java 8 tha persists data to a PostgresSQL repository. This api utilizes several dependencies such as the Reflections API and JDBC.

## **Creator**

- [Wayne Ledgister](https://github.com/wayneledgister)

## **Dependencies**

- Org.Relections
-Log4j
-org.postgresql

## **API Features:**

- Persists Java classes in ***Postgres Database*** utilizing ***Relections api***
- Established database connectivity with ***JDBC***.
- Created ***Custom Annotations***

### **Custom Annotations**

- @Table(table_name)
- @Column(name, contraints, sqltype))
- @JoinColum
- @Many2Many
- @One2Many

## **Configuration**

- Create an apllication.properties file.
- set url = DB_Url
- set username = DB_Username
- set password = DB_Pasword
- set modelpath = Models package location.
- Inside main method please pass the value of you application.properties as a parameter in PropertyFile.setFilePath().

-Next Call Persist.start();

## **License**

This project uses the following license: [GNU Public License 3.0.](https://www.gnu.org/licenses/gpl-3.0.en.html)
