## Description: I'm so lite

It's very upsetting when the data about registered users disappears after the program is completed. To avoid this problem, you need to create a database where you will store all the necessary information about the created credit cards. We will use SQLite to create the database.

SQLite is a database engine. It is software that allows users to interact with a relational database. In SQLite, a database is stored in a single file — a trait that distinguishes it from other database engines. This allows for greater accessibility: copying a database is no more complicated than copying the file that stores the data, and sharing a database implies just sending an email attachment.

You may consult the [SQL tutorial website](https://www.sqlitetutorial.net/sqlite-java/) to navigate the work with SQLite from Java.

You don't have to install any libraries to work with a database — you can use it in the project without any problems.

## Objectives

In this stage, create a database with a table titled `card`. It should have the following columns:

*   id INTEGER
*   number TEXT
*   pin TEXT
*   balance INTEGER DEFAULT 0

Also, in this stage, you should read the database file name from the command line argument. Filename should be passed to the program using `-fileName` argument, for example, `-fileName db.s3db`.

Pay attention: your database file should be created when the program starts, if it hasn't yet been created. And all created cards should be stored in the database from now.

## Example

The symbol `>` represents the user input. Notice that it's not a part of the input.

    1\. Create account
    2\. Log into account
    0\. Exit
    >1

    Your card has been created
    Your card number:
    4000003429795087
    Your card PIN:
    6826

    1\. Create account
    2\. Log into account
    0\. Exit
    >2

    Enter your card number:
    >4000003429795087
    Enter your PIN:
    >4444

    Wrong card number or PIN!

    1\. Create account
    2\. Log into account
    0\. Exit
    >2

    Enter your card number:
    >4000003429795087
    Enter your PIN:
    >6826

    You have successfully logged in!

    1\. Balance
    2\. Log out
    0\. Exit
    >1

    Balance: 0

    1\. Balance
    2\. Log out
    0\. Exit
    >2

    You have successfully logged out!

    1\. Create account
    2\. Log into account
    0\. Exit
    >0

    Bye!