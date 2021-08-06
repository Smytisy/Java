package banking;

import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static String url;
    public static void main(String args[]) {
        doTask(args[1]);
    }

    public static void doTask(String urlTmp) {
        url = urlTmp;
        Scanner scanner = new Scanner(System.in);
        boolean flag = true;
        boolean isLogIn = false;
        String key = null;
        String pin = null;
        createTable();

        while (flag) {
            printInterface(isLogIn);
            switch (scanner.nextInt()) {
                case 0: {
                    flag = false;
                    System.out.printf("%nBye!%n");
                    break;
                }
                case 1: {
                    if (!isLogIn) {

                        String keyAndPin = getNumberCardLuhnAndPin();
                        String key1 = keyAndPin.substring(0, 16);
                        String pin1 = keyAndPin.substring(16);
                        setNumberAndPin(key1, pin1);
                        System.out.printf("%nYour card has been created%nYour card number:%n%s%nYour card PIN:%n%s%n", key1, pin1);
                    } else {
                        System.out.printf("%nBalance: %d%n", getBalance(key));     
                    } break;
                }
                case 2: {
                    if (!isLogIn) {
                        System.out.printf("%nEnter your card number:%n");
                        key = scanner.next().replaceAll("\\s+","");
                        System.out.printf("Enter your PIN:%n");
                        pin = scanner.next().replaceAll("\\s+","");
                        String tmp = getPinCard(key);
                        isLogIn = tmp != null && tmp.equals(pin);               // getPin
                        System.out.printf(isLogIn ?
                                "%nYou have successfully logged in!%n" : "%nWrong card number or PIN!%n");
                        if(!isLogIn) {
                            key = null; pin = null;
                        }
                    } else {
                        addIncome(key);
                    } break;
                }
                case 3: {
                    if(isLogIn) {
                        doTransfer(key);
                    } break;
                }
                case 4: {
                    if(isLogIn){
                        closeAcc(key);
                    } break;
                }
                case 5: {
                    if(isLogIn) {
                        System.out.printf("%nYou have successfully logged out!%n");
                        isLogIn = false;
                        key = null; pin = null;
                    } break;
                }
            }
        }


    }

    public static void printInterface(boolean isLogIn) {
        System.out.printf(!isLogIn ? "%n1. Create an account%n2. Log into account%n" : "%n1. Balance%n2. Add income%n");
        if(isLogIn) {
            System.out.printf("3. Do transfer%n4. Close account%n5. Log out%n");
        }
        System.out.printf("0. Exit%n");
    }

    public static String getNumberCardLuhnAndPin() {
        Random random = new Random();
        StringBuilder str = new StringBuilder();
        str.append("400000");
        int sum = 8;
        int num;
        int numForSum;
        for (int i = 0; i < 13; i++) {
            num = random.nextInt(10);
            str.append(num);
            numForSum = i % 2 == 0 ? num * 2 : num;
            numForSum = numForSum > 9 ? numForSum - 9 : numForSum;
            sum = sum + numForSum;
            if (i == 8) {
                str.append((10 - sum % 10) % 10);
            }
        }
        return str.toString();
    }

    public static String getPinCard(String key) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + url);
            Statement statement = connection.createStatement();


            ResultSet resultSet1 = statement.executeQuery("select `pin` from card where `number` = " + key + ";");
            String str = null;
            if(resultSet1.next()) {
                str = resultSet1.getString("pin");
            };
            connection.close();

            return str;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "";
    }

    public static void setNumberAndPin(String key, String pin) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + url);
            Statement statement = connection.createStatement();

            int resultSet2 = statement.executeUpdate("insert into card(number, pin) VALUES ('" + key + "','" + pin + "');");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTable() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + url);
            Statement statement = connection.createStatement();
            statement.execute("CREATE TABLE if not exists 'card' (id INTEGER PRIMARY KEY AUTOINCREMENT,number VARCHAR(16),pin VARCHAR(4),balance INTEGER DEFAULT '0');");
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean chekCard(String card) {                                   // complete
        int num;
        int sum = 0;
        for (int i = 0; i < 16; i++) {
            num = (int) card.charAt(i) - 48;
            if (i % 2 == 0) {
                num = num * 2;
                if(num > 9) {
                    num = num - 9;
                }
            };
            sum = sum + num;
        }
        return sum % 10 == 0;
    }



    public static int getBalance(String card) {                                     // complete
        try {
            int res;
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + url);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT `balance` FROM card where `number` = " + card + ";");
            resultSet.next();
            res = resultSet.getInt(1);

            connection.close();
            return res;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -337;
    }


    public static void doTransfer(String thisCard) {                                     // complete
        String delMoney = "UPDATE card SET `balance` = `balance` - ? WHERE `number` = ?;";
        String appMoney = "UPDATE card SET `balance` = `balance` + ? WHERE `number` = ?;";

        try {
            System.out.println("Transfer");
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + url);
            connection.setAutoCommit(false);
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter card number:");
            String cardGetter = scanner.next();
            int money;

            try {
                if(!chekCard(cardGetter)) throw new Exception("Probably you made a mistake in the card number. Please try again!");
                if(!haveCard(cardGetter)) throw new Exception("Such a card does not exist.");
                if(cardGetter.equals(thisCard)) throw new Exception("You can't transfer money to the same account!");

                System.out.println("Enter how much money you want to transfer:");
                money = scanner.nextInt();
                if(getBalance(thisCard) < money) throw new Exception("Not enough money!");
                PreparedStatement preparedStateDel = connection.prepareStatement(delMoney);
                PreparedStatement preparedStateApp = connection.prepareStatement(appMoney);

                preparedStateDel.setInt(1, money);
                preparedStateDel.setString(2, thisCard);

                preparedStateDel.executeUpdate();

                preparedStateApp.setInt(1, money);
                preparedStateApp.setString(2, cardGetter);

                preparedStateApp.executeUpdate();

                connection.commit();
                connection.close();
                System.out.print("Success!");

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                connection.rollback();
            }


        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void addIncome(String key) {                                     // complete
        String queryIncome = "UPDATE card SET `balance` = `balance` + ? WHERE `number` = ?;";

        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + url);
            connection.setAutoCommit(false);
            try {
                PreparedStatement preparedStateInc = connection.prepareStatement(queryIncome);
                if(!chekCard(key)) throw new SQLException("Probably you made a mistake in the card number. Please try again!");
                if(!haveCard(key)) throw new SQLException("Such a card does not exist.");
                Scanner scanner = new Scanner(System.in);
                System.out.println("Enter income:");
                preparedStateInc.setInt(1, scanner.nextInt());
                preparedStateInc.setString(2, key);
                preparedStateInc.executeUpdate();
                connection.commit();
                System.out.println("Income was added!");

            } catch (SQLException ex) {
                connection.rollback();
                ex.printStackTrace();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public static boolean haveCard(String card) {                                     // complete
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + url);
            Statement statement = connection.createStatement();

            ResultSet resultSet1 = statement.executeQuery("select `number` from card where `number` = " + card + ";"); // get
            resultSet1.next();
            String str = resultSet1.getString("number");
            connection.close();
            if(str != null) {
                return true;
            }

        } catch (SQLException ex) {

        }
        return false;
    }

    public static void closeAcc(String key) {                                     // complete
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:" + url);
            Statement statement = connection.createStatement();

            boolean resultSet = statement.execute("DELETE FROM card WHERE `number` = " + key + ";"); // get
            connection.close();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
