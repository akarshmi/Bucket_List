package BucketList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Scanner;

public class Interface {

    static String Name;
    static String UserName;
    static String Password;

    public static void main(String[] args) throws IOException, SQLException {

        System.out.println("Hey Welcome to BuckyLog!");
        System.out.println("Select User Type : \n Press 1 for New User \n Press 2 for Regular User ");


        //Input Definition for user Input
        Scanner scInput = new Scanner(System.in);
        BufferedReader brInput = new BufferedReader(new InputStreamReader(System.in));


        byte UserType = scInput.nextByte();

        if (UserType>2){
            System.out.println("The Entered Choice is Invalid.");
        }else{
        switch (UserType){
            case 1:
                System.out.println("Register Now!!");
                System.out.println("Enter Your Details:");

                System.out.print("Name: ");
                Name = brInput.readLine();
                System.out.print("UserName: ");
                UserName = brInput.readLine();
                System.out.print("Password: ");
                Password = brInput.readLine();

                BucketList.newUser(Name,UserName,Password);

                break;
            case 2: {
                System.out.println("Login Now!");

                System.out.print("UserName: ");
                UserName = brInput.readLine();
                System.out.print("Password: ");
                Password = brInput.readLine();

                BucketList.regularUser(UserName,Password);
                break;
            }
        }

        }

    }
}
